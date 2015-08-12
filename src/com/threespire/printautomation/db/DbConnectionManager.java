package com.threespire.printautomation.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DbConnectionManager {
	
	//JDBC Driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	
	//Database credentials
	static final String USER = "root";
	static final String PASSWORD = "";
	
	public Connection getConnection(){
		Connection conn = null;		
		try {
			
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//Open a connection
			//System.out.println("Connecting database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			//System.out.println("Connected");
			
		} catch (Exception e) {
			System.err.println("Connection Filed.. "+e);
		}		
		return conn;
	}
	
	public void closeDbConnection(Connection conn){
		try {
			if (conn != null) {
				conn.close();
				//System.out.println("Connection closed");
			}
		} catch (Exception e) {
			System.err.println("Failed closing db connection. " +e); 
		}
	}
	
	public Boolean newOrder(Connection conn){
		
		Boolean status = false;
		
		Statement statement;
		String sql = "SELECT COUNT(id) WHERE printed=0";
		ResultSet rs = null;
		
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			if(rs.isFirst()){
				status = true;
			}
			statement.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Error retrieving new Order. "+ e);
		}
		
		return status;
	}
	
}
