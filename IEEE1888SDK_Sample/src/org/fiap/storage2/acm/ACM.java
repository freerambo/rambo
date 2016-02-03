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

package org.fiap.storage2.acm;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import org.fiap.storage2.Storage2Admin;

public class ACM  {

	/**
	 * Singletonオブジェクト格納用
	 */
	private static ACM m_Singleton;

	/**
	 * コンストラクタ<br />
	 * m_Singleton==null のときに getInstance() から呼び出される
	 */
	private ACM(){
		m_Singleton=this;
		m_RoleDefs=new java.util.Hashtable<String, String[]>();
		m_Roles=new java.util.Hashtable<String, Rule[]>();
		m_InitiatorID_to_RoleName=new java.util.Hashtable<String, String>();
				
		loadConfig();
		dumpACL();
	}
	
	/**
	 * グローバルユニーク(=Singleton)なACMオブジェクトの取得<br />
	 * @return グローバルユニークなACMオブジェクト
	 */
	public static ACM getInstance(){
		if(m_Singleton==null){
			new ACM();
		}
		return m_Singleton;
	}


	/*
	 * loaded Configurations
	 */
	private boolean m_DefaultAllow=true;
	private java.util.Hashtable<String,String[]> m_RoleDefs=null;
	private java.util.Hashtable<String,Rule[]> m_Roles=null;
	
	
	// For fast lookup (optimization purpose)
	private java.util.Hashtable<String,String> m_InitiatorID_to_RoleName;

	/**
	 * loadConfig -- load the configuration related to ACM from storage2.xml
	 */
	private void loadConfig(){

		Storage2Admin admin=Storage2Admin.getInstance();
		Element e=admin.getACM();
		
		if(e==null){
			return ;
		}
		
		if(e.getAttribute("default").equals("deny")){
			m_DefaultAllow=false;
		}else{
			m_DefaultAllow=true;
		}
		
		Node n0=e.getFirstChild();
		while(n0!=null){
			if(n0.getNodeType()==Node.ELEMENT_NODE){
				Element e0=(Element)n0;
				String tagName=e0.getTagName();
				if(tagName.equals("roleDef")){
					String name=e0.getAttribute("name");
					ArrayList<String> arr_ids=new ArrayList<String>();
					Node n1=e0.getFirstChild();
					while(n1!=null){
						if(n1.getNodeType()==Node.ELEMENT_NODE){
							Element e1=(Element)n1;
							if(e1.getTagName().equals("initiator")){
								String id=e1.getAttribute("id");
								arr_ids.add(id);
							}
						}
						n1=n1.getNextSibling();
					}
					String[] ids=new String[0];
					ids=arr_ids.toArray(ids);
					m_RoleDefs.put(name, ids);
					
				}else if(tagName.equals("role")){

					String name=e0.getAttribute("name");
					ArrayList<Rule> arr_rules=new ArrayList<Rule>();
					Node n1=e0.getFirstChild();
					while(n1!=null){
						if(n1.getNodeType()==Node.ELEMENT_NODE){
							Element e1=(Element)n1;
							if(e1.getTagName().equals("rule")){
								String str_method=e1.getAttribute("method");
								String str_policy=e1.getAttribute("policy");
								ArrayList<String> arr_ids=new ArrayList<String>();
								Node n2=e1.getFirstChild();
								while(n2!=null){
									if(n2.getNodeType()==Node.ELEMENT_NODE){
										Element e2=(Element)n2;
										if(e2.getTagName().equals("point")){
											arr_ids.add(e2.getAttribute("id"));
										}
									}
									n2=n2.getNextSibling();
								}
								String[] ids=new String[0];
								ids=arr_ids.toArray(ids);
								Rule rule=new Rule();
								if(str_method.equals("query")){
									rule.method=Rule.METHOD_QUERY;
								}else if(str_method.equals("data")){
									rule.method=Rule.METHOD_DATA;
								}else if(str_method.equals("FETCH")){
									rule.method=Rule.METHOD_FETCH;
								}else if(str_method.equals("TRAP")){
									rule.method=Rule.METHOD_TRAP;
								}else if(str_method.equals("WRITE")){
									rule.method=Rule.METHOD_WRITE;
								}else{
									rule.method=2;
								}
								if(str_policy.equals("allow")){
									rule.policy=Rule.POLICY_ALLOW;
								}else if(str_policy.equals("deny")){
									rule.policy=Rule.POLICY_DENY;
								}else if(m_DefaultAllow){
									rule.policy=Rule.POLICY_ALLOW;
								}else{
									rule.policy=Rule.POLICY_DENY;
								}
								rule.points=ids;
								arr_rules.add(rule);
							}
						}
						n1=n1.getNextSibling();
					}
					Rule[] rules=new Rule[0];
					rules=arr_rules.toArray(rules);
					m_Roles.put(name, rules);
				}
			}
			n0=n0.getNextSibling();
		}
		
		generateInitiatorID_to_RoleNameMap(); // For fast lookup (optimization)
	}
	
