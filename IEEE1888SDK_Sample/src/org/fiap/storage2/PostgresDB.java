/*
 * Copyright (c) 2013 Hideya Ochiai, the University of Tokyo,  All rights reserved.
 * 
 * Permission of redistribution and use in source and binary forms, 
 * with or without modification, are granted, free of charge, to any person 
 * obtaining the copy of this software under the following conditions:
 * 
 *  1. Any copies of this source code must include the above copyright notice,
 *  this permission notice and the following statement without modification 
 *  except possible additions of other copyright notices. 
 * 
 *  2. Redistributions of the binary code must involve the copy of the above 
 *  copyright notice, this permission notice and the following statement 
 *  in documents and/or materials provided with the distribution.  
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.fiap.storage2;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Postgres DB Adapter for FIAP 0.8 implementation
 * (with timeout-based garbage collection mechanisms)
 * @author Hideya Ochiai
 * create 2009-11-16
 * update 2010-01-14
 */
public class PostgresDB extends Thread {
	
	/**
	 * sqlConnection���Ǘ�����N���X
	 * @author Hideya Ochiai
	 */
	private class ConnectionManager {
		/**
		 * PostgreSQL DB�ւ̃R�l�N�V����
		 */
		Connection sqlConnection;
		
		/**
		 * ����Connection���g�p����Ă��� IDbCommand�C���X�^���X
		 */
		IDbCommand command;
		
		/**
		 * Connection���g�p�����ۂ��̃t���O
		 */
		boolean using;
		
		/**
		 * �g�p��Connection (using=true) �̃��C�t�^�C�� [ms]
		 * ���C�t�^�C���̍ő�l�́APostgresDB.CONNECTION_TIMEOUT
		 */
		long ttl;
	};
	
	/**
	 * �ő�R�l�N�V������
	 */
	private static final int MAX_CONNECTION=100;
	
	/**
	 * �R�l�N�V�����̎g�p�\����
	 */
	private static final int CONNECTION_TIMEOUT=120000; /* using but unused connection must be released in 120 sec. */
	
	/**
	 * PostgreSQL DB�ւ̐ڑ�URI 
	 */
	private static String m_ConnectionString="jdbc:postgresql:fiapstorage2";
	
	/**
	 * PostgreSQL DB�ւ̐ڑ����[�U��
	 */
	private static String m_ConnectionUser="postgres";
	
	/**
	 * PostgreSQL DB�ւ̐ڑ��p�X���[�h
	 */
	private static String m_ConnectionPassword="postgres";
	
	/**
	 * Connection �� �v�[�� 
	 */
	private java.util.ArrayList<ConnectionManager> m_Connections;
	
	/**
	 * PostgresDB�̗B��̃C���X�^���X(Singleton)
	 */
	private static PostgresDB m_Singleton=null;
	
	/**
	 * ������ -- �ݒ�Ǎ��݂ƃR�l�N�V�����v�[���̍쐬
	 * 
	 * @throws java.sql.SQLException
	 * @throws ClassNotFoundException
	 */
	private void init() throws java.sql.SQLException, ClassNotFoundException {
		
		// JDBC Connector
		Class.forName("org.postgresql.Driver");
		
		String connectionString=System.getenv("POSTGRESQL_CONNECTION_STRING");
		if(connectionString!=null && !connectionString.equals("")){
			m_ConnectionString=connectionString;
		}
	
		m_Connections=new java.util.ArrayList<ConnectionManager>();
	}	
	
	/**
	 * �R���X�g���N�^ - �����������ƃX���b�h�̊J�n (�v���O�������A���̂ݏq�ׂ���)
	 * @throws java.sql.SQLException
	 * @throws ClassNotFoundException
	 */
	private PostgresDB() throws java.sql.SQLException, ClassNotFoundException {
		m_Singleton=this;
		init();
		start();
	}

	/**
	 * �X���b�h�֐�
	 * Connection�v�[���Ŏg�p����Connection��TTL��(��1�b������)����������
	 * TTL��0��؂�����AConnection�̃��Z�b�g���s���A���g�p��Ԃɂ���
	 */
	public void run(){
		
		long lastLoopTime=System.currentTimeMillis();
		while(true){
			try{
				long time=System.currentTimeMillis();
				long diff=time-lastLoopTime;
				lastLoopTime=time;
				if(diff>0 && diff<CONNECTION_TIMEOUT/10){
					synchronized(m_Connections){
						java.util.Iterator<ConnectionManager> itr=m_Connections.iterator();
						while(itr.hasNext()){
							ConnectionManager cm=itr.next();
							if(!cm.using && cm.ttl==0){
								continue;
							}
							long ttl=cm.ttl-diff;
							if(ttl>0){
								// set decremented TTL
								cm.ttl=ttl;
							}else{
								// reset the connection and set using flag = false
								cm.using=false;
								cm.sqlConnection=DriverManager.getConnection(m_ConnectionString,m_ConnectionUser,m_ConnectionPassword);
								cm.command=null;
								cm.ttl=0;
							}
						}
					}
				}
				Thread.sleep(1000);
			}catch(Exception e){}
		}
	}

