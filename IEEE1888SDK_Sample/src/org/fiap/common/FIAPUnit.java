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
import org.fiap.types.Transport;
import org.fiap.types.Header;
import org.fiap.types.Body;
import org.fiap.types.PointSet;
import org.fiap.types.Point;
import org.fiap.types.OK;


public class FIAPUnit implements FIAPAPI {

	DataManager m_DataManager;
	WriteManager m_WriteManager;
	PointBusObserver[] m_ProgramLogics;
	PointBus m_PointBus;
	
	public FIAPUnit(DataManager dm,
					WriteManager write,
					PointBusObserver programLogic ){
		
		m_PointBus=new PointBus();
		m_DataManager=dm;
		m_WriteManager=write;
		if(programLogic!=null){
			m_ProgramLogics=new PointBusObserver[1];
			m_ProgramLogics[0]=programLogic;
		}
		
		if(dm!=null){
			m_PointBus.addPointBusObserver(dm);
		}
		if(write!=null){
			m_PointBus.addPointBusObserver(write);
		}
		if(programLogic!=null){
			m_PointBus.addPointBusObserver(programLogic);
		}
	}
	
	public FIAPUnit(DataManager dm,
			WriteManager write,
			PointBusObserver[] programLogics ){

			m_PointBus=new PointBus();
			m_DataManager=dm;
			m_WriteManager=write;
			if(programLogics!=null && programLogics.length>0){
				m_ProgramLogics=programLogics;
			}

			if(dm!=null){
				m_PointBus.addPointBusObserver(dm);
			}
			if(write!=null){
				m_PointBus.addPointBusObserver(write);
			}
			if(programLogics!=null){
				for(int i=0;i<programLogics.length;i++){
					m_PointBus.addPointBusObserver(programLogics[i]);
				}
			}
	}
	
	public Transport query(Transport t, String initiator_ip) throws org.fiap.common.FIAPException, java.io.IOException{
		return m_DataManager.query(t,initiator_ip);
	}
	
	public Transport data(Transport t, String initiator_ip) throws org.fiap.common.FIAPException, java.io.IOException{
		
		Body body=t.getBody();

		Transport ret=new Transport();
		Header header=new Header();
		ret.setHeader(header);
		
		PointSet[] pointSet=body.getPointSet();
		Point[] point=body.getPoint();

		for(int i=0;pointSet!=null && i<pointSet.length;i++){
			PointData pointData=new PointData(pointSet[i]);
			m_WriteManager.write(pointData);
		}		
		for(int i=0;point!=null && i<point.length;i++){
			PointData pointData=new PointData(point[i]);
			m_WriteManager.write(pointData);
		}
		header.setOK(new OK());
		return ret;	
	}
	
}
