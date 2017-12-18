package com.server;

import com.common.*;
import java.sql.*;

public class OutlineMsgProcess {
	
	private DataProcess dp;
	private Message msg;
	private String sql;
	private ResultSet rs;
	
	public OutlineMsgProcess(Message msg) {
		this.msg = msg;
	}
	
	public void addMsg() throws Exception {
		dp = new DataProcess();
		sql = "insert into OutlineMsg (Sender,Getter,Time,Text) values "+"('"+msg.getSender()+"','"+msg.getGetter()+"','"+msg.getTime()+"','"+msg.getText()+"')";
		dp.stmt.executeUpdate(sql);
	}
	
	public void sendMsg(UserInfo ui) throws Exception {
		dp = new DataProcess();
		sql = "select * from OutlineMsg";
		dp.stmt.executeQuery(sql);
		while(rs.next()) {
			ui.Username.equals(rs.getString("Getter"));
		}
	}
	
}
