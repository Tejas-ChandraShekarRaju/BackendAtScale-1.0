package com.master.service;

import com.master.response.BaseResponse;

public interface MasterService {
	
	public BaseResponse saveWords(String[] words);
	
	public BaseResponse getWords();
	
	public BaseResponse deleteWords();
	
	public BaseResponse deleteWord(String word);
	
	public BaseResponse enableDisableNode(String nodeId,String action);
	
	public BaseResponse getNodesByType(String nodeType);

}
