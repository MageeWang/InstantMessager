package com.server;
import java.util.ArrayList;

import com.common.*;

public class DataConfirm {
	
	private DataProcess dp;
	private UserInfo ui;
	private String account;
	private String password;
	
	public DataConfirm(UserInfo ui) {
		this.ui = ui;
	}
	/*login*/
	public ArrayList confirmLogin() {
		
		account = ui.getUsername();
		password = ui.getPassword();
		ui.getPassword();
		try {
			dp = new DataProcess(ui);
			switch(dp.Compare()) {
				case 0:System.out.println("Login");return dp.getFriendList();
				case 1:System.out.println("Wrong password");break;
				case 2:System.out.println("Not exist");break;
				case 3:System.out.println("Null");break;
			}
		}catch(Exception e1) {
			e1.printStackTrace();
		}
		
		return null;
	}
	/*register*/
	public void confirmRegister() {
		
		account = ui.getUsername();
		password = ui.getPassword();
		try {
			dp = new DataProcess(ui);
			switch(dp.Register()) {
				case 0:System.out.println("Success");break;
				case 1:System.out.println("Been registered");break;
				case 2:System.out.println("Null");break;
			}
		}catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
}