	/**
	 * command�Ɋ֘A�t�����ꂽConnection���擾����
	 * command�Ɋ֘A�t�����ꂽConnection���Ȃ��ꍇ�́A���g�p��Connection��Ԃ�
	 * ���g�p��Connection�������ꍇ�́AConnection��V���ɍ쐬��Collection�v�[���ɒǉ�����
	 * Connection�v�[���̑傫����MAX_CONNECTION�ɒB������Anull��Ԃ�
	 * @param command
	 * @return
	 * @throws java.sql.SQLException
	 */
	private java.sql.Connection getConnection(IDbCommand command) throws java.sql.SQLException  {
		
		synchronized(m_Connections){
			ConnectionManager[] cma=new ConnectionManager[0];
			cma=m_Connections.toArray(cma);
			// if using, return allocated connection
			for(int i=0;i<cma.length;i++){
				if(cma[i].command==command && cma[i].using){
					cma[i].using=true;
					cma[i].ttl=CONNECTION_TIMEOUT;
					return cma[i].sqlConnection;
				}
			}
			// return unused connection from the connection pool.
			for(int i=0;i<cma.length;i++){
				if(!cma[i].using){
					cma[i].using=true;
					cma[i].command=command;
					cma[i].ttl=CONNECTION_TIMEOUT;
					return cma[i].sqlConnection;
				}
			}
			// return newly created connection
			if(cma.length<MAX_CONNECTION){
				ConnectionManager cm=new ConnectionManager();
				cm.using=true;
				cm.command=command;
				cm.ttl=CONNECTION_TIMEOUT;
				cm.sqlConnection=DriverManager.getConnection(m_ConnectionString,m_ConnectionUser,m_ConnectionPassword);
				m_Connections.add(cm);
				return cm.sqlConnection;
			}
			// return null, if the connection pool is filled.
			return null;
		}
	}
	
	/**
	 * connection��������A���g�p��Ԃɂ���
	 * @param connection
	 */
	private void releaseConnection(java.sql.Connection connection){
		
		synchronized(m_Connections){
			ConnectionManager[] cma=new ConnectionManager[0];
			cma=m_Connections.toArray(cma);
			for(int i=0;i<cma.length;i++){
				if(cma[i].sqlConnection==connection){
					cma[i].using=false;
					cma[i].command=null;
					cma[i].ttl=0;
					return ;
				}
			}
		}
	}

	/**
	 * Connection�����Z�b�g����
	 * PostgreSQL DB�Ƃ̒ʐM�����s�����Ƃ��ȂǂɁA�Ăяo����A�V�K��Connection���m������B
	 * @param connection
	 * @return
	 * @throws java.sql.SQLException
	 */
	private java.sql.Connection resetConnection(java.sql.Connection connection) throws java.sql.SQLException {
		synchronized(m_Connections){
			ConnectionManager[] cma=new ConnectionManager[0];
			cma=m_Connections.toArray(cma);
			for(int i=0;i<cma.length;i++){
				if(cma[i].sqlConnection==connection){
					cma[i].sqlConnection=DriverManager.getConnection(m_ConnectionString,m_ConnectionUser,m_ConnectionPassword);
					cma[i].ttl=CONNECTION_TIMEOUT;
					return cma[i].sqlConnection;
				}
			}
			return null;
		}
	}
	
	/**
	 * IDbCommand�C���X�^���X�𓊓����A���̃C���X�^���X����`����f�[�^�x�[�X���������s����
	 * @param command
	 * @throws Exception
	 */
	public static void postCommand(IDbCommand command) throws java.sql.SQLException, org.fiap.common.FIAPException, ClassNotFoundException {
		
		if(m_Singleton==null){
			new PostgresDB();
		}
		java.sql.Connection cn=m_Singleton.getConnection(command);
		
		if(cn==null){
			throw new org.fiap.common.FIAPException("TOO MUCH CONNECTIONS");
		}
		
		try{
			if(command.execDbCommand(cn)){
				m_Singleton.releaseConnection(cn);
			}
		}catch(java.sql.SQLException e){
			// Try Re-Connection
			cn=m_Singleton.resetConnection(cn);
			try{
				if(command.execDbCommand(cn)){
					m_Singleton.releaseConnection(cn);
				}
			}catch(java.sql.SQLException ee){
				m_Singleton.releaseConnection(cn);
				throw ee;
			}
		}
	}
}
