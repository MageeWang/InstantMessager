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
	public JTextArea chatArea;
	private Message sendMsg=new Message(Message.CHAT_MSG);
	private Message msg;
	private String sender;
	private String getter;
	private Socket s;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public ChatGUI(final Socket s,final String sender,final String getter,final ArrayList UnreadMsg) {
		
		this.s = s;
		this.sender = sender;
		this.getter = getter;
		sendMsg.setSender(sender);
		sendMsg.setGetter(getter);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setTitle(getter+"("+sender+")");
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {	
			public void windowClosing(WindowEvent e) {
				ListGUI.chatList.remove(getter);
				super.windowClosing(e);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 414, 213);
		frame.getContentPane().add(scrollPane);
		
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		scrollPane.setViewportView(chatArea);
		for(int i=0;i<UnreadMsg.size();i++) {
			msg = (Message) UnreadMsg.get(i);
			if(msg.getSender().equals(getter)) {
				chatArea.append(msg.getSender()+" says"+"("+msg.getTime()+")"+":\n"+msg.getText()+"\n");
			}
		}
		
		sendField = new JTextField();
		sendField.setBounds(10, 230, 337, 21);
		frame.getContentPane().add(sendField);
		sendField.setColumns(10);
		
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
					oos.flush();
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
