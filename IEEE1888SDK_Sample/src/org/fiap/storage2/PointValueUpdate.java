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

import java.sql.Connection;
import java.sql.SQLException;

public class PointValueUpdate implements IDbCommand {

	private String m_ID;
	private String m_Time;
	private String m_AttrString;
	private String m_Value;
	
	public PointValueUpdate(String id, java.util.Calendar time, String attrString, String value){
		m_ID=id;
		m_Time=org.fiap.util.W3CTimestamp.toString(time);
		m_AttrString=attrString;
		m_Value=value;
	}
	
	public boolean execDbCommand(Connection connection) throws SQLException {
		
		try{
			java.sql.Statement stmt=connection.createStatement();
			
			String insert="INSERT INTO pointvalue (id,time,attrString,value) VALUES (\'"+m_ID+"\',\'"+m_Time+"\',";
			if(m_AttrString!=null){
				insert=insert+"\'"+m_AttrString+"\',";
			}else{
				insert=insert+"null,";
			}
			if(m_Value!=null){
				insert=insert+"\'"+m_Value+"\');";
			}else{
				insert=insert+"null);";
			}
			
			try{
				stmt.execute(insert);
			}catch(Exception e){
				String update="UPDATE pointvalue SET ";
				
				if(m_AttrString!=null){
					update=update+"attrString=\'"+m_AttrString+"\', ";
				}else{
					update=update+"attrString=null, ";
				}
				if(m_Value!=null){
					update=update+"value=\'"+m_Value+"\' ";
				}else{
					update=update+"value=null ";
				}
				update=update+"WHERE id=\'"+m_ID+"\' AND time=\'"+m_Time+"\';";
				stmt.execute(update);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}

}
