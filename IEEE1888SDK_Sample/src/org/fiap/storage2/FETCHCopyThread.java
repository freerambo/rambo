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
import org.fiap.soap.QueryRQ;
import org.fiap.soap.QueryRS;

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
import org.fiap.types.Value;

/**
 * FETCHCopyThread
 * 
 * fetch タグに設定された内容に従って、遠隔のFIAPサーバから定期的にデータを取得し、
 * ローカルサーバに保存する。
 * 
 * @author jo2lxq
 * @date 2011-05-21
 */
public class FETCHCopyThread extends Thread {

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
	 *  遠隔のサーバに対してFETCHを行う周期を sec　で指定する。 
	 * 
	 */
	private long m_Frequency;
	
	/**
	 * offset属性
	 * 　　現在時から、どのくらい時間前のデータを取得するかを指定する。
	 */
	private long m_OffSet;
	
	/**
	 * organizeTree属性
	 *  設定ファイルにある pointSetTreeに従って、サーバ内のpointSetTreeを構築するかどうか
	 */
	private boolean m_OrganizeTree;
	
	/**
	 * fetch 要素の直下にある PointもしくはPointSet オブジェクトの配列<br />
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
	 * FETCHCopyThreadを作成した PointBusObserver (TODO: ...)
	 */
	private PointBusObserver m_Parent;
	
	/**
	 * 
	 * @param fetch
	 * @param parent
	 * @throws org.apache.axis2.databinding.types.URI.MalformedURIException
	 */
	public FETCHCopyThread(Element fetch, PointBusObserver parent) throws Storage2ConfigException, org.apache.axis2.databinding.types.URI.MalformedURIException {
		m_Parent=parent;
		parseFETCHElement(fetch);
		
		start();
	}
	
	/**
	 * parseFETCHElement <br />
	 *   設定ファイルから読み込んだ fetch要素の内部を解析し、<br />
	 *   mode属性、frequency属性、offset属性、fiapURI属性、<br />
	 *   pointSet、pointのIDを読み出す。
	 */
	public void parseFETCHElement(Element fetch) throws org.apache.axis2.databinding.types.URI.MalformedURIException, Storage2ConfigException {
		
		m_FIAPURI=fetch.getAttribute("fiapURI");
		if(m_FIAPURI.equals("")){
			String msg="FETCHCopyThread.parseFETCHElement: Fatal error in configuration; fiapURI must be specified.";
			System.err.println(msg);
			throw new Storage2ConfigException(msg);
		}
		try{
			new java.net.URL(m_FIAPURI);
		}catch(Exception e){
			String msg="FETCHCopyThread.parseFETCHElement: Fatal error in configuration; fiapURI schema error.";
			System.err.println(msg);
			throw new Storage2ConfigException(msg);
		}

		m_Mode=fetch.getAttribute("mode");
		if(!(m_Mode.equals("diff") || m_Mode.equals("latest"))){
			String msg="FETCHCopyThread.parseFETCHElement: Fatal error in configuration; mode must be diff or latest.";
			System.err.println(msg);
			throw new Storage2ConfigException(msg);
		}
				
		try{
			m_Frequency=Long.parseLong(fetch.getAttribute("frequency"));
			if(m_Frequency<=0){
				System.err.println("FETCHCopyThread.parseFETCHElement: frequency must be a positive integer -- take 3600 [sec] for frequency.");
				m_Frequency=3600;
			}
		}catch(Exception e){
			System.err.println("FETCHCopyThread.parseFETCHElement: frequency must be a positive integer -- take 3600 [sec] for frequency.");
			m_Frequency=3600;
		}
		
		try{
			m_OffSet=Long.parseLong(fetch.getAttribute("offset"));
		}catch(Exception e){
			System.err.println("FETCHCopyThread.parseFETCHElement: offset must be an integer -- take 0 [sec] for offset.");
			m_OffSet=0;
		}
		
		try{
			if(fetch.getAttribute("organizeTree").equals("true")){
				m_OrganizeTree=true;
			}else{
				m_OrganizeTree=false;
			}
		}catch(Exception e){
			m_OrganizeTree=false;
		}		
				
		parsePointTree(fetch);
	}
	
	/**
	 * fetch要素の直下にある PointおよびPointSet を解析し、<br />
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
					}else if(parentTagName.equals("fetch")){
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
					}else if(parentTagName.equals("fetch")){
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
		admin.log("fiap.client.fetchcopy.init.begin", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet, false);
		
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
			if(m_OrganizeTree){
				m_Parent.sendUpdate(obj);
			}
		}
		
		admin.log("fiap.client.fetchcopy.init.end", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet, false);
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
		admin.log("fiap.client.fetchcopy.run.begin", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet, false);

		long time=System.currentTimeMillis()/1000;
		long lastFETCHtime=0;
		while(true){
			try{
				if(time < System.currentTimeMillis()/1000){
					time++;
				}
				if((time+m_OffSet)%m_Frequency==0 && time>=lastFETCHtime){
					lastFETCHtime=time;
										
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
					query.setAcceptableSize(new org.apache.axis2.databinding.types.PositiveInteger("100"));					
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
					
					// FETCH repeatedly and store into local storage
					FIAPWS srv=new FIAPWSStub(m_FIAPURI);					
					boolean eof=false;
					while(!eof){
						Transport request=new Transport();
						Header hreq=new Header();

						hreq.setQuery(query);
						request.setHeader(hreq);
						
						QueryRQ queryRQ=new QueryRQ();
						queryRQ.setTransport(request);
						
						QueryRS queryRS=null;
						
						admin.log("fiap.client.fetchcopy.run.query.begin", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet, false);
						try{
							queryRS=srv.query(queryRQ);
							Transport response=queryRS.getTransport();			
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
								
								for(int i=0;ps!=null && i<ps.length;i++){
									m_Parent.sendUpdate(new PointData(ps[i]));
								}
								for(int i=0;p!=null && i<p.length;i++){
									m_Parent.sendUpdate(new PointData(p[i]));
								//  for debugging
								//	System.out.println(p[i].getId().toString());
								//	Value[] vs=p[i].getValue();
								//	for(int k=0;vs!=null && k<vs.length;k++){
								//		System.out.println(org.fiap.util.W3CTimestamp.toString(vs[k].getTime())+" : "+vs[k].getString());
								//	}
								}
								
							}else if(hres.getError()!=null){
								org.fiap.types.Error error=hres.getError();
								admin.log("fiap.client.fetchcopy.run.query.error", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet+"; errorType="+error.getType()+"; errorMessage="+error.getString(), true);
								eof=true;
							}	
						}catch(Exception e){
							admin.log("fiap.client.fetchcopy.run.query.error", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet+"; error="+e.getMessage(), true);
							eof=true;
						}finally{
							admin.log("fiap.client.fetchcopy.run.query.end", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet, false);
						}

					}
				}
			}catch(Exception e){
				//e.printStackTrace();
				admin.log("fiap.client.fetchcopy.run.error", "fiapURI="+m_FIAPURI+"; frequency="+m_Frequency+"; mode="+m_Mode+"; offset="+m_OffSet+"; fatalerror="+e.getMessage(), true);
			}
			
			try{
				sleep(500);
			}catch(Exception e){}
		}
	}
}
