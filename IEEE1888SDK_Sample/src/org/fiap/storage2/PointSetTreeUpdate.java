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

public class PointSetTreeUpdate implements IDbCommand {

	private String m_ID;
	private String m_Parent;
	private boolean m_IsPoint;
	
	public PointSetTreeUpdate(String id, String parent, boolean ispoint){
		m_ID=id;
		m_Parent=parent;
		m_IsPoint=ispoint;
	}
	
	public boolean execDbCommand(Connection connection) throws SQLException {
		
		try{
			java.sql.Statement stmt=connection.createStatement();
			
			String insert="INSERT INTO pointsettree (id,parent,ispoint) VALUES (\'"+m_ID+"\',";
			if(m_Parent!=null){
				insert=insert+"\'"+m_Parent+"\',";
			}else{
				insert=insert+"null,";
			}
			if(m_IsPoint){
				insert=insert+"true);";
			}else{
				insert=insert+"false);";
			}
			
			try{
				stmt.execute(insert);
			}catch(Exception e){
				String update="UPDATE pointsettree SET ";
				
				if(m_Parent!=null){
					update=update+"parent=\'"+m_Parent+"\', ";
				}else{
					update=update+"parent=null, ";
				}
				if(m_IsPoint){
					update=update+"ispoint=true ";
				}else{
					update=update+"ispoint=false ";
				}
				update=update+"WHERE id=\'"+m_ID+"\';";
				stmt.execute(update);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
	}

}
