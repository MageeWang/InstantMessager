package com.server;
import java.sql.*;

public class DataProcessing {

	public static void main(String[] args) throws Exception{
		Class.forName("com.hxtt.sql.access.AccessDriver");
		Connection con = DriverManager.getConnection("jdbc:Access:///C:\\Workspace\\InstantMessager\\ClientInformatica.accdb");
		Statement stmt = con.createStatement();
		String sql = "select * from ClientInformatica";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			String Username = rs.getString(2);
			String Password = rs.getString(3);
		}
		con.close();
		stmt.close();
		rs.close();
	}
	
}