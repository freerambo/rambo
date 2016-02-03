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

import java.util.Calendar;

import org.apache.axis2.context.MessageContext;
import org.fiap.types.Transport;
import org.fiap.types.Header;
import org.fiap.types.Body;
import org.fiap.types.Query;
import org.fiap.types.QueryType;
import org.fiap.types.Error;
import org.fiap.types.Key;

import org.fiap.types.PointSet;
import org.fiap.types.Point;
import org.fiap.types.Value;

import org.fiap.common.FIAPUnit;
import org.fiap.common.WriteManager;
import org.fiap.soap.QueryRQ;
import org.fiap.soap.QueryRS;
import org.fiap.soap.DataRQ;
import org.fiap.soap.DataRS;
import org.fiap.storage2.acm.ACM;

import org.fiap.common.FIAPException;


/**
 * FIAP-Storage実装へのインターフェスを提供する
 * Axis Webサービスで公開される
 * 
 * @author Hideya Ochiai
 * create 2009-12
 * update 2011-09-04
 */
public class Storage2 implements org.fiap.soap.FIAPWSSkeletonInterface {

	protected static FIAPUnit m_GUTUnit=null;
	private int MAX_KEY_COUNTS=6000;
	private int MAX_VALUE_COUNTS=5000;

	/**
	 * 初期化メソッド
	 * Generic なComponent に StorageDataManager と WriteManagerをアタッチする
	 */
	private void initialize() throws org.fiap.common.FIAPException, org.apache.axis2.databinding.types.URI.MalformedURIException{
		
		// 1. load configuration
		String configPath=System.getenv("CATALINA_BASE")+"/webapps/axis2/WEB-INF/conf/storage2/storage2.xml";
		Storage2Admin admin=Storage2Admin.getInstance();
		admin.parseConfigFile(configPath);
		
		// 2. Parse configuration (MAX_KEY_COUNTS, MAX_VALUE_COUNTS)
		String maxKeyCounts=admin.getParameter("MAX_KEY_COUNTS");
		String maxValueCounts=admin.getParameter("MAX_VALUE_COUNTS");
		
		try{
			int intMaxKeyCounts=Integer.parseInt(maxKeyCounts);
			if(0<intMaxKeyCounts){
				MAX_KEY_COUNTS=intMaxKeyCounts;
			}else{
				admin.log("org.fiap.storage2.Storage2.warning", "MAX_KEY_COUNTS should be specified with a positive value.", false);
			}
		}catch(Exception e){
			admin.log("org.fiap.storage2.Storage2.warning", "MAX_KEY_COUNTS should be specified with a positive value.", false);
		}
		
		try{
			int intMaxValueCounts=Integer.parseInt(maxValueCounts);
			if(0<intMaxValueCounts){
				MAX_VALUE_COUNTS=intMaxValueCounts;
			}else{
				admin.log("org.fiap.storage2.Storage2.warning", "MAX_VALUE_COUNTS should be specified with a positive value.", false);
			}
		}catch(Exception e){
			admin.log("org.fiap.storage2.Storage2.warning", "MAX_VALUE_COUNTS should be specified with a positive value.", false);
		}

		// 3. initialization of FIAPUnit
		m_GUTUnit=new FIAPUnit(
				new Storage2DataManager(),
				new WriteManager(),
				new Storage2App()
				);
	}
	
