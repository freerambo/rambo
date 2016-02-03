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


import org.fiap.types.PointSet;
import org.fiap.types.Point;
import org.fiap.types.Query;
import org.fiap.types.Key;
import org.fiap.types.Transport;
import org.fiap.types.Header;
import org.fiap.types.OK;
import org.fiap.types.Error;
import org.fiap.types.Body;
import org.fiap.types.Value;

import org.fiap.common.PointData;
import org.fiap.exception.QueryNotSupportedException;
import org.fiap.soap.FIAPWSStub;
import org.fiap.soap.DataRQ;
import org.fiap.soap.DataRS;


/**
 * 下記のクエリ処理をサポートする Storage用 StreamQueryProcessor <br />
 * <br />
 * 1. Key の深さは1段のみ <br />
 * 2. Key で指定するid は PointのIDのみ PointSetのIDには対応しない <br />
 * 3. Key の attrName は "time" と "value" のみサポート <br />
 * 4. Key の trap は　"changed" のみサポート <br />
 * 5. attrName と trap 省略不可能 <br />
 * 6. それ以外の属性が指定された Key を読み込んだときは、QueryNotSupportedExceptionを発行する <br />
 * <br />
 * (*) StorageDataManagerに依存する <br /> 
 * <br />
 * @author Hideya Ochiai
 * @since 2009-12-28
 */
public class Stream2QueryProcessor extends org.fiap.common.StreamQueryProcessor {

	/**
	 * 関連付けされたStorageDataManagerのインスタンス (このインスタンスの生成元)
	 */
	Storage2DataManager m_DM;
	
	/**
	 * 処理するQueryの保持
	 */
	Query m_Query;
	
	/**
	 * Queryに含まれるKey要素の配列
	 */
	Key[] m_Keys;
	
	/**
	 * Queryに含まれる　callbackData 属性の保持
	 */
	String m_CallbackData;

	/**
	 * Queryに含まれる　callbackControl 属性の保持<br />
	 * (現在の実装2009-12-28では利用されない)
	 */
	String m_CallbackControl;
	
	/**
	 * 前回の通信失敗時刻
	 */
	long m_LastFailTimeMillis=0L;
	
	/**
	 * 連続失敗回数
	 */
	int m_ContinuousFailCount=0;
	
	/**
	 * コンストラクタ
	 * @param dm -- 関連付けされStorageDataManagerインスタンス
	 * @param q -- クエリの内容
	 * @param keys -- クエリに含まれる Key要素の配列
	 * @throws QueryNotSupportedException
	 */
	protected Stream2QueryProcessor(Storage2DataManager dm, Query q, Key[] keys) throws QueryNotSupportedException {

		Storage2Admin admin=Storage2Admin.getInstance();
		if(admin.debug()){
			admin.log("org.fiap.storage.StreamQueryProcessor.new", q.toString(), false);
		}

		m_DM=dm;
		m_Query=q;
		m_Keys=keys;
		
		if(q.getCallbackData()!=null){
			m_CallbackData=q.getCallbackData().toString();
		}
		if(q.getCallbackControl()!=null){
			m_CallbackControl=q.getCallbackControl().toString();
		}
		parseKeys(keys);
	}
	
	/**
	 * Trapの対象とするID と その属性(attrName)の対応表 <br />
	 * time と value 双方に対応する 場合は、 "time|value" もしくは "value|time" (正規表現形式)が得られる <br />
	 * <br />
	 * 例) <br />
	 * &lt;key id="x" attrName="time" trap="changed" /&gt; <br />
	 * &lt;key id="x" attrName="value" trap="changed" /&gt; <br />
	 * の場合は、 <br />
	 *   m_TrapTargetMap.get(id); <br />
	 * は、"time|value" を提供するように、構築される。
	 */
	private java.util.Hashtable<String, String> m_TrapTargetMap;
	
