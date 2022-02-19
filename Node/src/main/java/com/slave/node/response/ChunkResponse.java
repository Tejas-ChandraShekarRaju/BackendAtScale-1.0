package com.slave.node.response;

import java.util.ArrayList;
import java.util.List;

import com.slave.node.models.Word;

public class ChunkResponse extends BaseResponse {
	
	List<Word> words;

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}
	
	

}
