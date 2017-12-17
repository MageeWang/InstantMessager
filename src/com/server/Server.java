package com.server;
import com.server.*;
import java.io.*;
import java.net.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.common.UserInfo;

public class Server implements Serializable{

	private ServerSocket ss;
	private Socket s;
		
	static class Task implements Runnable {
		private Socket s;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private UserInfo ui;
		private DataConfirm dc;
		
		public Task(Socket s) {
			this.s = s;
		}
		
		public void run() {
			try {
				System.out.println("Socket has built");
				while(true) {
					ois = new ObjectInputStream(s.getInputStream());
					ui = (UserInfo)ois.readObject();
					dc = new DataConfirm(ui);
					if(ui.Status.equals("$Login")) {
						if(dc.confirmLogin() != null) {
							oos = new ObjectOutputStream(s.getOutputStream());
							oos.writeObject(dc.confirmLogin());
						}
					}
					else if(ui.Status.equals("$Register")) {
						dc.confirmRegister();
					}
				}	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Server() {
		
		JFrame jf = new JFrame();
		jf.setBounds(100, 100, 450, 300);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);
		JButton btnOpen = new JButton("Open");
		JButton btnClose = new JButton("Close");
		btnOpen.setBounds(106, 100, 214, 23);
		btnClose.setBounds(106, 140, 214, 23);
		jf.getContentPane().add(btnOpen);
		jf.getContentPane().add(btnClose);
		jf.setVisible(true);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				
				try {
					System.out.println("Start to listen");
					ss = new ServerSocket(3223);
					while(true) {
						s = ss.accept();
						new Thread(new Task(s)).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
	}
	
	public static void main(String[] args) {
		Server se = new Server();
	}

}