	/*
	 * generateInitiatorID_to_RoleNameMap
	 *  -- optimization of looking up
	 */
	private void generateInitiatorID_to_RoleNameMap(){
		
		// For Static Map
		java.util.Hashtable<String, String> newmap=new java.util.Hashtable<String, String>();
		String[] keys=new String[0];
		keys=m_RoleDefs.keySet().toArray(keys);
		for(int k=0;k<keys.length;k++){
			String[] initiator_ids=m_RoleDefs.get(keys[k]);
			for(int i=0;i<initiator_ids.length;i++){
				if(!newmap.containsKey(initiator_ids[i])){
					newmap.put(initiator_ids[i], keys[k]);
				}else{
					System.err.println("Error: initiator="+initiator_ids[i]+" has multiple roles.");
				}
			}
		}
		m_InitiatorID_to_RoleName=newmap;
	}
		
	/**
	 * checkAccessControlPolicy
	 *  -- verifies the access rights of the initiator 
	 */
	public boolean checkAccessControlPolicy(String initiator_id, String initiator_ip, String method, String[] target_ids)
		throws org.fiap.common.FIAPException {
		
		// Debug Message
		// for(int i=0;i<target_ids.length;i++){
		//	System.out.println("ID: "+ target_ids[i]);
		// }
		
		if(initiator_id==null){
			if(m_DefaultAllow){
				return true;
			}
			throw new org.fiap.exception.ForbiddenException("initiator is anonymous, and thus denied to access.");			
		}

		String roleName=m_InitiatorID_to_RoleName.get(initiator_id);
		if(roleName==null){
			if(m_DefaultAllow){
				return true;
			}
			throw new org.fiap.exception.ForbiddenException(initiator_id+" is an unknown initiator, and thus denied to access.");
		}

		Rule[] rules=m_Roles.get(roleName);
		if(rules==null){
			if(m_DefaultAllow){
				return true;
			}
			throw new org.fiap.exception.ForbiddenException("No rules defined for role="+roleName+", thus denied to access.");			
		}

		byte b_method=0;		
		if(method.equals("query")){
			b_method=Rule.METHOD_QUERY;
		}else if(method.equals("data")){
			b_method=Rule.METHOD_DATA;
		}else if(method.equals("FETCH")){
			b_method=Rule.METHOD_FETCH;
		}else if(method.equals("TRAP")){
			b_method=Rule.METHOD_TRAP;
		}else if(method.equals("WRITE")){
			b_method=Rule.METHOD_WRITE;
		}else{
			b_method=Rule.METHOD_NONE;
		}

		// Set match vector to 2:  POLICY_DENY(0) -- deny; POLICY_ALLOW(1) -- allow; POLICY_NONE(2) -- untouched;
		byte[] b_match=new byte[target_ids.length];
		for(int i=0;i<b_match.length;i++){
			b_match[i]=Rule.POLICY_NONE;
		}
				
		for(int i=0;i<rules.length;i++){
			Rule r=rules[i];
			if((r.method==Rule.METHOD_QUERY && (b_method==Rule.METHOD_FETCH || b_method==Rule.METHOD_TRAP)) ||
			   (r.method==Rule.METHOD_DATA && (b_method==Rule.METHOD_WRITE)) ||
				r.method==b_method ){

				if(r.policy==Rule.POLICY_ALLOW){ // if allowed
					for(int j=0;j<r.points.length;j++){
						String point=r.points[j];
						if(point.length()==1 && point.charAt(0)=='*'){
							for(int k=0;k<b_match.length;k++){
								if(b_match[k]==Rule.POLICY_NONE){ // if untouched
									b_match[k]=Rule.POLICY_ALLOW;
								}
							}
							break;
						}
						if(point.length()>1 && point.charAt(point.length()-1)=='*'){
							String point_base=point.substring(0, point.length()-2);
							for(int k=0;k<b_match.length;k++){
								if(b_match[k]==Rule.POLICY_NONE){ // if untouched
									if(target_ids[k].startsWith(point_base)){
										b_match[k]=Rule.POLICY_ALLOW;
									}
								}
							}
						}
						for(int k=0;k<b_match.length;k++){
							if(b_match[k]==Rule.POLICY_NONE){ // if untouched
								if(point.equals(target_ids[k])){
									b_match[k]=Rule.POLICY_ALLOW;
								}
							}
						}
					}
				}else if(r.policy==Rule.POLICY_DENY){ // if denied

					for(int j=0;j<r.points.length;j++){
						String point=r.points[j];
						if(point.length()==1 && point.charAt(0)=='*'){
							for(int k=0;k<b_match.length;k++){
								if(b_match[k]==Rule.POLICY_NONE){ // if untouched
									b_match[k]=Rule.POLICY_DENY;
									throw new org.fiap.exception.ForbiddenException("Access Denied."); 
								}
							}
							break;
						}
						if(point.length()>1 && point.charAt(point.length()-1)=='*'){
							String point_base=point.substring(0, point.length()-2);
							for(int k=0;k<b_match.length;k++){
								if(b_match[k]==Rule.POLICY_NONE){ // if untouched
									if(target_ids[k].startsWith(point_base)){
										b_match[k]=Rule.POLICY_DENY;
										throw new org.fiap.exception.ForbiddenException("Access Denied.");
									}
								}
							}
						}
						for(int k=0;k<b_match.length;k++){
							if(b_match[k]==Rule.POLICY_NONE){ // if untouched
								if(point.equals(target_ids[k])){
									b_match[k]=Rule.POLICY_ALLOW;
									throw new org.fiap.exception.ForbiddenException("Access Denied.");
								}
							}
						}
					}					
				}
			}
		}

		for(int i=0;i<b_match.length;i++){
			if(b_match[i]==Rule.POLICY_NONE){
				if(!m_DefaultAllow){
					throw new org.fiap.exception.ForbiddenException("No rules defined for some points, thus denied to access.");			
				}
			}
		}
		//return m_DefaultAllow;
		return true;
	}


