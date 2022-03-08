package com.master.response;

import java.util.List;

public class WordResponse extends BaseResponse{
	
	private List<ChunkResponse> chunks;

	public List<ChunkResponse> getChunks() {
		return chunks;
	}

	public void setChunks(List<ChunkResponse> chunks) {
		this.chunks = chunks;
	}

	private List<String> words;

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}
	
	
	
	

}
