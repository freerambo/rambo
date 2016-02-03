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

package org.fiap.common;

import org.fiap.common.PointData;
import org.fiap.storage2.Storage2Admin;
import org.fiap.types.Transport;
import org.fiap.types.Header;
import org.fiap.types.Body;
import org.fiap.types.Query;
import org.fiap.types.OK;

import org.fiap.types.Uuid;


public abstract class DataManager extends PointBusObserver implements Runnable {

	public int STORAGE_QUERY_SESSION_TIME=60; // 60 sec (modified from 10sec to 60sec 2010-06-03 by Hideya)
	public int MAX_QUERY_SESSIONS_BY_IP=5;
	
	private Thread m_Thread;
	java.util.Map<String,QueryProcessor> m_QueryProcessorMap; // (cursor, query_processor) 
	java.util.Map<String,Integer> m_InitiatorSessionUsageMap; // (initiator_ip, number of sessions)
	
	public DataManager(){
		m_QueryProcessorMap=new java.util.Hashtable<String, QueryProcessor>();
		m_InitiatorSessionUsageMap=new java.util.Hashtable<String, Integer>();
		loadConfig();
		
		m_Thread=new Thread(this);
		m_Thread.start();
	}
	
	private void loadConfig(){
		
		Storage2Admin admin=Storage2Admin.getInstance();
		String maxQuerySessionsByIP=admin.getParameter("MAX_QUERY_SESSIONS_BY_IP");
		String storageQuerySessionTime=admin.getParameter("STORAGE_QUERY_SESSION_TIME");
		
		try{
			int intMaxQuerySessionsByIP=Integer.parseInt(maxQuerySessionsByIP);
			if(0<intMaxQuerySessionsByIP){
				MAX_QUERY_SESSIONS_BY_IP=intMaxQuerySessionsByIP;
			}else{
				admin.log("org.fiap.common.DataManager.warning", "MAX_QUERY_SESSIONS_BY_IP should be a positive value.", false);
			}
		}catch(Exception e){
			admin.log("org.fiap.common.DataManager.warning", "Invalid MAX_QUERY_SESSIONS_BY_IP", false);
		}
		
		try{
			int intStorageQuerySessionTime=Integer.parseInt(storageQuerySessionTime);
			if(0<intStorageQuerySessionTime){
				STORAGE_QUERY_SESSION_TIME=intStorageQuerySessionTime;
			}else{
				admin.log("org.fiap.common.DataManager.warning", "STORAGE_QUERY_SESSION_TIME should be a positive value.", false);
			}
		}catch(Exception e){
			admin.log("org.fiap.common.DataManager.warning", "Invalid STORAGE_QUERY_SESSION_TIME", false);
		}
	}
	
	// Query Processor LifeTime Management Thread
	public void run(){
		long unitTime=1; // unitTime in sec
		while(true){
			String[] keyArray=new String[0];
			keyArray=m_QueryProcessorMap.keySet().toArray(keyArray);

			java.util.Map<String,Integer> initiatorSessionUsageMap=new java.util.Hashtable<String, Integer>();
			for(int i=0;i<keyArray.length;i++){
				QueryProcessor qp=m_QueryProcessorMap.get(keyArray[i]);
				long ttl=0;
				if(qp.getQueryType().equals("storage")){
					ttl=qp.getSessionTTL();
					ttl-=unitTime;
					qp.setSessionTTL(ttl);
				}else if(qp.getQueryType().equals("stream")){
					ttl=qp.getQueryTTL();
					ttl-=unitTime;
					qp.setQueryTTL(ttl);
				}
	
				if(ttl<0){
					qp.close();
					m_QueryProcessorMap.remove(keyArray[i]);
				}else{
					String initiator_ip=qp.getInitiatorIP();
					Integer count=initiatorSessionUsageMap.get(initiator_ip);
					if(count==null){
						initiatorSessionUsageMap.put(initiator_ip, 1);	
					}else{
						initiatorSessionUsageMap.put(initiator_ip, count+1);
					}
				}
			}
			m_InitiatorSessionUsageMap=initiatorSessionUsageMap;
			try{
				Thread.sleep(unitTime*1000);
			}catch(Exception e){}
		}
	}
	
