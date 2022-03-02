package com.slave.node.models;

import java.util.Date;

public class Word {

	String word;
	  
	Date createdTime;
	
	int nodeId;
	
	public Word()
	{
		
	}
	public Word(String word,Date createdTime,int nodeId)
	{
		this.word = word;
		this.createdTime = createdTime;
		this.nodeId = nodeId;
	}
	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	
	
	
	
}
