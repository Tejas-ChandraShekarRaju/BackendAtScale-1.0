package com.master.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;

import com.master.response.Word;
import com.master.constant.Constants;
import com.master.meta.MetaData;
import com.master.models.Node;
import com.master.response.BaseResponse;
import com.master.service.Util;

@Service("masterservice")
public class MasterServiceImpl implements MasterService{
	
	
	
	private static String nodeUrl = "http://localhost:8081/api/words";
	
	private static List<String> nodeUris =Arrays.asList(nodeUrl,nodeUrl,nodeUrl,nodeUrl,nodeUrl);
	
	private MetaData md = MetaData.getInstance();
	
	
	public BaseResponse saveWords(String[] words) {
		// TODO Auto-generated method stub
		StopWatch timeMeasure = new StopWatch();
		timeMeasure.start();
		Word response = new Word();
		try {
			
		words = Util.getWordsByMaxLimit(125,words);
		
		List<Node> nodesPack  = reconcile(md.getLRUI(),md.getLRUN(),words);


//			String[] s = webClientBuilder.build()
//					.get()
//					.uri("http://localhost:8081/api/words")
//					.retrieve()
//					.bodyToMono(String[].class)
//					.block();
//			response.setWords(s);

		}

		catch(Exception e)
		{
			response.setExecutionStatus(Constants.Failure);
			response.setErrorDetails(e.getLocalizedMessage());

		}

		timeMeasure.stop();		
		response.setExecutionTime(timeMeasure.getLastTaskTimeMillis());
		response.setExecutionStatus(Constants.Success);
		return response;
	}

	
	public BaseResponse getWords() {
		// TODO Auto-generated method stub
		BaseResponse response = new BaseResponse();
		return null;
	}

	
	public BaseResponse deleteWords() {
		// TODO Auto-generated method stub
		BaseResponse response = new BaseResponse();
		return null;
	}

	
	public BaseResponse deleteWord(String word) {
		// TODO Auto-generated method stub
		BaseResponse response = new BaseResponse();
		return null;
	}

	
	public BaseResponse enableDisableNode(String nodeId, String action) {
		// TODO Auto-generated method stub
		BaseResponse response = new BaseResponse();
		return null;
	}

	
	public BaseResponse getNodesByType(String nodeType) {
		// TODO Auto-generated method stub
		BaseResponse response = new BaseResponse();
		return null;
	}
	
	public List<Node> reconcile(int wordIndex,int nodeIndex,String[] words)
	{
		return null;
	}
	
	
	

}
