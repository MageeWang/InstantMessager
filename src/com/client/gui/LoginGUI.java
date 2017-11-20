package com.client.gui;

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

public class LoginGUI {

	private JFrame frame;
	private JPasswordField txtPassword;
	private JTextField txtAccount;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI window = new LoginGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginGUI() {
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
				String enterUsername = txtAccount.getText();
				String enterPassword = txtPassword.getText();
			}
		});
		btnLogin.setBounds(106, 180, 214, 23);
		frame.getContentPane().add(btnLogin);
		
	}
}