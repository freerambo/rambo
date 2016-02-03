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

import java.util.ArrayList;
import java.util.Hashtable;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

public class GetPointList {

	private final String INDENT_BASE="  ";
	
	private ArrayList<String> m_Roots;
	private Hashtable<String,String> m_ChildToParentMap;

	private Hashtable<String,PointSet> m_PointSetMap;
	private Hashtable<String,Point> m_PointMap;
	
	// Point型
	public class Point {
		public String id;
		public Point(String id){
			this.id=id;
		}
	}

	// PointSet型
	public class PointSet {
		public String id;
		public ArrayList<PointSet> ps;
		public ArrayList<Point> p;
		public PointSet(String id){
			this.id=id;
			ps=new ArrayList<PointSet>();
			p=new ArrayList<Point>();
		}
	}
	
	// DBからPointSetTreeを取得し m_Roots, m_PointSetMap, m_PointMapを作成する IDbCommand
	//　環境変数 POSTGRESQL_CONNECTION_STRING で接続先DBを指定することができる。
	public class PointSetTreeScan implements IDbCommand {
		public boolean execDbCommand(Connection connection) throws SQLException {
			Statement stmt=connection.createStatement();
			
			String sql="SELECT id, parent, ispoint FROM pointsettree";
			ResultSet rset=stmt.executeQuery(sql);
			
			while(rset.next()){
				String id=rset.getString("id");
				String parent=rset.getString("parent");
				boolean ispoint=rset.getBoolean("ispoint");
				
				if(ispoint){
					m_PointMap.put(id, new Point(id));
				}else{
					m_PointSetMap.put(id, new PointSet(id));
				}
				if(parent==null){
					m_Roots.add(id);
				}else{
					m_ChildToParentMap.put(id, parent);
				}
			}
			
			return false;
		}
	}
	
	/**
	 * Point表記の生成
	 */ 
	private String generateXmlStringFromPoint(Point p, String indent){
		return indent+"<point id=\""+p.id+"\"/>\n";
	}
	
	/**
	 *　PointSet表記の生成 (再帰的)
	 */ 
	private String generateXmlStringFromPointSet(PointSet ps, String indent){
	
		StringBuilder sb=new StringBuilder();
		sb.append(indent);
		sb.append("<pointSet id=\"");
		sb.append(ps.id);
		sb.append("\">\n");
		
		ArrayList<PointSet> child_ps=ps.ps;
		for(int i=0;child_ps!=null && i<child_ps.size();i++){
			PointSet next_ps=child_ps.get(i);
			sb.append(generateXmlStringFromPointSet(next_ps,INDENT_BASE+indent));
		}
		ArrayList<Point> child_p=ps.p;
		for(int i=0;child_p!=null && i<child_p.size();i++){
			Point next_p=child_p.get(i);
			sb.append(generateXmlStringFromPoint(next_p,INDENT_BASE+indent));
		}
	
		sb.append(indent);
		sb.append("</pointSet>\n");
		return sb.toString();
	}

	/**
	 * XMLにシリアライズする
	 */ 
	private String generateXml(){
				
		StringBuilder sb=new StringBuilder();		
		sb.append("<pointList xmlns=\"http://gutp.jp/fiap/2009/11/pointlist\">\n");
	
		String[] roots=new String[0];
		roots=m_Roots.toArray(roots);
		for(int i=0;i<roots.length;i++){
			PointSet ps=m_PointSetMap.get(roots[i]);
			if(ps!=null){
				sb.append(generateXmlStringFromPointSet(ps,""));
			}	
			Point p=m_PointMap.get(roots[i]);
			if(p!=null){
				sb.append(generateXmlStringFromPoint(p,""));
			}
		}

		sb.append("</pointList>");
		return sb.toString();
	}
	
	/**
	 * 個別のPointSet, Pointをリンクする
	 * @throws Exception
	 */
	private void linkChildToParent(){
		String[] children=new String[0];
		children=m_ChildToParentMap.keySet().toArray(children);
		for(int i=0;i<children.length;i++){
			String child=children[i];
			String parent=m_ChildToParentMap.get(child);
			
			PointSet parent_ps=m_PointSetMap.get(parent);
			if(parent_ps==null){
				System.err.println("Fatal Error... Unknown pointSet parent id=\""+parent+"\"");
				continue;
			}
			
			PointSet child_ps=m_PointSetMap.get(child);
			if(child_ps!=null){
				parent_ps.ps.add(child_ps);
			}
			
			Point child_p=m_PointMap.get(child);
			if(child_p!=null){
				parent_ps.p.add(child_p);
			}
		}
	}

	public void run() throws Exception {
		PointSetTreeScan pst=new PointSetTreeScan();
		
		// 環境変数 POSTGRESQL_CONNECTION_STRING で接続先DBを指定することができる。
		PostgresDB.postCommand(pst);
		
		linkChildToParent();
		
		System.out.println(generateXml());
		return ;
	}
	
	private GetPointList(){
		m_Roots=new ArrayList<String>();
		m_ChildToParentMap=new Hashtable<String,String>();
		m_PointSetMap=new Hashtable<String,PointSet>();
		m_PointMap=new Hashtable<String,Point>();
	}
	
	public static void main(String[] args) throws Exception {
		GetPointList mainObj=new GetPointList();
		mainObj.run();
		System.exit(0);
	}

}
