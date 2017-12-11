package com.server;
import java.sql.*;
import com.common.*;

public class DataProcessing {

	private String account;
	private String password;
	private String sql;
	private Connection con;
	private Statement stmt;
	private ResultSet rs0;
	private ResultSet rs1;
	
	public DataProcessing(UserInfo ui) throws Exception {
		this.account = ui.getUsername();
		this.password = ui.getPassword();
		Class.forName("com.hxtt.sql.access.AccessDriver");
		con = DriverManager.getConnection("jdbc:Access:///C:\\Workspace\\InstantMessager\\ClientInformatica.accdb");
		stmt = con.createStatement();
	}
	
	public int Compare() throws Exception {	
		int result = 0;
		if(!account.isEmpty()&&!password.isEmpty()) {
			sql = "select * from ClientInformatica";
			rs0 = stmt.executeQuery(sql);
			while(rs0.next()) {
				if(account.equals(rs0.getString("Username"))) {
					if(password.equals(rs0.getString("Password"))) {
						result = 0;
						break;
					}
					else {
						result = 1;
						break;
					}	
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
			rs1 = stmt.executeQuery(sql);
			//验证是否已被注册
			while(rs1.next()) {
				if(account.equals(rs1.getString("Username"))) {
					result = 1;
					break;
				}	
			}
			if(result==0) {
				sql = "insert into ClientInformatica (Username,Password) values "+"('"+account+"','"+password+"')";
				stmt.executeUpdate(sql);
			}
		}
		else
			result = 2;
		return result;//if 0 success;if 1 had been registered;if 2 not entered;
	}
	
}