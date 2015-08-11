package com.rambo.common.utils.db;


public abstract class DAOFactory {
	
	public static final int ORACLE = 1;
	public static final int SQLSERVER = 2;
	public static final int MYSQL = 3;
	
	public abstract NMSRecordDAO getNMSRecordDAO();
	public static DAOFactory getDAOFactory(int dbType){
		switch(dbType){
//		case ORACLE:
//			return new OracleDAOFactory();
//		case SQLSERVER:
//			return new SQLDAOFactory();
		case MYSQL:
			return new MysqlDAOFactory();
		default:
			return null;
		}
		
	}
}
