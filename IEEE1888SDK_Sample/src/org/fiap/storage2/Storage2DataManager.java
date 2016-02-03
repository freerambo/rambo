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

import org.fiap.common.DataManager;
import org.fiap.common.PointData;
import org.fiap.common.QueryProcessor;
import org.fiap.types.Key;
import org.fiap.types.Point;
import org.fiap.types.PointSet;
import org.fiap.types.Query;
import org.fiap.types.Value;

public class Storage2DataManager extends DataManager {

	public static int DEFAULT_ACCEPTABLE_SIZE=100; // modified from 10000 to 100 (2010-08-03 by Hideya)
	public static int MAX_ACCEPTABLE_SIZE=1000;
	
	/**
	 * FIAPポイントのIDに対する最新値の格納用連想配列
	 */
	protected java.util.Hashtable<String,Value> m_ValueMap=new java.util.Hashtable<String,Value>(); 

	/**
	 * createQueryProcessor should be called only from the super class (DataManager)
	 * createQueryProcessor is an abstract method
	 * it should parse the query and provide an 
	 * appropriate queryProcessor object
	 * for StorageDataManager
	 */
	@Override
	public QueryProcessor createQueryProcessor(Query q) throws org.fiap.common.FIAPException {
		
		String queryType=q.getType().getValue();
		Key[] keys=q.getKey();
			
		if(keys.length==0){
			throw new org.fiap.exception.QueryNotSupportedException("No keys are specified by the query.");
		}
		if(queryType.equals("storage")){
			return new Storage2QueryProcessor(this,q,keys);
		}else if(queryType.equals("stream")){
			return new Stream2QueryProcessor(this,q,keys);
		}else{
			throw new org.fiap.exception.InvalidRequestException("type=\""+queryType+"\" is not allowed.");
		}
	}
		
	java.util.Map<String, String> m_PointTreeMap; // (child, this)

	public Storage2DataManager(){
		super();
		
		loadConfig();
		m_PointTreeMap=new java.util.Hashtable<String, String>();
	}
	
	private void loadConfig(){
		
		Storage2Admin admin=Storage2Admin.getInstance();
		String defaultAcceptableSize=admin.getParameter("DEFAULT_ACCEPTABLE_SIZE");
		String maxAcceptableSize=admin.getParameter("MAX_ACCEPTABLE_SIZE");
		
		try{
			int intDefaultAcceptableSize=Integer.parseInt(defaultAcceptableSize);
			if(0<intDefaultAcceptableSize){
				DEFAULT_ACCEPTABLE_SIZE=intDefaultAcceptableSize;
			}else{
				admin.log("org.fiap.storage2.Storage2DataManager.warning", "DEFAULT_ACCEPTABLE_SIZE should be a positive value.", false);
			}
		}catch(Exception e){
			admin.log("org.fiap.storage2.Storage2DataManager.warning", "Invalid DEFAULT_ACCEPTABLE_SIZE", false);
		}
		
		try{
			int intMaxAcceptableSize=Integer.parseInt(maxAcceptableSize);
			if(0<intMaxAcceptableSize){
				MAX_ACCEPTABLE_SIZE=intMaxAcceptableSize;
			}else{
				admin.log("org.fiap.storage2.Storage2DataManager.warning", "MAX_ACCEPTABLE_SIZE should be a positive value.", false);
			}
		}catch(Exception e){
			admin.log("org.fiap.storage2.Storage2DataManager.warning", "Invalid MAX_ACCEPTABLE_SIZE", false);
		}
	}

