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

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.fiap.common.PointBusObserver;
import org.fiap.common.PointData;
import org.fiap.soap.FIAPWS;
import org.fiap.soap.FIAPWSStub;
import org.fiap.soap.DataRQ;
import org.fiap.soap.DataRS;

import org.fiap.types.Transport;
import org.fiap.types.Header;
import org.fiap.types.Body;
import org.fiap.types.AttrNameType;
import org.fiap.types.Key;
import org.fiap.types.Point;
import org.fiap.types.PointSet;
import org.fiap.types.Query;
import org.fiap.types.QueryType;
import org.fiap.types.SelectType;
import org.fiap.types.Uuid;

/**
 * WRITECopyThread
 * 
 * write タグに設定された内容に従って、ローカルのデータベースから定期的にデータを取得し、
 * 遠隔のFIAPサーバに保存する。
 * 
 * @author jo2lxq
 * @date 2011-07-09
 */
public class WRITECopyThread extends Thread {

	/**
	 * fiapURI 属性
	 *  遠隔のFIAPサーバのSOAP EPRが指定される (複数指定は不可) 
	 */
	private String m_FIAPURI;
	
	/**
	 * mode 属性 
	 * mode="diff"の場合、Aの条件を満たすときに、Bの範囲のデータを読み出す
	 * 　　現在時刻を　t　とする。
	 *   A: (t+offset)%frequency == 0 
	 *   B: [t-frequency+offset,t+offset)
	 *   
	 * mode="latest"の場合、Cの条件を満たすとき、Dの範囲のデータを読み出す
	 *   現在時刻を t とする
	 *   C: (t+offset)%frequency == 0
	 *   D: attrName="time" select="maximum"
	 */
	private String m_Mode;

	/**
	 * frequency属性
	 *  遠隔のサーバに対してWRITEを行う周期を sec　で指定する。 
	 * 
	 */
	private long m_Frequency;
	
	/**
	 * offset属性
	 * 　　現在時から、どのくらい時間前のデータを取得するかを指定する。
	 */
	private long m_OffSet;
	
	/**
	 * write 要素の直下にある PointもしくはPointSet オブジェクトの配列<br />
	 * 配列(Hashtable)のキーには PointもしくはPointSet のidが設定される
	 */
	private java.util.Hashtable<String,PointData> m_PointTreeRoots=new java.util.Hashtable<String, PointData>();
	
	/**
	 * FIAPポイントIDに対するPointもしくはPointSetオブジェクトの対応表
	 */
	private java.util.Hashtable<String,PointData> m_PointMap=new java.util.Hashtable<String, PointData>();
	
	/**
	 * 子ポイントIDから親ポイントIDを発見するための対応表
	 */
	private java.util.Hashtable<String,String> m_ChildToParentMap=new java.util.Hashtable<String, String>();

	/**
	 * WRITECopyThreadを作成した PointBusObserver (TODO: ...)
	 */
	private PointBusObserver m_Parent;
	
	/**
	 * 
	 * @param write
	 * @param parent
	 * @throws org.apache.axis2.databinding.types.URI.MalformedURIException
	 */
	public WRITECopyThread(Element write, PointBusObserver parent) throws Storage2ConfigException, org.apache.axis2.databinding.types.URI.MalformedURIException {
		m_Parent=parent;
		parseWRITEElement(write);
		
		start();
	}
	
	/**
	 * parseWRITEElement <br />
	 *   設定ファイルから読み込んだ write要素の内部を解析し、<br />
	 *   mode属性、frequency属性、offset属性、fiapURI属性、<br />
	 *   pointSet、pointのIDを読み出す。
	 */
	public void parseWRITEElement(Element write) throws org.apache.axis2.databinding.types.URI.MalformedURIException, Storage2ConfigException {
		
		m_FIAPURI=write.getAttribute("fiapURI");
		if(m_FIAPURI.equals("")){
			String msg="WRITECopyThread.parseFETCHElement: Fatal error in configuration; fiapURI must be specified.";
			System.err.println(msg);
			throw new Storage2ConfigException(msg);
		}
		try{
			new java.net.URL(m_FIAPURI);
		}catch(Exception e){
			String msg="WRITECopyThread.parseFETCHElement: Fatal error in configuration; fiapURI schema error.";
			System.err.println(msg);
			throw new Storage2ConfigException(msg);
		}

		m_Mode=write.getAttribute("mode");
		if(!(m_Mode.equals("diff") || m_Mode.equals("latest"))){
			String msg="WRITECopyThread.parseFETCHElement: Fatal error in configuration; mode must be diff or latest.";
			System.err.println(msg);
			throw new Storage2ConfigException(msg);
		}
				
		try{
			m_Frequency=Long.parseLong(write.getAttribute("frequency"));
			if(m_Frequency<=0){
				System.err.println("WRITECopyThread.parseWRITEElement: frequency must be a positive integer -- take 3600 [sec] for frequency.");
				m_Frequency=3600;
			}
		}catch(Exception e){
			System.err.println("WRITECopyThread.parseWRITEElement: frequency must be a positive integer -- take 3600 [sec] for frequency.");
			m_Frequency=3600;
		}
		
		try{
			m_OffSet=Long.parseLong(write.getAttribute("offset"));
		}catch(Exception e){
			System.err.println("WRITECopyThread.parseWRITElement: offset must be an integer -- take 0 [sec] for offset.");
			m_OffSet=0;
		}
				
		parsePointTree(write);
	}
	
