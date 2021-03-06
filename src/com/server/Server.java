package com.server;
import com.server.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.common.*;

public class Server implements Serializable{

	private ServerSocket ss;
	private Socket s;
	public static HashMap<String,Socket> online = new HashMap<String,Socket>();
	public static ArrayList onlineList;
		
	static class Task implements Runnable {
		private Socket s;
		private Socket aimSocket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private UserInfo ui;
		private DataConfirm dc;
		private Message msg;
		private OutlineMsgProcess omp;
		
		public Task(Socket s) {
			this.s = s;
		}
		
		public void run() {
			try {
				while(true) {
					ois = new ObjectInputStream(s.getInputStream());
					ui = (UserInfo)ois.readObject();
					dc = new DataConfirm(ui);
					if(ui.Status.equals("$Login")) {
						if(dc.confirmLogin() != null) {
							oos = new ObjectOutputStream(s.getOutputStream());
							oos.writeObject(dc.confirmLogin());
							onlineList.addAll(online.keySet());
							oos.writeObject(onlineList);
							online.put(ui.Username,s);
							break;
						}
					}
					else if(ui.Status.equals("$Register")) {
						dc.confirmRegister();
					}
				}
				omp = new OutlineMsgProcess(ui,s);
				//Send outline messages.
				omp.sendMsg();
				//Transmit
				while(true) {
					ois = new ObjectInputStream(s.getInputStream());
					msg = (Message)ois.readObject();
					System.out.println(msg.getSender()+" say "+msg.getText()+" to "+msg.getGetter());
					if(online.containsKey(msg.getGetter())) {
						aimSocket = online.get(msg.getGetter());
						oos = new ObjectOutputStream(aimSocket.getOutputStream());
						oos.writeObject(msg);
						oos.flush();
					}
					else {
						omp = new OutlineMsgProcess(msg);
						omp.addMsg();
					}//Outline
					
				}
			} catch (Exception e) {
				System.out.println(ui.getUsername()+" Outlined");
				online.remove(ui.getUsername());
				Message outlineMsg = new Message(Message.OUTLINE_MSG);
				for(Entry<String, Socket> entry:online.entrySet()) {
					try {
						outlineMsg.setOutUser(ui.getUsername());
						oos = new ObjectOutputStream(entry.getValue().getOutputStream());
						oos.writeObject(outlineMsg);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}	
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