	/**
	 * Key要素の解析
	 * @param keys
	 * @throws QueryNotSupportedException
	 */
	private void parseKeys(Key[] keys) throws QueryNotSupportedException {
		
		Storage2Admin admin=Storage2Admin.getInstance();
		if(admin.debug()){
			admin.log("org.fiap.storage.StreamQueryProcessor.parseKey.begin", "keys.length="+keys.length, false);
		}

		m_TrapTargetMap=new java.util.Hashtable<String, String>();
		
		for(int i=0;i<keys.length;i++){
			if(admin.debug()){
				admin.log("org.fiap.storage.StreamQueryProcessor.parseKey.debug.0", "key index="+i, false);
			}
			String id=keys[i].getId().toString();
			String attrName=keys[i].getAttrName().getValue();
			String eq=keys[i].getEq();
			String neq=keys[i].getNeq();
			String lt=keys[i].getLt();
			String gt=keys[i].getGt();
			String lteq=keys[i].getLteq();
			String gteq=keys[i].getGteq();
			
			String select=null;
			if(keys[i].getSelect()!=null){
				select=keys[i].getSelect().toString();
			}
			String trap=null;
			if(keys[i].getTrap()!=null){
				trap=keys[i].getTrap().toString();
			}
			
			if(admin.debug()){
				admin.log("org.fiap.storage.StreamQueryProcessor.parseKey.debug.1", "key index="+i, false);
			}

			if(eq!=null){ throw new QueryNotSupportedException("org.fiap.storage.StreamQueryProcessor: \'eq\' is set in a key but not supported."); }
			if(neq!=null){ throw new QueryNotSupportedException("org.fiap.storage.StreamQueryProcessor: \'neq\' is set in a key but not supported."); }
			if(lt!=null){ throw new QueryNotSupportedException("org.fiap.storage.StreamQueryProcessor: \'lt\' is set in a key but not supported."); }
			if(gt!=null){ throw new QueryNotSupportedException("org.fiap.storage.StreamQueryProcessor: \'gt\' is set in a key but not supported."); }
			if(lteq!=null){ throw new QueryNotSupportedException("org.fiap.storage.StreamQueryProcessor: \'lteq\' is set in a key but not supported."); }
			if(gteq!=null){ throw new QueryNotSupportedException("org.fiap.storage.StreamQueryProcessor: \'gteq\' is set in a key but not supported."); }
			if(select!=null){ throw new QueryNotSupportedException("org.fiap.storage.StreamQueryProcessor: \'select\' is set in a key but not supported."); }
			
			if(admin.debug()){
				admin.log("org.fiap.storage.StreamQueryProcessor.parseKey.debug.2", "key index="+i, false);
			}

			if(attrName==null){
				throw new QueryNotSupportedException("org.fiap.storage.StreamQueryProcessor: \'attrName\' is not specified in a stream query.");
			}
			if(!(attrName.equals("time") || attrName.equals("value"))){
				throw new QueryNotSupportedException("org.fiap.storage.StreamQueryProcessor: \'attrName=\""+attrName+"\"\' is not supported.");
			}
			if(trap==null || trap.equals("")){
				throw new QueryNotSupportedException("org.fiap.storage.StreamQueryProcessor: \'trap\' is not specified in a stream query.");
			}
			
			if(admin.debug()){
				admin.log("org.fiap.storage.StreamQueryProcessor.parseKey.debug.3", "key index="+i, false);
			}

			String target=m_TrapTargetMap.get(id);
			if(target==null){
				m_TrapTargetMap.put(id, attrName);
			}else if(!attrName.matches(target)){
				m_TrapTargetMap.put(id, target+"|"+attrName);
			}
			if(admin.debug()){
				admin.log("org.fiap.storage.StreamQueryProcessor.parseKey.debug.4", "key index="+i, false);
			}

		}
	}
	
	@Override
	public void close() {

	}

	@Override
	public void postData(PointData[] o) {
		
		Storage2Admin admin=Storage2Admin.getInstance();
		
		long time=System.currentTimeMillis();
		if(m_LastFailTimeMillis < time && time < m_LastFailTimeMillis+60000*(1<<m_ContinuousFailCount)){
			admin.log("org.fiap.storage.StreamQueryProcessor.ignore", "", false);
			return ;
		}
		
		if(admin.debug()){
			admin.log("org.fiap.storage.StreamQueryProcessor.begin", "", false);
		}

		java.util.ArrayList<Point> updates=new java.util.ArrayList<Point>();
		for(int i=0;i<o.length;i++){
			parseData(o[i],updates);
		}
		
		if(updates.size()==0){
			if(admin.debug()){
				admin.log("org.fiap.storage.StreamQueryProcessor.end", "no updates", false);
			}
			return ;
		}

		// 送信すべきUpdateを検出したときに実行される
		try{
			admin.log("fiap.client.data.begin", "initiated by trap; callback=\'"+m_CallbackData+"\'", false);
			Transport reqTransport=new Transport();
			Header reqHeader=new Header();
			Body reqBody=new Body();
			Point[] points=new Point[0];
			points=updates.toArray(points);
			reqBody.setPoint(points);
			
			reqHeader.setQuery(m_Query);
			reqTransport.setHeader(reqHeader);
			reqTransport.setBody(reqBody);

			DataRQ dataRQ=new DataRQ();
			dataRQ.setTransport(reqTransport);
			
			FIAPWSStub srv=new FIAPWSStub(m_CallbackData);
			srv._getServiceClient().getOptions().setTimeOutInMilliSeconds(5000); // TIMEOUT SHOULD BE SMALL (because it has to reply as fast as possible)
			DataRS dataRS=srv.data(dataRQ);

			Transport resTransport=dataRS.getTransport();
			Header resHeader=resTransport.getHeader();
			OK ok=resHeader.getOK();
			if(ok!=null){
				// succeeded
				m_ContinuousFailCount=0;
			}
			Error error=resHeader.getError();
			if(error!=null){
				admin.log("fiap.client.data.error", "initiated by trap; callback=\'"+m_CallbackData+"\' remote server internal error; " +
						  "error_type=\'"+error.getType()+"\' error_message=\'"+error.getString()+"\'", true);
			}
		}catch(java.rmi.RemoteException e){
			// e.printStackTrace();
			m_ContinuousFailCount++;
			m_LastFailTimeMillis=System.currentTimeMillis();
			admin.log("fiap.client.data.error", "initiated by trap; callback=\'"+m_CallbackData+"\' " +
					  "error_type=\'"+e.getClass().getName()+"\' error_message=\'"+e.getMessage()+"\'", true);
		}catch(Exception e){
			m_ContinuousFailCount++;
			m_LastFailTimeMillis=System.currentTimeMillis();
			admin.log("fiap.client.data.error", "initiated by trap; callback=\'"+m_CallbackData+"\' " +
					  "error_type=\'"+e.getClass().getName()+"\' error_message=\'"+e.getMessage()+"\'", true);			
		}finally{
			admin.log("fiap.client.data.end", "initiated by trap; callback=\'"+m_CallbackData+"\'", true);
		}
		
	}
	
