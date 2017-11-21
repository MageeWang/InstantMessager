package com.server;
import java.sql.*;

public class DataProcessing {

	private String account;
	private String password;
	private String sql;
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	
	public DataProcessing(String account,String password) throws Exception {
		this.account = account;
		this.password = password;
		Class.forName("com.hxtt.sql.access.AccessDriver");
		con = DriverManager.getConnection("jdbc:Access:///C:\\Workspace\\InstantMessager\\ClientInformatica.accdb");
		stmt = con.createStatement();
	}
	
	public int Compare() throws Exception {	
		int result = 0;
		if(!account.isEmpty()&&!password.isEmpty()) {
			sql = "select * from ClientInformatica";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				if(account.equals(rs.getString("Username"))) {
					if(password.equals(rs.getString("Password"))) {
						result = 0;
					}
					else
						result = 1;
				}
				else
					result = 2;
			}
		}
		else
			result = 3;

		return result;//if 0 success;if 1 password wrong;if 2 user is not exist;if 3 null;
	}
	
	public int Register() throws Exception {
		int result = 0;
		if(!account.isEmpty()&&!password.isEmpty()) {
			sql = "select * from ClientInformatica";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				if(account.equals(rs.getString("Username"))) {
					result = 1;
				}
			}
			if(result!=1) {
				sql = "insert into ClientInformatica (Username,Password) values "+"('"+account+"','"+password+"')";
				stmt.executeUpdate(sql);
			}
		}
		else
			result = 2;
		return result;//if 0 success;if 1 had been registered;if 2 not entered;
	}
	
}