	/**
	 * write要素の直下にある PointおよびPointSet を解析し、<br />
	 * m_PointTreeRoots,　m_PointMap, m_ChildToParentMap<br />
	 * を構築する
	 * @param e
	 */
	private void parsePointTree(Element e) throws org.apache.axis2.databinding.types.URI.MalformedURIException {
		
		String parentTagName=e.getTagName();
		Node n0=e.getFirstChild();
		while(n0!=null){
			if(n0.getNodeType()==Node.ELEMENT_NODE){
				Element e0=(Element)n0;
				String tagName=e0.getTagName();
				String id=e0.getAttribute("id");
				if(tagName.equals("pointSet")){
					PointSet ps=new PointSet();
					ps.setId(new org.apache.axis2.databinding.types.URI(id));
					m_PointMap.put(id, new PointData(ps));
					if(parentTagName.equals("pointSet")){
						String parent=e.getAttribute("id");
						m_ChildToParentMap.put(id, parent);	
					}else if(parentTagName.equals("write")){
						m_PointTreeRoots.put(id, new PointData(ps));
					}
					parsePointTree(e0);
				}else if(tagName.equals("point")){
					Point p=new Point();
					p.setId(new org.apache.axis2.databinding.types.URI(id));
					m_PointMap.put(id, new PointData(p));
					if(parentTagName.equals("pointSet")){
						String parent=e.getAttribute("id");
						m_ChildToParentMap.put(id, parent);
					}else if(parentTagName.equals("write")){
						m_PointTreeRoots.put(id, new PointData(p));
					}
				}
			}
			n0=n0.getNextSibling();
		}
	}
	
	/**
	 * PointSet木を ローカルのStorageに作成する
	 */
	public void init(){

		Storage2Admin admin=Storage2Admin.getInstance();
		admin.log("fiap.client.writecopy.init.begin", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet, false);
		
		java.util.Map<String, PointData> roots=new java.util.Hashtable<String, PointData>();
		java.util.Map<String, PointData> points=new java.util.Hashtable<String, PointData>();
		String[] keys=new String[0];
		keys=m_PointMap.keySet().toArray(keys);
		for(int i=0;i<keys.length;i++){
			PointData obj=m_PointMap.get(keys[i]);
			PointData clone=null;
			if(obj.hasPointSet()){
				PointSet clonePS=new PointSet();
				clonePS.setId(obj.getPointSet().getId());
				clone=new PointData(clonePS);
			}else if(obj.hasPoint()){
				Point cloneP=new Point();
				cloneP.setId(obj.getPoint().getId());
				clone=new PointData(cloneP);
			}
			if(m_PointTreeRoots.containsKey(keys[i])){
				roots.put(keys[i], clone);
			}
			points.put(keys[i], clone);
		}
		keys=new String[0];
		keys=m_ChildToParentMap.keySet().toArray(keys);
		for(int i=0;i<keys.length;i++){
			String id=keys[i];
			String parent=m_ChildToParentMap.get(id);
			PointData child=points.get(id);
			if(child.hasPointSet()){
				points.get(parent).getPointSet().addPointSet(child.getPointSet());
			}else if(child.hasPoint()){
				points.get(parent).getPointSet().addPoint(child.getPoint());
			}
		}

		keys=new String[0];
		keys=roots.keySet().toArray(keys);
		for(int i=0;i<keys.length;i++){
			PointData obj=roots.get(keys[i]);
		//	System.out.println(keys[i]);
			m_Parent.sendUpdate(obj);
		}
		
		admin.log("fiap.client.writecopy.init.end", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet, false);
	}
	
