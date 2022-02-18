package com.master.models;

import java.sql.Timestamp;

public class Chunk {
	
	private int startIndex;
	
	private int EndIndex;
	
	private Timestamp createdTime;
	
	private String[] words;

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

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	
	

}
