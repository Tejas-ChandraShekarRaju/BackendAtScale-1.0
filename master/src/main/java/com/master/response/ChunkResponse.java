package com.master.response;

import java.util.List;

import com.master.models.Word;



public class ChunkResponse extends BaseResponse {
	
	List<Word> words;

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}
	
	

}
