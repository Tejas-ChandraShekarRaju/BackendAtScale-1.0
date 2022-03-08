package com.master.service;

import com.master.response.BaseResponse;

public interface MasterService {
	
	public BaseResponse saveWords(String[] words);
	
	public BaseResponse getWords();
	
	public BaseResponse deleteWords();
	
	public BaseResponse deleteWord(String word);
	
	public BaseResponse getNodesByType(String nodeType);

	public BaseResponse enableDisableNode(int nodeId, String action);

	public boolean preHandle();

}
