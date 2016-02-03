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

import java.util.LinkedList;
import java.util.Queue;

import org.fiap.common.PointData;

public class PointBus extends Thread {

	
	private class MessageElement {
		PointData o;
		PointBusObserver avoidNotify;
	};

	/**
	 * PointBusQueue is a buffer between PointBus.update and PointBus.run(notifyProcess). 
	 * @author Hideya Ochiai
	 * @version 1.0
	 */
	private class PointBusQueue {
		
		Queue<MessageElement> m_MessageQueue;
		
		PointBusQueue(){
			m_MessageQueue=new LinkedList<MessageElement>();
		}
		
		/**
		 * synchronized 
		 * @param msg
		 */
		public synchronized void post(MessageElement o){
			m_MessageQueue.offer(o);
			notifyAll();
		}
		
		/**
		 * @return
		 */
		public synchronized MessageElement peek(){
			return m_MessageQueue.peek();
		}
		
		/**
		 * @return
		 */
		public synchronized MessageElement pop(){
			return m_MessageQueue.poll();
		}
		
		/**
		 * @param timeout millisecond
		 * @return
		 */
		public synchronized MessageElement accept(long timeout){		
			if(m_MessageQueue.size()>0){
				return m_MessageQueue.poll();
			}
			try{
				wait(timeout);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			if(m_MessageQueue.size()>0){
				return m_MessageQueue.poll();
			}
			return null;
		}
		
		/**
		 * @return
		 */
		public synchronized MessageElement accept(){
			
			if(m_MessageQueue.size()>0){
				return m_MessageQueue.poll();
			}
			try{
				wait();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			if(m_MessageQueue.size()>0){
				return m_MessageQueue.poll();
			}
			return null;
		}
		
		/**
		 * @return How many messages are buffered now.
		 */
		public synchronized int count(){
			return m_MessageQueue.size();
		}
	}

	
	java.util.ArrayList<PointBusObserver> m_ObserverArray;
	PointBusQueue m_Queue;
	
	public PointBus(){
		m_ObserverArray=new java.util.ArrayList<PointBusObserver>();
		m_Queue=new PointBusQueue();
		start();
	}
	
	public void addPointBusObserver(PointBusObserver observer){
		if(observer==null){
			return ;
		}
		
		if(!m_ObserverArray.contains(observer)){
			m_ObserverArray.add(observer); // TODO: Adds buffer to enable asynchronous communication
		}
		observer.setPointBus(this);
	}
	
	public void update(PointData o, PointBusObserver avoidNotify){
		MessageElement e=new MessageElement();
		e.o=o;
		e.avoidNotify=avoidNotify;
		m_Queue.post(e);
	}
	
	public void run(){
		while(true){
			try{
				MessageElement e=m_Queue.accept();
				notify(e.o,e.avoidNotify);
			}catch(Exception exp){}
		}
	}
	
	private void notify(PointData o, PointBusObserver avoidNotify) {
		
		java.util.Iterator<PointBusObserver> itr=m_ObserverArray.iterator();
		while(itr.hasNext()){
			PointBusObserver observer=itr.next();
			if(observer!=avoidNotify){
				observer.recvUpdate(o);
			}
		}
	}
}