	public Transport query(Transport t, String initiator_ip) throws org.fiap.common.FIAPException {
		
		Transport reply=new Transport();
		Header replyHeader=new Header();
		Body replyBody=new Body();
		reply.setHeader(replyHeader);
		reply.setBody(replyBody);
		
		Header reqHeader=t.getHeader();
		Query q=reqHeader.getQuery();
		
		String queryID=q.getId().getUuid();
		String queryType=q.getType().getValue();
		String cursor=null;
		if(q.getCursor()!=null){
			cursor=q.getCursor().getUuid();
		}
		
		/* query state management */
		if(queryType.equals("storage")){
			
			QueryProcessor qp=null;
			if(cursor!=null){
				qp=m_QueryProcessorMap.get(cursor);
				if(qp==null){
					String msg="Invalid cursor in query id=\""+queryID+"\" cursor=\""+cursor+"\"";
					System.err.println(msg);
					throw new org.fiap.exception.InvalidCursorException("Invalid cursor in query id=\""+queryID+"\" cursor=\""+cursor+"\"");					
				}
				qp.setSessionTTL(STORAGE_QUERY_SESSION_TIME);				
			}else{
				Integer sessionUsage=m_InitiatorSessionUsageMap.get(initiator_ip);
				if(sessionUsage==null || sessionUsage<MAX_QUERY_SESSIONS_BY_IP){
					// added 2011-03-11
					qp=createQueryProcessor(q);
					if(qp==null){
						throw new org.fiap.exception.ServerErrorException("Failed to create queryProcessor for query id=\""+q.getId().toString()+"\"");
					}
					qp.setSessionTTL(STORAGE_QUERY_SESSION_TIME);
					qp.setInitiatorIP(initiator_ip);
					if(qp.getQueryType().equals("storage")){
						cursor=((StorageQueryProcessor)qp).getCursor();
						m_QueryProcessorMap.put(cursor, qp);
					}
				}else{
					String msg="too many (cursor) sessions for initiator ip=\""+initiator_ip+"\" query id=\""+queryID+"\"";
					System.err.println(msg);
					throw new org.fiap.exception.TooManyCursorSessionsException(msg);										
				}
			}
			
			if(qp.getQueryType().equals("storage")){
				
				StorageQueryProcessor storageQP=(StorageQueryProcessor)qp;
				
				PointData[] data=storageQP.getNextDataBlock();
				for(int i=0;data!=null && i<data.length;i++){
					if(data[i].hasPointSet()){
						replyBody.addPointSet(data[i].getPointSet());
					}else if(data[i].hasPoint()){
						replyBody.addPoint(data[i].getPoint());
					}
				}
				replyHeader.setOK(new OK());
						
				if(storageQP.isEoF()){
					storageQP.close();
					m_QueryProcessorMap.remove(cursor);
					q.setCursor(null);
				}else{
					Uuid uuidCursor=new Uuid();
					uuidCursor.setUuid(cursor);
					q.setCursor(uuidCursor);
				}
				replyHeader.setQuery(q);
				return reply;
			}else{
				throw new org.fiap.exception.UnknownException("Fatal Error code=\"A\" in DataManager.");
			}
			
		}else if(queryType.equals("stream")){
			
			if(q.getTtl()==null){
				throw new org.fiap.exception.InvalidRequestException("ttl is not specified in type=\"stream\" query."); 
			}
			
			long ttl=q.getTtl().longValue();
			
			QueryProcessor qp=m_QueryProcessorMap.get(queryID);
			if(qp==null && ttl>0){
				Integer sessionUsage=m_InitiatorSessionUsageMap.get(initiator_ip);
				if(sessionUsage==null || sessionUsage<MAX_QUERY_SESSIONS_BY_IP){
					qp=createQueryProcessor(q);
					qp.setQueryTTL(ttl);
					qp.setInitiatorIP(initiator_ip);
					m_QueryProcessorMap.put(queryID, qp);
				}else{
					String msg="too many (TRAP) queries for initiator ip=\""+initiator_ip+"\" query id=\""+queryID+"\"";
					System.err.println(msg);
					throw new org.fiap.exception.TooManyTrapQueriesException(msg);										
				}
			}else if(ttl>0){
				qp.setQueryTTL(ttl);
			}else{
				qp.close();
				m_QueryProcessorMap.remove(queryID);
			}			
			replyHeader.setOK(new OK());
			replyHeader.setQuery(q);
			return reply;
		}else{
			throw new org.fiap.exception.UnknownException("Fatal Error code=\"B\" in DataManager.");
		}
		
	}
	
	/**
	 * createQueryProcessor is an abstract method
	 * it should parse the query and provide an 
	 * appropriate queryProcessor object
	 * for inherited (i.e., concrete) DataManager
	 * @return
	 */
	public abstract QueryProcessor createQueryProcessor(Query q) throws org.fiap.common.FIAPException;
	
	public void recvUpdate(PointData o){
		
		// 1. Send TRAP
		String[] keyArray=new String[0];
		keyArray=m_QueryProcessorMap.keySet().toArray(keyArray);
		for(int i=0;i<keyArray.length;i++){
			QueryProcessor qp=m_QueryProcessorMap.get(keyArray[i]);
			if(qp.getQueryType().equals("stream")){
				StreamQueryProcessor streamQP=(StreamQueryProcessor)qp;
				PointData[] array=new PointData[1];
				array[0]=o;
				streamQP.postData(array); //   should return control as fast as possible 
			}
		}

		// 2. Store data
		storeData(o);
	}
	
	/**
	 * storeData receives the data posted to the PointBus.
	 * that data should be stored in an appropriate format
	 * by inherited (i.e., concrete) DataManager
	 */
	public abstract void storeData(PointData o);
	
}