	/**
	 * FIAPインタフェースのqueryメソッド
	 * @param t
	 * @return
	 */
	public QueryRS query(QueryRQ q) {
		 
		Storage2Admin admin=Storage2Admin.getInstance();
		String transaction=java.util.UUID.randomUUID().toString();
				
		Transport r=null;
		Transport t=null;
		Header h=null;
		Query query=null;
		QueryType queryType=null; 
		Key[] keys=null;
		try{
			if(m_GUTUnit==null){ 
				initialize(); 
			}
			
			admin.log("fiap.server.query.begin", "transaction:"+transaction+";", false);
			
			// for Remote IP, Certificate Abstraction
			String initiator_ip=null;
			String initiator_id=null;  // SAN name
			try{
			  MessageContext mc=MessageContext.getCurrentMessageContext();
			  initiator_ip=(String)mc.getProperty(MessageContext.REMOTE_ADDR);
			  int certsCount=Integer.parseInt(mc.getProperty("certsCount").toString());
			  if(certsCount==0){
				  initiator_id="anonymous";
			  }
			  if(certsCount>1){
				  throw new org.fiap.exception.CertificateException("Too many certificates at initiator: certsCount="+certsCount);
			  }
			  
			  for(int i=0;i<certsCount;i++){
				  java.util.Collection col=(java.util.Collection)mc.getProperty("SAN"+i);
				  java.util.Iterator itr=col.iterator();
				  int san_count=0;
				  while(itr.hasNext()){
					  if(san_count!=0){
						  throw new org.fiap.exception.CertificateException("Too many SANs at initiator's certificate");						  
					  }
					  java.util.List list=(java.util.List)itr.next();
					  initiator_id=(String)list.get(1);
					  san_count++;
				  }
			  }
			}catch(Exception e){}
			
			// Pre-Parse Transport (including Point ID abstraction: i.e., key id abstraction)
			String[] point_ids=new String[0];
			java.util.ArrayList<String> arr_ids=new java.util.ArrayList<String>(); 
			t=q.getTransport();
			if(t==null){
				throw new org.fiap.exception.InvalidRequestException("transport is not specified.");
			}
			
			h=t.getHeader();
			if(h==null){
				throw new org.fiap.exception.InvalidRequestException("header is not specified.");
			}
			query=h.getQuery();
			if(query==null){
				throw new org.fiap.exception.InvalidRequestException("query is not specified.");
			}
			queryType=query.getType();
			keys=query.getKey();
			if(keys==null || keys.length==0){
				throw new org.fiap.exception.QueryNotSupportedException("no keys are specified in the query (this type of query is not supported).");
			}
			if(keys.length>MAX_KEY_COUNTS){
				throw new org.fiap.exception.TooManyKeysException("The number of keys exceeded the server limit MAX_KEY_COUNTS=\""+MAX_KEY_COUNTS+"\".");
			}
			for(int i=0;i<keys.length;i++){
				arr_ids.add(keys[i].getId().toString());
			}
			point_ids=arr_ids.toArray(point_ids);
			
			// For debug messages
			if(admin.debug()){	
				StringBuilder sb=new StringBuilder();
				try{
					sb.append("transaction:");
					sb.append(transaction);
					sb.append("; ");					
					sb.append("initiator:");
					sb.append(initiator_id);
					sb.append("; ");					
					sb.append("id:");
					sb.append(query.getId().toString());
					sb.append("; ");
					if(query.getType()!=null){
						sb.append("type:");
						sb.append(query.getType().toString());
						sb.append("; ");
					}
					if(query.getAcceptableSize()!=null){
						sb.append("acceptableSize:");
						sb.append(query.getAcceptableSize().toString());
						sb.append("; ");
					}
					if(query.getCursor()!=null){
						sb.append("cursor:");
						sb.append(query.getCursor().toString());
						sb.append("; ");
					}
					if(query.getTtl()!=null){
						sb.append("ttl:");
						sb.append(query.getTtl().toString());
						sb.append("; ");
					}
					if(query.getCallbackData()!=null){
						sb.append("callbackData:");
						sb.append(query.getCallbackData().toString());
						sb.append("; ");
					}
					if(query.getCallbackControl()!=null){
						sb.append("callbackControl:");
						sb.append(query.getCallbackControl().toString());
						sb.append("; ");
					}
					for(int i=0;i<keys.length;i++){
						sb.append("key["+i+"]:(");
						sb.append("id:"); sb.append(keys[i].getId()); sb.append("; ");
						sb.append("eq:"); sb.append(keys[i].getEq()); sb.append("; ");
						sb.append("neq:"); sb.append(keys[i].getNeq()); sb.append("; ");
						sb.append("lt:"); sb.append(keys[i].getLt()); sb.append("; ");
						sb.append("gt:"); sb.append(keys[i].getGt()); sb.append("; ");
						sb.append("lteq:"); sb.append(keys[i].getLteq()); sb.append("; ");
						sb.append("gteq:"); sb.append(keys[i].getGteq()); sb.append("; ");
						if(keys[i].getSelect()!=null){
							sb.append("select:"); sb.append(keys[i].getSelect().toString()); sb.append("; ");
						}else{
							sb.append("select:null; ");
						}
						sb.append("); ");
					}
					admin.log("fiap.server.query.message", sb.toString(), true);
				}catch(Exception e){
					admin.log("fiap.server.query.message", "Failed while parsing the request", false);
					admin.log("fiap.server.query.message", sb.toString(), true);
				}
			}
			
			// Check ACCESS CONTROL POLICY
			String method=null;
			if(queryType==QueryType.storage){
				method="FETCH";
			}else if(queryType==QueryType.stream){
				method="TRAP";				
			}
			if(ACM.getInstance().checkAccessControlPolicy(initiator_id, initiator_ip, method, point_ids)){
				// if passed ..
				r=m_GUTUnit.query(t,initiator_ip);
			}

		}catch(FIAPException e){
			
			r=new Transport();
			Header header=new Header();
			Error error=new Error();
			error.setString(e.getMessage());
			error.setType(e.getType());
			header.setError(error);
			if(query!=null){
				header.setQuery(query);
			}
			r.setHeader(header);
			admin.log("fiap.server.query.error", "transaction:"+transaction+"; type:"+e.getType()+"; message=\'"+e.getMessage()+"\'", true);

		}catch(Exception e){
			e.printStackTrace();

			StackTraceElement[] stackTraceElements=e.getStackTrace();
			String errorStackTrace="";
			for(int i=0;i<stackTraceElements.length;i++){
				errorStackTrace=errorStackTrace+"; "+stackTraceElements[i];
			}
			
			r=new Transport();
			Header header=new Header();
			Error error=new Error();
			error.setString("StackTrace: "+errorStackTrace);
			error.setType("UNKNOWN");
			header.setError(error);
			if(query!=null){
				header.setQuery(query);
			}
			r.setHeader(header);
			admin.log("fiap.server.query.error", "transaction:"+transaction+"; type:"+e.getClass().getName()+"; message=\'"+e.getMessage()+"\'", true);
		}finally{
			admin.log("fiap.server.query.end", "transaction:"+transaction+";", true);
		}

		QueryRS result=new QueryRS();
		result.setTransport(r);
		return result;
	}
	
