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

public class PointData {
	private org.fiap.types.PointSet m_PointSet;
	private org.fiap.types.Point m_Point;
	
	public PointData(){	
	}
	
	public PointData(org.fiap.types.PointSet pointSet){
		m_PointSet=pointSet;
	}

	public PointData(org.fiap.types.Point point){
		m_Point=point;
	}
	
	public void setPointSet(org.fiap.types.PointSet pointSet){
		m_PointSet=pointSet;
	}

	public void setPoint(org.fiap.types.Point point){
		m_Point=point;
	}

	public org.fiap.types.PointSet getPointSet(){
		return m_PointSet;
	}

	public org.fiap.types.Point getPoint(){
		return m_Point;
	}

	public boolean hasPointSet(){
		if(m_PointSet!=null){
			return true;
		}else{
			return false;
		}
	}

	public boolean hasPoint(){
		if(m_Point!=null){
			return true;
		}else{
			return false;
		}
	}
}
