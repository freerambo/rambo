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

public abstract class QueryProcessor {
	
	long m_SessionTTL;
	long m_QueryTTL;
	String m_QueryID;
	String m_InitiatorIP;
	
	QueryProcessor(){}
		
	public String getQueryID(){
		return m_QueryID;
	}
	
	/**
	 * getQueryType
	 * @return "storage" for data retrieval from storage, "stream" for updated data detection
	 */
	protected abstract String getQueryType();
	

	/**
	 * getSessionTTL() -- for state management (will be used by DataManager)
	 * @return
	 */
	protected long getSessionTTL(){	
		return m_SessionTTL;
	}

	/**
	 * setSessionTTL() -- for state management (will be used by DataManager)
	 * @return
	 */
	protected void setSessionTTL(long ttl){
		m_SessionTTL=ttl;
	}
	
	/**
	 * getQueryTTL() -- for state management (will be used by DataManager)
	 * @return
	 */
	protected long getQueryTTL(){
		return m_QueryTTL;
	}
	
	/**
	 * setQueryTTL() -- for state management (will be used by DataManager)
	 * @return
	 */
	protected void setQueryTTL(long ttl){
		m_QueryTTL=ttl;
	}
	
	/**
	 * getInitiatorIP() -- for state management (will be used by DataManager)
	 * @return
	 */
	protected String getInitiatorIP(){
		return m_InitiatorIP;
	}
	
	/**
	 * setInitiatorIP() -- for state management (will be used by DataManager)
	 * @return
	 */
	protected void setInitiatorIP(String initiator_ip){
		m_InitiatorIP=initiator_ip;
	}
	
	
	/**
	 * close() -- finalize the queryProcessor
	 */
	public abstract void close();
}