	/**
	 * FIAPインタフェースのdataメソッド
	 */
	public DataRS data(DataRQ d) {

		Storage2Admin admin=Storage2Admin.getInstance();
		String transaction=java.util.UUID.randomUUID().toString();

		Transport r=null;
		Transport t=null;
		Body b=null;
		try{
			if(m_GUTUnit==null){ 
				initialize(); 
			}

			admin.log("fiap.server.data.begin", "transaction:"+transaction+"; ", false);

			// for Remote IP, Certificate Abstraction
			String initiator_ip=null;
			String initiator_id=null;  // SAN name
			try{
			  MessageContext mc=MessageContext.getCurrentMessageContext();
			  initiator_ip=(String)mc.getProperty(MessageContext.REMOTE_ADDR);
			  int certsCount=Integer.parseInt(mc.getProperty("certsCount").toString());
			  if(certsCount==0){
				  initiator_id="anonymous";
			  }
			  if(certsCount>1){
				  throw new org.fiap.exception.CertificateException("Too many certificates at initiator: certsCount="+certsCount);
			  }
			  
			  for(int i=0;i<certsCount;i++){
				  java.util.Collection col=(java.util.Collection)mc.getProperty("SAN"+i);
				  java.util.Iterator itr=col.iterator();
				  int san_count=0;
				  while(itr.hasNext()){
					  if(san_count!=0){
						  throw new org.fiap.exception.CertificateException("Too many SANs at initiator's certificate");						  
					  }
					  java.util.List list=(java.util.List)itr.next();
					  initiator_id=(String)list.get(1);
					  san_count++;
				  }
			  }
			}catch(Exception e){}

			// Pre-Parse Transport (including Point ID abstraction)
			String[] point_ids=new String[0];
			java.util.ArrayList<String> arr_ids=new java.util.ArrayList<String>();
			t=d.getTransport();
			if(t==null){
				throw new org.fiap.exception.InvalidRequestException("transport is not specified.");
			}
			
			b=t.getBody();
			if(b==null){
				throw new org.fiap.exception.InvalidRequestException("body is not specified.");
			}			
			if(walkAndCountValues(b.getPointSet(),b.getPoint(),arr_ids)>MAX_VALUE_COUNTS){
				throw new org.fiap.exception.TooManyValuesException("The number of keys exceeded the server limit MAX_VALUE_COUNTS=\""+MAX_VALUE_COUNTS+"\".");
			}
			point_ids=arr_ids.toArray(point_ids);
			
			// For Debug Messages
			if(admin.debug()){	
				StringBuilder sb=new StringBuilder();
				try{
					sb.append("transaction:");
					sb.append(transaction);
					sb.append("; ");		
					sb.append("initiator:");
					sb.append(initiator_id);
					sb.append("; ");					
					translate2LogMessage(b.getPointSet(),b.getPoint(),sb);
					admin.log("fiap.server.data.message", sb.toString(), true);
				}catch(Exception e){
					admin.log("fiap.server.data.message", "Failed while parsing the request", false);
					admin.log("fiap.server.data.message", sb.toString(), true);
				}
			}

			// Check ACCESS CONTROL POLICY
			if(ACM.getInstance().checkAccessControlPolicy(initiator_id, initiator_ip, "WRITE", point_ids)){
				// if passed ..
				r=m_GUTUnit.data(t,initiator_ip);
			}
			
		}catch(FIAPException e){

			r=new Transport();
			Header header=new Header();
			Error error=new Error();
			error.setString(e.getMessage());
			error.setType(e.getType());
			header.setError(error);
			r.setHeader(header);
			admin.log("fiap.server.data.error",  "transaction:"+transaction+"; type=:"+e.getType()+"; message=\'"+e.getMessage()+"\'", true);

		}catch(Exception e){
			
			e.printStackTrace();
			
			StackTraceElement[] stackTraceElements=e.getStackTrace();
			String errorStackTrace="";
			for(int i=0;i<stackTraceElements.length;i++){
				errorStackTrace=errorStackTrace+"; "+stackTraceElements[i];
			}
			
			r=new Transport();
			Header header=new Header();
			Error error=new Error();
			error.setString(errorStackTrace);
			error.setType("UNKNOWN");
			header.setError(error);
			r.setHeader(header);
			admin.log("fiap.server.data.error",  "transaction:"+transaction+"; type=:"+e.getClass().getName()+"; message=\'"+e.getMessage()+"\'", true);
		}finally{
			admin.log("fiap.server.data.end", "transaction:"+transaction+"; ", true);
		}

		DataRS result=new DataRS();
		result.setTransport(r);
		return result;
	}
	
	
	/**
	 * walkAndCountValues -- 含まれるvalue要素の数を計上する。 その際に、含まれるポイントIDをarr_idsに記憶する
	 * @param ps
	 * @param p
	 * @return
	 */
	private static int walkAndCountValues(PointSet[] ps, Point[] p, java.util.ArrayList<String> arr_ids) throws FIAPException {
		
		int count=0;
		if(ps!=null){
			for(int i=0;i<ps.length;i++){
				count+=walkAndCountValues(ps[i].getPointSet(),ps[i].getPoint(),arr_ids);
			}
		}
				
		if(p!=null){
			Calendar now=Calendar.getInstance();
			for(int i=0;i<p.length;i++){
				arr_ids.add(p[i].getId().toString());
				Value[] v=p[i].getValue();
				if(v!=null){
					count+=v.length;
					for(int j=0;j<v.length;j++){
						if(v[j].getTime()==null){
							v[j].setTime(now);
						//	throw new org.fiap.exception.ValueTimeNotSpecifiedException("time attribute should be specified for this server.");
						}
					}
				}
			}
		}
		return count;
	}
	
	/**
	 * Dataメソッドにより書き込まれたデータの統計解析 (デバッグ用)
	 * @param ps
	 * @param p
	 * @param sb
	 */
	private static void translate2LogMessage(PointSet[] ps, Point[] p, StringBuilder sb){
		
		if(ps!=null){
			for(int i=0;i<ps.length;i++){
				translate2LogMessage(ps[i].getPointSet(),ps[i].getPoint(),sb);
			}
		}
		
		if(p!=null){
			for(int i=0;i<p.length;i++){
				sb.append("(");
				if(p[i].getId()!=null){
					sb.append("id:"); 
					sb.append(p[i].getId().toString()); 
					sb.append("; ");
				}
				
				Value[] v=p[i].getValue();
				if(v!=null){
					sb.append("count:");
					sb.append(v.length);
					sb.append("; ");
				}
				sb.append(")");
			}
		}
	}
}
