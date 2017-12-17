package com.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class ChatGUI {

	private JFrame frame;
	private JTextField sendField;

	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatGUI window = new ChatGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	public ChatGUI() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 414, 213);
		frame.getContentPane().add(scrollPane);
		
		JTextArea chatArea = new JTextArea();
		chatArea.setEditable(false);
		scrollPane.setViewportView(chatArea);
		
		sendField = new JTextField();
		sendField.setBounds(10, 230, 337, 21);
		frame.getContentPane().add(sendField);
		sendField.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(357, 229, 67, 23);
		frame.getContentPane().add(btnSend);
		frame.setVisible(true);
	}
}
