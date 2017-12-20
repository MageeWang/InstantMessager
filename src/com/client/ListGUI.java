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
	private Socket s;
	public ArrayList FriendList;
	public ArrayList OutlineMsg;
	private ObjectInputStream ois;
	private String userName;
	private Message msg;
	private ChatGUI cg;
	
	public ListGUI(final Socket s,final String userName) throws Exception {
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
		
		class FriendListSet implements Runnable {

			public void run(){
				try {
					ois = new ObjectInputStream(s.getInputStream());
					OutlineMsg = (ArrayList) ois.readObject();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(OutlineMsg!=null) {
					for(int i=0;i<FriendList.size();i++) {
						int count=0;
						String Sender = FriendList.get(i).toString();
						for(int j=0;j<OutlineMsg.size();j++) {
							msg = (Message) OutlineMsg.get(j);
							if(Sender.equals(msg.getSender())) {
								count++;
							}
						}
						if(count!=0)
							FriendList.set(i,Sender+"("+count+")");
					}
				}		
			}	
		}
		
		new Thread(new FriendListSet()).start();
			
		final JList list = new JList();
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
					new ChatGUI(s,userName,getter1,OutlineMsg);
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
