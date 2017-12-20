package com.server;

import com.common.*;

import java.io.ObjectOutputStream;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class OutlineMsgProcess {
	
	private DataProcess dp;
	private UserInfo ui;
	private Message saveMsg;
	
	private String sql;
	private Socket s;
	private ResultSet rs;
	private ArrayList OutlineMsg;
	
	public OutlineMsgProcess(Message msg) {
		this.saveMsg = msg;
	}
	
	public OutlineMsgProcess(UserInfo ui,Socket s) {
		this.ui = ui;
		this.s = s;
	}
	
	public void addMsg() throws Exception {
		dp = new DataProcess();
		sql = "insert into OutlineMsg (Sender,Getter,Time,Text) values "+"('"+saveMsg.getSender()+"','"+saveMsg.getGetter()+"','"+saveMsg.getTime()+"','"+saveMsg.getText()+"')";
		dp.stmt.executeUpdate(sql);
	}
	
	public void sendMsg() throws Exception {
		dp = new DataProcess();
		sql = "select * from OutlineMsg";
		rs = dp.stmt.executeQuery(sql);
		OutlineMsg = new ArrayList();
		while(rs.next()) {
			Message sendMsg = new Message();
			sendMsg.setSender(rs.getString("Sender"));
			sendMsg.setGetter(rs.getString("Getter"));
			sendMsg.setTime(rs.getString("Time"));
			sendMsg.setText(rs.getString("Text"));
			if(ui.Username.equals(sendMsg.getGetter())&&!rs.getBoolean("Sended")){
				OutlineMsg.add(sendMsg);
			}
		}
		ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(OutlineMsg);
		for(int i=0;i<OutlineMsg.size();i++) {
			Message tmsg = (Message) OutlineMsg.get(i);
			dp.stmt.executeUpdate("update OutlineMsg set Sended = not Sended where Text = '"+tmsg.getText()+"' and Time = '"+tmsg.getTime()+"'");
		}
	}
	
}
