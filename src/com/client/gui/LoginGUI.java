package com.client.gui;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame{
	
	public LoginGUI(){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//绘制图形界面
		this.setTitle("IM Demo");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(350,240);
		int x = (dim.width-350)/2;
		int y = (dim.height-240)/2;
		this.setLocation(x,y);
		this.setVisible(true);
		
		Container jFrame_content = this.getContentPane();
		jFrame_content.setLayout(new java.awt.FlowLayout());
	}
	
	public static void main(String[] args) {
		LoginGUI lGUI = new LoginGUI();
	}

}
