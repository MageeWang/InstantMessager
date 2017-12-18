package com.client;

import java.awt.EventQueue;

public class Client {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new LoginGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
