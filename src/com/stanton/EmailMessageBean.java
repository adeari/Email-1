package com.stanton;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;

public class EmailMessageBean {
	
	static Map<String, Integer> formatedValues = new HashMap<>();

	private SimpleStringProperty sender;
	private SimpleStringProperty subject;
	private SimpleStringProperty size;
	private String content;
	
	public EmailMessageBean(String subject, String sender, int size, String content) {
		this.subject = new SimpleStringProperty(subject);
		this.sender = new SimpleStringProperty(sender);
		this.size = new SimpleStringProperty(formatSize(size));
		this.content = content;
	}


	public String getSender(){
		return sender.get();
	}
	public String getSubject(){
		return subject.get();
	}
	public String getSize(){
		return size.get();
	}
	public String getContent() {
		return content;
	}
	public String formatSize(int size){
		String returnValue;
		if(size <= 0) {
			returnValue = "0";
		}
		else if(size < 1024) {
			returnValue = size + " B";
		}
		else if(size < 1048576) {
			returnValue = size/1024 + " kB";
		}
		else {
			returnValue = size/1048576 + " MB";
		}
		formatedValues.put(returnValue, size);
		return returnValue;
	}
	
}
