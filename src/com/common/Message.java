package com.common;

import java.io.Serializable;
import java.util.*;

public class Message implements Serializable{
	
	private String sender;
	private String getter;
	private String text;
	private String time;
	private String onUser;
	private String outUser;
	public int type;
	public static final int CHAT_MSG = 0;
	public static final int ONLINE_MSG = 1;
	public static final int OUTLINE_MSG = 2;
	
	public Message() {
		
	}
	
	public Message(int type) {
		this.type = type;
	}
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getGetter() {
		return getter;
	}
	public void setGetter(String getter) {
		this.getter = getter;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String sendText) {
		this.text = sendText;
	}

	public String getOnUser() {
		return onUser;
	}

	public void setOnUser(String onUser) {
		this.onUser = onUser;
	}

	public String getOutUser() {
		return outUser;
	}

	public void setOutUser(String outUser) {
		this.outUser = outUser;
	}
	
}