	/**
	 * 更新通知の中から 条件に合致する Update を検出し、<br />
	 * それをPointインスタンスの形式でupdates配列に格納(追記)する。
	 * 
	 * @param o
	 * @param updates
	 */
	private void parseData(PointData o, java.util.ArrayList<Point> updates){
		
		Storage2Admin admin=Storage2Admin.getInstance();
		if(admin.debug()){
			admin.log("org.fiap.storage.StreamQueryProcessor.parseData.begin", "", false);
		}

		if(o.hasPoint()){
			if(admin.debug()){
				admin.log("org.fiap.storage.StreamQueryProcessor.parseData.debug.0", "", false);
			}

			Point p=o.getPoint();
			
			String id=p.getId().toString();
			String target=m_TrapTargetMap.get(id);
			if(target!=null){
				if(admin.debug()){
					admin.log("org.fiap.storage.StreamQueryProcessor.parseData.debug.1", "", false);
				}
				java.util.Calendar time=null;
				Value value=new Value();
				Value[] values=p.getValue();
				for(int i=0;i<values.length;i++){
					Value v=values[i];
					java.util.Calendar t=v.getTime();
					if(time==null || time.compareTo(t)<0){
						time=t;
						value.setTime(v.getTime());
						value.setString(v.getString());
					}
				}
								
				if(time!=null){
					if(admin.debug()){
						admin.log("org.fiap.storage.StreamQueryProcessor.parseData.debug.2", "", false);
					}

					Value cacheValue=m_DM.m_ValueMap.get(id);
					
					try{
						if(cacheValue==null){
							if(admin.debug()){
								admin.log("org.fiap.storage.StreamQueryProcessor.parseData.debug.3", "id="+id, false);
							}

							Point q=new Point();
							q.setId(new org.apache.axis2.databinding.types.URI(id));
							q.addValue(value);
							updates.add(q);
							return ;
						}
						if("time".matches(target)){
							if(!cacheValue.getTime().equals(value.getTime())){
								if(admin.debug()){
									admin.log("org.fiap.storage.StreamQueryProcessor.parseData.debug.4", "id="+id, false);
								}
								Point q=new Point();
								q.setId(new org.apache.axis2.databinding.types.URI(id));
								q.addValue(value);
								updates.add(q);
								return ;
							}
						}	
						if("value".matches(target)){
							if(!cacheValue.getString().equals(value.getString())){
								if(admin.debug()){
									admin.log("org.fiap.storage.StreamQueryProcessor.parseData.debug.5", "id="+id, false);
								}

								Point q=new Point();
								q.setId(new org.apache.axis2.databinding.types.URI(id));
								q.addValue(value);
								updates.add(q);
								return ;
							}
						}
					}catch(org.apache.axis2.databinding.types.URI.MalformedURIException e){
						if(admin.debug()){
							admin.log("org.fiap.storage.StreamQueryProcessor.parseData.error", "message="+e.getMessage(), false);
						}
						e.printStackTrace();
					}
				}
			}
			
		}else if(o.hasPointSet()){
			PointSet ps=o.getPointSet();

			if(admin.debug()){
				admin.log("org.fiap.storage.StreamQueryProcessor.parseData.debug.6", "", false);
			}

			PointSet[] pointSets=ps.getPointSet();
			Point[] points=ps.getPoint();
			for(int i=0;pointSets!=null && i<pointSets.length;i++){
				parseData(new PointData(pointSets[i]),updates);
			}
			for(int i=0;points!=null && i<points.length;i++){
				parseData(new PointData(points[i]),updates);
			}

		}else{			
			if(admin.debug()){
				admin.log("org.fiap.storage.StreamQueryProcessor.parseData.error", "Unknown", false);
			}
			// TODO Error handling
		}
	}
	
}