	/**
	 * 定期的実行のメイン・ルーチン
	 */
	public void run(){

		try{
			Thread.sleep(5000);
		}catch(Exception e){}
		
		init();
		
		Storage2Admin admin=Storage2Admin.getInstance();
		admin.log("fiap.client.writecopy.run.begin", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet, false);

		long time=System.currentTimeMillis()/1000;
		long lastWRITEtime=0;
		while(true){
			try{
				if(time < System.currentTimeMillis()/1000){
					time++;
				}
				if((time+m_OffSet)%m_Frequency==0 && time>=lastWRITEtime){
					lastWRITEtime=time;
										
					// Find points to retrieve by FETCH
					java.util.ArrayList<String> idArray=new java.util.ArrayList<String>(); // idArray -- 取得するべき point idのみをリストアップする					
					String[] ids=new String[0];
					ids=m_PointMap.keySet().toArray(ids);
					for(int i=0;i<ids.length;i++){
						if(m_PointMap.get(ids[i]).hasPoint()){
							idArray.add(ids[i]);
						}
					}
					long startTime=(time-m_Frequency+m_OffSet)*1000;
					long endTime=(time+m_OffSet)*1000;
					String gt=org.fiap.util.W3CTimestamp.toString(startTime, java.util.TimeZone.getDefault());
					String lteq=org.fiap.util.W3CTimestamp.toString(endTime, java.util.TimeZone.getDefault());
															
					// Generate Query
					Query query=new Query();
					String id=java.util.UUID.randomUUID().toString();
					Uuid uuid=new Uuid();
					uuid.setUuid(id);
					query.setId(uuid);
					query.setType(QueryType.storage);
					query.setAcceptableSize(new org.apache.axis2.databinding.types.PositiveInteger("1000"));					
					for(int i=0;i<idArray.size();i++){
						Key key=new Key();
						key.setId(new org.apache.axis2.databinding.types.URI(idArray.get(i)));
						key.setAttrName(AttrNameType.time);
						if(m_Mode.equals("diff")){
							key.setGteq(gt);
							key.setLt(lteq);
						}
						if(m_Mode.equals("latest")){
							key.setSelect(SelectType.maximum);
						}
						query.addKey(key);						
					}
					
					// FETCH repeatedly from the local storage and WRITE to a remote server specified by fiapURI
					FIAPWS srv=new FIAPWSStub(m_FIAPURI);
					boolean eof=false;
					while(!eof){
						Transport request=new Transport();
						Header hreq=new Header();

						hreq.setQuery(query);
						request.setHeader(hreq);
												
						Transport response=null;
						admin.log("fiap.client.writecopy.run.query.begin", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet, false);
						try{
							response=Storage2.m_GUTUnit.query(request, "127.0.0.1");
							Header hres=response.getHeader();
							
							if(hres.getOK()!=null){	
								Query qres=hres.getQuery();
								Uuid cursor=qres.getCursor();
								if(cursor==null){
									eof=true;
								}else{
									query=qres;
								}
								Body body=response.getBody();
								
								PointSet[] ps=body.getPointSet();
								Point[] p=body.getPoint();
								
								// Create request to WRITE data to a remote fiapURI.
								Transport remote_transport=new Transport();
								Body remote_body=new Body();
								remote_transport.setBody(remote_body);
								if(ps!=null && ps.length!=0){
									remote_body.setPointSet(ps);
								}
								if(p!=null && p.length!=0){
									remote_body.setPoint(p);
								}
								
								DataRQ dataRQ=new DataRQ();
								dataRQ.setTransport(remote_transport);
								
								DataRS dataRS=null;
								admin.log("fiap.client.writecopy.run.write.begin", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet, true);
								try{
									dataRS=srv.data(dataRQ);
									remote_transport=dataRS.getTransport();
									Header remote_header=remote_transport.getHeader();
									
									if(remote_header.getOK()!=null){
									// Do nothing.
									}else if(remote_header.getError()!=null){
										org.fiap.types.Error error=remote_header.getError();
										admin.log("fiap.client.writecopy.run.write.error", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet+"; errorType="+error.getType()+"; errorMessage="+error.getString(), true);
									}
								}catch(Exception e){
									admin.log("fiap.client.writecopy.run.write.error", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet+"; error="+e.getMessage(), true);
								}finally{
									admin.log("fiap.client.writecopy.run.write.end", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet, false);
								}

							}else if(hres.getError()!=null){
								org.fiap.types.Error error=hres.getError();
								admin.log("fiap.client.writecopy.run.query.error", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet+"; errorType="+error.getType()+"; errorMessage="+error.getString(), true);
							}	
						}catch(Exception e){
							admin.log("fiap.client.writecopy.run.query.error", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet+"; error="+e.getMessage(), true);
						}finally{
							admin.log("fiap.client.writecopy.run.query.end", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet, false);
						}
					}
				}
			}catch(Exception e){
				//e.printStackTrace();
				admin.log("fiap.client.writecopy.run.error", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet+"; fatalerror="+e.getMessage(), true);
			}
			
			try{
				sleep(500);
			}catch(Exception e){}
		}
	}
}
