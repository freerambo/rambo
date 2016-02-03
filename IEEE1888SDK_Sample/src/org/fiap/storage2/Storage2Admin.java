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

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.fiap.common.FIAPException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Storage2(PostgreSQL使用)の設定ファイル読み込み＆ログ出力の管理を行う<br />
 * <br />
 * Storage2の初期化時に、parseConfigFileメソッドを呼び出して、設定ファイルを読み込むこと。<br />
 * Singletonとして実装されているため、利用するときには<br />
 *  Storage2Admin admin=Storage2Admin.getInstance();<br />
 * の手順を踏むこと<br />
 * 
 * @author Hideya Ochiai
 * create 2010-01-14
 * update 2011-04-07
 * update 2013-04-10
 */
public class Storage2Admin {
	
	/**
	 * Singletonオブジェクト格納用
	 */
	private static Storage2Admin m_Singleton;
	
	/**
	 * コンストラクタ<br />
	 * m_Singleton==null のときに getInstance() から呼び出される
	 */
	private Storage2Admin(){
		m_Singleton=this;
	}
	
	/**
	 * グローバルユニーク(=Singleton)なStorageAdminオブジェクトの取得<br />
	 * @return グローバルユニークなStorageAdminオブジェクト
	 */
	public static Storage2Admin getInstance(){
		if(m_Singleton==null){
			new Storage2Admin();
		}
		return m_Singleton;
	}
	
	/**
	 * デバッグモード
	 */
	private boolean m_Debug=false;

	/**
	 *  ログ出力のファイルパス (nullの場合は出力しない)
	 */
	private String m_LogPath=null;
	
	/**
	 * acmエレメント
	 */
	private Element m_ACM=null;
	
	/**
	 * fetchエレメントの集合
	 */
	private Element[] m_FETCHs=null;
	
	/**
	 * writeエレメントの集合
	 */
	private Element[] m_WRITEs=null;
	
	/**
	 * parameterの集合
	 */
	private java.util.Hashtable<String,String> m_Parameters=new java.util.Hashtable<String,String>();
	
	/**
	 * 
	 * @param configPath
	 * @throws FIAPException
	 */
	public void parseConfigFile(String configPath) throws FIAPException {
		
		Document doc=org.fiap.util.Xml.parseXmlFile(configPath);
		
		if(doc==null){
			String msg="Storage2Admin.parseConfigFile: Fatal error while parsing the configuration file.";
			System.err.println(msg);
			throw new Storage2ConfigException(msg);
		}
		Element root=doc.getDocumentElement();
		
		if(!root.getTagName().equals("storage2")){
			String msg="Storage2Admin.parseConfigFile:Fatal error in configuration; It must start with storage2 element.";
			System.err.println(msg);
			throw new Storage2ConfigException(msg);
		}
		
		m_LogPath=root.getAttribute("logpath");
		if(m_LogPath.equals("")){
			String msg="Storage2Admin.parseConfigFile: Warning in configuration; logpath is not specified. log output will be disabled.";
			System.err.println(msg);
			m_LogPath=null;
		}
				
		String debug=root.getAttribute("debug");
		if(debug.equals("true")){
			m_Debug=true;
		}else{
			m_Debug=false;
		}
		
		java.util.ArrayList<Element> fetchArray=new java.util.ArrayList<Element>();
		java.util.ArrayList<Element> writeArray=new java.util.ArrayList<Element>();
		Node n0=root.getFirstChild();
		while(n0!=null){
			if(n0.getNodeType()==Node.ELEMENT_NODE){
				Element e0=(Element)n0;
				String tagName=e0.getTagName();
				if(tagName.equals("acm")){
					m_ACM=e0;
				}
				if(tagName.equals("fetch")){
					fetchArray.add(e0);					
				}
				if(tagName.equals("write")){
					writeArray.add(e0);
				}
				if(tagName.equals("parameter")){
					String name=e0.getAttribute("name");
					String value=e0.getAttribute("value");
					m_Parameters.put(name, value);
				}
			}
			n0=n0.getNextSibling();
		}
		m_FETCHs=new Element[0];
		m_FETCHs=fetchArray.toArray(m_FETCHs);
		m_WRITEs=new Element[0];
		m_WRITEs=writeArray.toArray(m_WRITEs);
	}
	
	
	/**
	 * ログ出力のためのファイルハンドル
	 */
	private BufferedWriter m_LogWriter;
	
	/**
	 * ログ出力メソッド
	 * @param eventName -- イベント名
	 * @param message -- メッセージ
	 * @param flush -- flushes if true
	 */
	public void log(String eventName, String message, boolean flush){

		/* if m_LogPath == null, it means log output is disabled. */
		if(m_LogPath==null){
			return ;
		}
		
		try{
			if(m_LogWriter==null){
				m_LogWriter=new BufferedWriter(new FileWriter(m_LogPath,true));
			}
		
			String time=org.fiap.util.W3CTimestamp.toString(java.util.Calendar.getInstance());
			m_LogWriter.write("\""+time+"\",\""+eventName+"\",\""+message+"\"\r\n");
			if(flush){
				m_LogWriter.flush();
			}
			
		}catch(Exception e){
			m_LogWriter=null;
			System.err.println("Storage2Admin.log: Fatal error in log output.");
		}
	}
	
	/**
	 * 設定ファイルの debug 指定を取得する
	 * @return
	 */
	public boolean debug(){
		return m_Debug;
	}	

	
	/**
	 * 設定ファイルのacm要素を取得する
	 */
	public org.w3c.dom.Element getACM(){
		return m_ACM;
	}
	
	/**
	 * 設定ファイルのfetch要素の配列 を取得する
	 * @return
	 */
	public org.w3c.dom.Element[] getFETCHs(){		
		return m_FETCHs;
	}
	
	/**
	 * 設定ファイルのwrite要素の配列 を取得する
	 * @return
	 */
	public org.w3c.dom.Element[] getWRITEs(){		
		return m_WRITEs;
	}
	
	/**
	 * 設定ファイルのパラメータ値を取得する 
	 * @return
	 */
	public String getParameter(String name){
		return m_Parameters.get(name);
	}
}
