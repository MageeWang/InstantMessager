package com.client;

import java.awt.*;
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
	private ObjectInputStream ois;
	
	public ListGUI(Socket s) throws Exception {
		this.s = s;

		frame = new JFrame();
		frame.setBounds(100, 100, 200, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		ois = new ObjectInputStream(s.getInputStream());
		this.FriendList = (ArrayList) ois.readObject();
		
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
				String getter = (String) list.getSelectedValue();
				if(list.getValueIsAdjusting())
					new ChatGUI();
			}
		});
		scrollPane.setViewportView(list);
		
		frame.setVisible(true);
	}

}
