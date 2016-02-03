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
import java.sql.SQLException;
import java.sql.ResultSet;


import org.fiap.common.FIAPException;
import org.fiap.common.PointData;
import org.fiap.types.PointSet;
import org.fiap.types.Point;

import org.fiap.types.Query;
import org.fiap.types.Key;
import org.fiap.types.Value;

public class Storage2QueryProcessor extends org.fiap.common.StorageQueryProcessor implements IDbCommand {

	Storage2DataManager m_DataManager;
	Query m_Query;
	
	int m_AcceptableSize;  /* i.e., BlockSize */
	
	boolean m_Valid;
	boolean m_PreEoF;
	boolean m_EoF;
	
	Key[] m_Keys;
	
	protected Storage2QueryProcessor(Storage2DataManager dm,Query q, Key[] keys){
		super();
		
		m_DataManager=dm;
		m_Query=q;
		m_Keys=keys;
		
		Storage2Admin admin=Storage2Admin.getInstance();
		
		Integer acceptableSize=null;
		if(q.getAcceptableSize()!=null){
			acceptableSize=q.getAcceptableSize().intValue();
		}else{		
			acceptableSize=Storage2DataManager.DEFAULT_ACCEPTABLE_SIZE;
			if(admin.debug()){
				admin.log("org.fiap.storage2.Storage2QueryProcessor.info","acceptableSize is not set in query. Use "+Storage2DataManager.DEFAULT_ACCEPTABLE_SIZE+" as default.",false);
			}
		}
		if(acceptableSize>Storage2DataManager.MAX_ACCEPTABLE_SIZE){
			acceptableSize=Storage2DataManager.MAX_ACCEPTABLE_SIZE;
			if(admin.debug()){
				admin.log("org.fiap.storage2.Storage2QueryProcessor.info","acceptableSize has exceeded the maximum limit. Use "+Storage2DataManager.MAX_ACCEPTABLE_SIZE+".",false);
			}
		}
		m_AcceptableSize=acceptableSize;
		m_Valid=true;
		m_EoF=false;
	}
	
	@Override
	public PointData[] getNextDataBlock() throws FIAPException {
		
		if(!m_Valid || m_EoF){
			return null;
		}
		try{
			m_Exception=null;
			PostgresDB.postCommand(this);
			if(m_Exception!=null){
				throw m_Exception;
			}
			
			PointData[] retArray=new PointData[0];
			retArray=m_NextDataBlock.toArray(retArray);
			if(m_KeyIndex>=m_Keys.length){
				m_EoF=true;
			}
			return retArray;
		}catch(FIAPException ge){
			throw ge;
		}catch(Exception e){			
			StackTraceElement[] stackTraceElements=e.getStackTrace();
			String errorStackTrace="";
			for(int i=0;i<stackTraceElements.length;i++){
				errorStackTrace=errorStackTrace+"; "+stackTraceElements[i];
			}
			throw new org.fiap.exception.ServerErrorException("StackTrace: " + errorStackTrace);			
		}
	}

	@Override
	public boolean isEoF() {
		if(m_Valid){
			return m_EoF;
		}
		return true;
	}

	@Override
	public void close() {
		m_EoF=true;
		m_Valid=false;
	}
	
	int m_KeyIndex;
	java.sql.Statement m_Statement=null;
	String m_CursorName=null;
	Point m_WorkingPoint=null;
	java.util.ArrayList<PointData> m_NextDataBlock=null;
	int m_AppendedSize;
	
