package com.common;

import java.io.Serializable;
import java.util.*;

public class Message implements Serializable{
	
	private String sender;
	private String getter;
	private String text;
	private String time;
	
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
	
}
