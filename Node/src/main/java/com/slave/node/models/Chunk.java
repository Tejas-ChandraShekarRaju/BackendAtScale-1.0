package com.slave.node.models;

import java.util.Date;


public class Chunk {
	
	private int startIndex;
	
	private int EndIndex;
	
	private Date createdTime;
	
	private String[] words;
	
	private int nodeId;

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return EndIndex;
	}

	public void setEndIndex(int endIndex) {
		EndIndex = endIndex;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	
	

}
