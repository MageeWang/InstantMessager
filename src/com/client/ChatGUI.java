package com.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import com.common.*;

public class ChatGUI {

	private JFrame frame;
	private JTextField sendField;
	private Message sendMsg = new Message();
	private String sender;
	private String getter;
	private Socket s;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public ChatGUI(final Socket s,final String sender,final String getter,final ArrayList OutlineMsg) {
		
		this.s = s;
		this.sender = sender;
		this.getter = getter;
		sendMsg.setSender(sender);
		sendMsg.setGetter(getter);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle(getter+"("+sender+")");
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 414, 213);
		frame.getContentPane().add(scrollPane);
		
		final JTextArea chatArea = new JTextArea();
		chatArea.setEditable(false);
		scrollPane.setViewportView(chatArea);
		
		sendField = new JTextField();
		sendField.setBounds(10, 230, 337, 21);
		frame.getContentPane().add(sendField);
		sendField.setColumns(10);
		
		class GetMsg implements Runnable {
			
			private Message getMsg = new Message();
			private Socket s;
			private ObjectInputStream ois;
			
			public GetMsg(Socket s) {
				this.s = s;
			}
			
			public void run() {
				try {
					if(OutlineMsg!=null) {
						for(int i=0;i<OutlineMsg.size();i++) {
						getMsg = (Message) OutlineMsg.get(i);
						System.out.println(getMsg.getSender()+" to "+getMsg.getGetter()+":"+getMsg.getText());
						System.out.println(getter);
						if(getMsg.getSender().equals(getter))
							chatArea.append(getMsg.getSender()+" says"+"("+getMsg.getTime()+")"+":\n"+getMsg.getText()+"\n");
						}
					}
					
					while(true) {
						ois = new ObjectInputStream(s.getInputStream());
						getMsg = (Message) ois.readObject();
						chatArea.append(getMsg.getSender()+" says"+"("+getMsg.getTime()+")"+":\n"+getMsg.getText()+"\n");
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		new Thread(new GetMsg(s)).start();
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(sendField.getText().equals("")) return;
					sendMsg.setTime(df.format(new Date().getTime()));
					sendMsg.setText(sendField.getText());
					sendField.setText("");
					chatArea.append("I say"+"("+sendMsg.getTime()+")"+":\n"+sendMsg.getText()+"\n");
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(sendMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnSend.setBounds(357, 229, 67, 23);
		frame.getContentPane().add(btnSend);
		frame.setVisible(true);
	}

}
