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

public abstract class StorageQueryProcessor extends QueryProcessor {

	String m_Cursor;
	
	public StorageQueryProcessor(){
		m_Cursor=java.util.UUID.randomUUID().toString();
	}
	
	public String getCursor(){
		return m_Cursor;
	}

	@Override
	protected String getQueryType() {
		return "storage";
	}

	public abstract PointData[] getNextDataBlock() throws FIAPException;
	public abstract boolean isEoF();
	public abstract void close();

}
