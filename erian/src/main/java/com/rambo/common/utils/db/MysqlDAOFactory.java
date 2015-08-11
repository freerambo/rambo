package com.rambo.common.utils.db;


import java.sql.*;

public class MysqlDAOFactory extends DAOFactory {
	static String serverName = "172.19.209.15";
	static String sDBDriver = "com.mysql.jdbc.Driver";
	static String dbInstance ="ict_data_tran";
	static String sConnStr = "jdbc:mysql://"+ serverName +":3306/" + dbInstance;
	
	static String dbUser = "root";
	static String dbPwd = "whying123";
//	static NMSRecordDAO dao = 
	public NMSRecordDAO getNMSRecordDAO(){
		return new MysqlNMSRecordDAO();
	}
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(sDBDriver);
			conn = DriverManager.getConnection(sConnStr,dbUser,dbPwd);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeResultSet(ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeStatement(Statement stmt){
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void closeConnection(Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