	@Override
	public void storeData(PointData o) {
				
		if(o==null){
			return ;
		}
		
		// m_ValueMap (最新値マップ)の更新
		parseUpdate(o);
		
		try{
			// Added point support (thus -- the following programming is not good -- it should be revised.)
			if(o.hasPoint()){
				Point p=o.getPoint();
				String id=p.getId().toString();
				String cache=m_PointTreeMap.get(id);
				if(cache==null){
					m_PointTreeMap.put(id, "");  // "" means that it has marked as Root
					PostgresDB.postCommand(new PointSetTreeRootTryUpdate(id,true));
				}
				
				java.util.Map<java.util.Calendar,Value> valueTimeMap=new java.util.Hashtable<java.util.Calendar, Value>();
				Value[] values=p.getValue();
				for(int i=0;i<values.length;i++){
					java.util.Calendar time=values[i].getTime();
					valueTimeMap.put(time, values[i]);
				}
			
				// insert or update the pointvalue (future plan -- it should be integrated to PointValueUpdate to increase the throughput)
				java.util.Calendar[] time=new java.util.Calendar[0];
				time=valueTimeMap.keySet().toArray(time);
				for(int i=0;i<time.length;i++){
					Value v=valueTimeMap.get(time[i]);
					String value=v.getString();
					PostgresDB.postCommand(new PointValueUpdate(id,time[i],null,value));
				}

			}else if(o.hasPointSet()){
				PointSet ps=o.getPointSet();
				
				String id=ps.getId().toString();
				PointSet[] childPointSets=ps.getPointSet();
				Point[] childPoints=ps.getPoint();
				
				String cache=m_PointTreeMap.get(id);
				if(cache==null){
					m_PointTreeMap.put(id, "");  // "" means that it has marked as Root
					PostgresDB.postCommand(new PointSetTreeRootTryUpdate(id,false));
				}
				
				for(int i=0;childPointSets!=null && i<childPointSets.length;i++){
					PointSet cps=childPointSets[i];
					String cid=cps.getId().toString();
					cache=m_PointTreeMap.get(cid);
					if(cache==null || !cache.equals(id)){
						m_PointTreeMap.put(cid, id);
						// insert or update the pointsettree (with ispoint="false")
						PostgresDB.postCommand(new PointSetTreeUpdate(cid,id,false));
					}
					storeData(new PointData(cps));  // Recursive Parse					
				}

				for(int i=0;childPoints!=null && i<childPoints.length;i++){
					Point cp=(Point)childPoints[i];
					String cid=cp.getId().toString();
					cache=m_PointTreeMap.get(cid);
					if(cache==null || !cache.equals(id)){
						m_PointTreeMap.put(cid, id);
						// insert or update the pointsettree (with ispoint="true")
						PostgresDB.postCommand(new PointSetTreeUpdate(cid,id,true));
					}
					
					java.util.Map<java.util.Calendar,Value> valueTimeMap=new java.util.Hashtable<java.util.Calendar, Value>();
					Value[] values=cp.getValue();
					if(values==null){
						continue;
					}
					
					for(int k=0;k<values.length;k++){
						java.util.Calendar time=values[k].getTime();
						valueTimeMap.put(time, values[k]);
					}
										
					// insert or update the pointvalue (future plan -- it should be integrated to PointValueUpdate to increase the throughput)
					java.util.Calendar[] time=new java.util.Calendar[0];
					time=valueTimeMap.keySet().toArray(time);
					for(int k=0;k<time.length;k++){
						Value v=valueTimeMap.get(time[k]);
						String value=v.getString();
						PostgresDB.postCommand(new PointValueUpdate(cid,time[k],null,value));
					}
					
				}				

			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	/**
	 * PointBus から受信した更新信号を解析し、最新値として m_Value に保存する
	 * @param o
	 */
	private void parseUpdate(PointData o) {

		Storage2Admin admin=Storage2Admin.getInstance();
		if(admin.debug()){
			admin.log("StorageDataManager.parseUpdate", "", false);
		}

		if(o.hasPoint()){
			Point p=o.getPoint();

			String id=p.getId().toString();
			Value[] values=p.getValue();
			if(values!=null && values.length>0){
				if(admin.debug()){
					admin.log("StorageDataManager.parseUpdate.valueMapUpdate", "id="+id+"; value="+values[values.length-1], false);
				}
				m_ValueMap.put(id, values[values.length-1]);
			}

		}else if(o.hasPointSet()){
			PointSet ps=o.getPointSet();
			
			PointSet[] pointSets=ps.getPointSet();
			Point[] points=ps.getPoint();
			for(int i=0;pointSets!=null && i<pointSets.length;i++){
				parseUpdate(new PointData(pointSets[i]));
			}
			for(int i=0;points!=null && i<points.length;i++){
				parseUpdate(new PointData(points[i]));
			}
		}
	}

}
