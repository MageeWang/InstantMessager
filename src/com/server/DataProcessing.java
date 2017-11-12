package com.server;
import java.sql.*;

public class DataProcessing {

	public void ConnectAccessFile() throws Exception{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		String dbur1 = "jdbc:odbc:driver={Microsoft Access Driver (*.accdb)};DBQ=C://Workspace//InstantMessager//ClientInformation.accdb";
		Connection con = DriverManager.getConnection(dbur1,"Username","Password");
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery("select * from ClientInformation");
		while(rs.next()) {
			System.out.println(rs.getString("Username"));
			System.out.println(rs.getString("Password"));
			rs.close();
			stmt.close();
			con.close();
		}
	}
	
	public static void main(String[] args) throws Exception{
		DataProcessing ca = new DataProcessing();
		ca.ConnectAccessFile();
	}
}