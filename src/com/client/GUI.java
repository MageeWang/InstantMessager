package com.client;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.server.DataProcessing;

public class GUI {

	private JFrame frame;
	private JPasswordField txtPassword;
	private JTextField txtAccount;
	String enterUsername;
	String enterPassword;
	DataProcessing dp;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				enterUsername = txtAccount.getText();
				enterPassword = txtPassword.getText();
				try {
					dp = new DataProcessing(enterUsername,enterPassword);
					switch(dp.Compare()) {
						case 0:System.out.println("Success");break;
						case 1:System.out.println("Wrong password");break;
						case 2:System.out.println("Not exist");break;
						case 3:System.out.println("Null");break;
					}
				}catch(Exception e1) {
					e1.printStackTrace();
				}	
			}
		});
		btnLogin.setBounds(106, 180, 214, 23);
		frame.getContentPane().add(btnLogin);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enterUsername = txtAccount.getText();
				enterPassword = txtPassword.getText();
				try {
					dp = new DataProcessing(enterUsername,enterPassword);
					switch(dp.Register()) {
						case 0:System.out.println("Success");break;
						case 1:System.out.println("Been registered");break;
						case 2:System.out.println("Null");break;
					}
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(106, 213, 214, 23);
		frame.getContentPane().add(btnNewButton);
	}
}