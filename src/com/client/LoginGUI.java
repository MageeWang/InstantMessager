package com.client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.util.ArrayList;
import java.io.*;

import com.common.*;

import com.server.DataProcess;

public class LoginGUI implements Serializable{

	private JFrame frame;
	private JPasswordField txtPassword;
	private JTextField txtAccount;
	private static LoginGUI login;
	private String enterUsername;
	private String enterPassword;
	private DataProcess dp;
	private UserInfo ui;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public LoginGUI() throws Exception {
		initialize();
	}

	private void initialize() throws Exception {

		ui = new UserInfo();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("InstantMessager");
		frame.getContentPane().setLayout(null);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(184, 110, 136, 21);
		frame.getContentPane().add(txtPassword);
		
		JLabel lblAccount = new JLabel("Account:");
		lblAccount.setBounds(106, 67, 68, 15);
		frame.getContentPane().add(lblAccount);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(106, 113, 68, 15);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblMessage = new JLabel("");
		lblMessage.setBounds(106, 155, 214, 15);
		frame.getContentPane().add(lblMessage);
		
		txtAccount = new JTextField();
		txtAccount.setBounds(184, 64, 136, 21);
		frame.getContentPane().add(txtAccount);
		txtAccount.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(socket==null)	socket = new Socket("localhost",3223);
					ui.setUsername(txtAccount.getText());
					ui.setPassword(txtPassword.getText());
					ui.setStatus("$Login");
					oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(ui);
					new ListGUI(socket, txtAccount.getText());
					frame.setVisible(false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}	
			}
		});
		btnLogin.setBounds(106, 180, 214, 23);
		frame.getContentPane().add(btnLogin);
		//register
		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(socket==null)	socket = new Socket("localhost",3223);
					ui.setUsername(txtAccount.getText());
					ui.setPassword(txtPassword.getText());
					ui.setStatus("$Register");
					oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(ui);		
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(106, 213, 214, 23);
		frame.getContentPane().add(btnNewButton);
		frame.setVisible(true);
	}
}