package com.server;
import com.server.*;
import java.io.*;
import java.net.*;

import com.common.UserInfo;

public class Server implements Serializable{

	private ObjectInputStream ois;
	private UserInfo ui;
	private DataConfirm dc;
	
	public Server() {
		
		try {
			System.out.println("Start to listen");
			ServerSocket ss = new ServerSocket(3223);
			Socket s = ss.accept();
			System.out.println("Socket has built");
			ois = new ObjectInputStream(s.getInputStream());
			ui = (UserInfo)ois.readObject();
			dc = new DataConfirm(ui);
			dc.confirmLogin();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Server se = new Server();

	}

}
