package com.server;
import java.sql.*;

public class DataProcessing {

	private String account;
	private String password;
	
	public DataProcessing(String account,String password) {
		this.account = account;
		this.password = password;
	}
	
	public boolean Compare() throws Exception {
		Class.forName("com.hxtt.sql.access.AccessDriver");
		Connection con = DriverManager.getConnection("jdbc:Access:///C:\\Workspace\\InstantMessager\\ClientInformatica.accdb");
		Statement stmt = con.createStatement();
		String sql = "select * from ClientInformatica";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			String loginUsername = rs.getString("Username");
			String loginPassword = rs.getString("Password");
			if(account.equals(loginUsername)) {
				if(password.equals(loginPassword)) {
					return true;
				}
			}
		}
		con.close();
		stmt.close();
		rs.close();
		return false;
	}
	
}