	/**
	 * dumpACL -- for debug
	 */
	protected void dumpACL(){

		System.out.println("--- ACL (begin) ---");

		if(m_DefaultAllow){
			System.out.println("Default Allow");
		}else{			
			System.out.println("Default Deny");
		}
		
		// For Static ACL Configuration
		if(m_RoleDefs!=null && m_RoleDefs.size()>0){
			String[] roledefKeys=new String[0];
			roledefKeys=m_RoleDefs.keySet().toArray(roledefKeys);
			for(int i=0;i<roledefKeys.length;i++){
				String[] initiators=m_RoleDefs.get(roledefKeys[i]);
				for(int j=0;j<initiators.length;j++){
					System.out.println("def "+roledefKeys[i]+":"+initiators[j]);
				}
			}
			
			String[] roleKeys=new String[0];
			roleKeys=m_Roles.keySet().toArray(roleKeys);
			for(int i=0;i<roleKeys.length;i++){
				Rule[] rules=m_Roles.get(roleKeys[i]);
				for(int j=0;j<rules.length;j++){
					System.out.print("role "+roleKeys[i]+"["+j+"]: ");
					if(rules[j].method==Rule.METHOD_QUERY){
						System.out.print("method=query; ");
					}else if(rules[j].method==Rule.METHOD_DATA){
						System.out.print("method=data; ");
					}else if(rules[j].method==Rule.METHOD_FETCH){
						System.out.print("method=FETCH; ");
					}else if(rules[j].method==Rule.METHOD_TRAP){
						System.out.print("method=TRAP; ");
					}else if(rules[j].method==Rule.METHOD_WRITE){
						System.out.print("method=WRITE; ");
					}else{
						System.out.print("method=NONE; ");
					}
					if(rules[j].policy==Rule.POLICY_ALLOW){
						System.out.print("policy=allow; ");
					}else if(rules[j].policy==Rule.POLICY_DENY){
						System.out.print("policy=deny; ");
					}
					
					for(int p=0;p<rules[j].points.length;p++){
						System.out.print("id["+p+"]: "+rules[j].points[p]+"; ");
					}
					System.out.println();
				}
			}
		}
		
		System.out.println("--- ACL (end) ---");
	}	
}