	org.fiap.common.FIAPException m_Exception=null;
	public boolean execDbCommand(Connection connection) throws SQLException {
	
		m_NextDataBlock=new java.util.ArrayList<PointData>();
		m_AppendedSize=0;
		
		try{
			MAIN : while(m_KeyIndex<m_Keys.length){
			
				Key key=m_Keys[m_KeyIndex];
				if(m_CursorName==null){
					m_Statement=connection.createStatement();
					// 1. Read the ID and check the object type (point or pointSet)
					String id=key.getId().toString();
					boolean ispoint=false;
					String sql="SELECT id,parent,ispoint FROM pointsettree WHERE id=\'"+id+"\'";
					ResultSet rset=m_Statement.executeQuery(sql);
					if(rset.next()){
						ispoint=rset.getBoolean("ispoint");
					}else{
						m_Exception=new org.fiap.exception.PointNotFoundException(id+" was not found.");
						m_Statement.close();
						return true;
					}
					rset.close();
					if(!ispoint){
						// 2. for pointSet object -- read it's children, append it to the result 
						PointSet ps=new PointSet();
						ps.setId(new org.apache.axis2.databinding.types.URI(id));
						m_NextDataBlock.add(new PointData(ps));
						
						sql="SELECT id,parent,ispoint FROM pointsettree WHERE parent=\'"+id+"\'";
						rset=m_Statement.executeQuery(sql);
						while(rset.next()){
							String cid=rset.getString("id");
							boolean cispoint=rset.getBoolean("ispoint");
							if(cispoint){
								Point c=new Point();
								c.setId(new org.apache.axis2.databinding.types.URI(cid));
								ps.addPoint(c);
							}else{
								PointSet c=new PointSet();
								c.setId(new org.apache.axis2.databinding.types.URI(cid));
								ps.addPointSet(c);							
							}
						}
						rset.close();
						
						// finished for the "key" ==> move to the next;
						m_Statement.close();
						m_KeyIndex++;
						continue MAIN;
						
					}else{
						// 3. for point object -- parse the detailed query
						String attrName=key.getAttrName().getValue();
						if(attrName==null){
							m_Exception=new org.fiap.exception.InvalidRequestException("attrName is not specified in the query key.");
							m_Statement.close();
							return true;							
						}else if(!attrName.equals("time")){
							m_Exception=new org.fiap.exception.InvalidRequestException("attrName=\'"+attrName+"\' is not allowed for type=\'storage\' query.");
							m_Statement.close();
							return true;
						}
						String eq=key.getEq();
						String neq=key.getNeq();
						String lt=key.getLt();
						String gt=key.getGt();
						String lteq=key.getLteq();
						String gteq=key.getGteq();
						
						String select=null;
						if(key.getSelect()!=null){
							select=key.getSelect().toString();
							if(!(select.equals("maximum") || select.equals("minimum"))){
								m_Exception=new org.fiap.exception.QueryNotSupportedException("select=\""+select+"\" is not supported query for this database.");
								m_Statement.close();
								return true;
							}
						}
						if(key.getTrap()!=null){ 
							m_Exception=new org.fiap.exception.InvalidRequestException("trap is not defined in type=\"storage\" query.");
							m_Statement.close();
							return true;
						}
						
						// 4. for "select" or "eq" query -- read one line associated to the ID and select type
						if(select!=null || eq!=null){
							Point p=new Point();
							p.setId(new org.apache.axis2.databinding.types.URI(id));
							m_NextDataBlock.add(new PointData(p));
							
							sql="SELECT id,time,value FROM pointvalue "+createWHERE(id,eq,neq,lt,gt,lteq,gteq,select);					
							rset=m_Statement.executeQuery(sql);
							if(rset.next()){
								java.sql.Timestamp t=rset.getTimestamp("time");
								// TODO: timezone should come from the configuration.
								java.util.Calendar time=java.util.Calendar.getInstance();
								time.setTime(t);
								String value=rset.getString("value");
								
								Value v=new Value();
								v.setTime(time);
								v.setString(""+value);
								p.addValue(v);
								++m_AppendedSize;							
							}
							rset.close();
							// finished for the "key" ==> move to the next;
							m_Statement.close();
							m_KeyIndex++;
							if(++m_AppendedSize>=m_AcceptableSize){ // but, if value count is full => return 
								return true;
							}
							continue MAIN;
						}
						
						// 5. setup a new cursor and submit a declare.
						m_CursorName="cursorid"+java.util.UUID.randomUUID().toString().replaceAll("-","");
						sql="DECLARE "+m_CursorName+" CURSOR FOR SELECT id,time,value FROM pointvalue " + createWHERE(id,eq,neq,lt,gt,lteq,gteq,select);					
						m_Statement.execute("START TRANSACTION");
						m_Statement.execute(sql);
						
						m_WorkingPoint=new Point();
						m_WorkingPoint.setId(new org.apache.axis2.databinding.types.URI(id));
					}
				}
				
				if(m_CursorName!=null){
					Point p=new Point();
					p.setId(m_WorkingPoint.getId());
					p.setValue(m_WorkingPoint.getValue());
					m_NextDataBlock.add(new PointData(p));
					while(true){
						String sql="FETCH NEXT FROM "+m_CursorName;
						ResultSet rset=m_Statement.executeQuery(sql);
						if(rset.next()){							
							java.sql.Timestamp t=rset.getTimestamp("time");
							// TODO: timezone should come from the configuration.
							java.util.Calendar time=java.util.Calendar.getInstance();
							time.setTime(t);
							String value=rset.getString("value");
							
							Value v=new Value();
							v.setTime(time);
							v.setString(""+value);
							p.addValue(v);

							if(++m_AppendedSize>=m_AcceptableSize){
								rset.close();
								return false;
							}
						}else{
							rset.close();
							m_CursorName=null;
							m_Statement.execute("COMMIT;");
							// finished for the "key" ==> move to the next;
							m_Statement.close();
							m_KeyIndex++;
							continue MAIN;
						}
						rset.close();
					}
				}
			}
		}catch(Exception e){
		//	e.printStackTrace();
			if(m_Statement!=null){
				try{
					m_Statement.execute("ROLLBACK;");
				}catch(Exception ee){ }
			}
			StackTraceElement[] stackTraceElements=e.getStackTrace();
			String errorStackTrace="";
			for(int i=0;i<stackTraceElements.length;i++){
				errorStackTrace=errorStackTrace+"; "+stackTraceElements[i];
			}

			m_Exception=new org.fiap.exception.ServerErrorException("Server Error: "+errorStackTrace);
		}
		return true;		
	}

	
	private String createWHERE(String id, String eq, String neq, String lt, String gt, String lteq, String gteq, String select){
		
		StringBuilder sb=new StringBuilder();
		sb.append("WHERE id='"+id+"'");
		if(eq!=null){
			sb.append(" AND time='"+eq+"'");
		}
		if(neq!=null){
			sb.append(" AND NOT time='"+neq+"'");
		}
		if(lt!=null){
			sb.append(" AND time<'"+lt+"'");
		}
		if(gt!=null){
			sb.append(" AND time>'"+gt+"'");
		}
		if(lteq!=null){
			sb.append(" AND time<='"+lteq+"'");
		}
		if(gteq!=null){
			sb.append(" AND time>='"+gteq+"'");
		}
		if(select!=null){
			if(select.equals("maximum")){
				sb.append(" ORDER BY time DESC LIMIT 1");
			}else if(select.equals("minimum")){
				sb.append(" ORDER BY time LIMIT 1");
			}else{
				System.err.println("unrecognized select");
			}
		}else{
			sb.append(" ORDER BY time");
		}
		return sb.toString();
	}
}
