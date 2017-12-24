package com.client;

import java.awt.*;
import com.common.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.net.*;
import java.io.*;
import java.util.*;

public class ListGUI {

	private JFrame frame;
	public final JList list;
	private Socket s;
	public ArrayList FriendList;
	public ArrayList OnlineList;
	public ArrayList UnreadMsg;
	public static HashMap<String,ChatGUI> chatList = new HashMap<String,ChatGUI>();
	private ObjectInputStream ois;
	private String userName;
	private Message msg;
	public boolean listSetable = false;
	
	public ListGUI(final Socket s,final String userName) throws IOException, ClassNotFoundException {
		this.s = s;
		this.userName = userName;

		frame = new JFrame();
		frame.setBounds(100, 100, 250, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(userName);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		ois = new ObjectInputStream(s.getInputStream());
		this.FriendList = (ArrayList) ois.readObject();
		this.OnlineList = (ArrayList) ois.readObject();
		
		ois = new ObjectInputStream(s.getInputStream());
		UnreadMsg = (ArrayList) ois.readObject();
		
		for(int i=0;i<FriendList.size();i++) {
			int count=0;
			String Sender = FriendList.get(i).toString();
			for(int j=0;j<UnreadMsg.size();j++) {
				msg = (Message) UnreadMsg.get(j);
				if(Sender.equals(msg.getSender())) {
					count++;
				}
			}
			if(count!=0)
				FriendList.set(i,Sender+"("+count+")");
		}
				
		class GetMsg implements Runnable {
			
			private Message getMsg;
			private Message msg = new Message();
			private Socket s;
			private ObjectInputStream ois;
					
			public GetMsg(Socket s) {
				this.s = s;
			}
					
			public void run() {
				try {		
					while(true) {
						ois = new ObjectInputStream(s.getInputStream());
						getMsg = (Message) ois.readObject();
						if(getMsg.type==Message.CHAT_MSG) {
							//If get messages and chatGUI has been bulit,append the message to the chat GUI
							if(chatList.containsKey(getMsg.getSender())) {
								chatList.get(getMsg.getSender()).chatArea.append(getMsg.getSender()+" says"+"("+getMsg.getTime()+")"+":\n"+getMsg.getText()+"\n");
							}
							//If it is not exist,add the message into UnreadList and change value of unread message in the friendlist.
							else {
								UnreadMsg.add(getMsg);
								for(int i=0;i<FriendList.size();i++) {
									String getter = (String) FriendList.get(i);
									String getter1="";
									for(int k=0;k<getter.length();k++) {
										if(getter.charAt(k)!='(') {
											getter1+=getter.charAt(k);
										}
										else {
											break;
										}
									}
									FriendList.set(i, getter1);
								}	
								for(int k=0;k<UnreadMsg.size();k++) {
									msg = (Message) UnreadMsg.get(k);
									if(chatList.containsKey(msg.getSender())) {
										chatList.get(msg.getSender()).chatArea.append(msg.getSender()+" says"+"("+msg.getTime()+")"+":\n"+msg.getText()+"\n");
									}
									else {
										for(int i=0;i<FriendList.size();i++) {
											int count=0;
											String Sender = FriendList.get(i).toString();
											for(int j=0;j<UnreadMsg.size();j++) {
												msg = (Message) UnreadMsg.get(j);
												if(Sender.equals(msg.getSender())) {
													count++;
												}
											}
											if(count!=0)
												FriendList.set(i,Sender+"("+count+")");
										}
									}
								}
								list.setListData(FriendList.toArray());
							}
						}
						else if(getMsg.type==Message.ONLINE_MSG) {
							System.out.println(getMsg.getOnUser()+" online");
						}
						else if(getMsg.type==Message.OUTLINE_MSG) {
							System.out.println(getMsg.getOutUser()+" outline");
						}
						
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		new Thread(new GetMsg(s)).start();
			
		list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = (String[]) FriendList.toArray(new String[50]);
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {		
			public void valueChanged(ListSelectionEvent e) {
				if(list.getValueIsAdjusting()) {
					String getter = (String) list.getSelectedValue();
					String getter1="";
					for(int k=0;k<getter.length();k++) {
						if(getter.charAt(k)!='(') {
							getter1+=getter.charAt(k);
						}
						else {
							break;
						}
					}
					if(!chatList.containsKey(getter1)) {
						chatList.put(getter1,new ChatGUI(s,userName,getter1,UnreadMsg));
						for(int i=0;i<UnreadMsg.size();i++) {
							if(((Message) UnreadMsg.get(i)).getSender().equals(getter1)) {
								UnreadMsg.remove(i);
							}
						}
					}
					else
						return;
					if(FriendList.indexOf(getter)!=-1)
						FriendList.set(FriendList.indexOf(getter),getter1);
					list.setListData(FriendList.toArray());
				}	
			}
		});
		
		scrollPane.setViewportView(list);	
		frame.setVisible(true);
	}

}
