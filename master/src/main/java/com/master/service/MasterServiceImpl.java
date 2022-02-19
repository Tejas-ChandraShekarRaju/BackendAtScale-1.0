package com.master.service;

import java.util.Date;
import java.sql.Timestamp;
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
import com.master.models.Chunk;
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
		
		
		synchronized(this)
		{
			List<Node> nodesPack  = createMergePackage(md.getLRUI(),md.getLRUN(),words);
		}
		

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
	
	public List<Node> createMergePackage(int wordIndex,int nodeIndex,String[] words)
	{
		List<Node> nodesData = new ArrayList<>();
		
		Date date = new Date();
		Timestamp ts=new Timestamp(date.getTime());
		int startIndex = wordIndex;
		int wordIndexPointer = 0;
		int nextStartWordIndex = wordIndex;
		int nextStartNodeIndex = nodeIndex;
		
	    for (int i = nodeIndex; i < 5 + nodeIndex && wordIndexPointer<words.length; i++) {
	    	int remainingWords = words.length - wordIndexPointer;
	    	int slotsRemaining = (25 - startIndex);
	    	int endIndex;	
	    		if(slotsRemaining > remainingWords)
	    		{
	    			endIndex = startIndex+remainingWords;
	    		}
	    		else endIndex = startIndex+slotsRemaining;
	    		
	    	int currentNode = i % 5;
	    	Node n = new Node();
	    	n.setNodeId(currentNode);
	    	n.setNodeUrl(nodeUrl);
	    	Chunk c = new Chunk();
	    	c.setCreatedTime(ts);
	    	c.setStartIndex(startIndex);
	    	c.setEndIndex(endIndex);
	    	c.setWords(Arrays.copyOfRange(words,wordIndexPointer,wordIndexPointer+slotsRemaining));
	    	n.setChunk(c);
	    	wordIndexPointer = wordIndexPointer+slotsRemaining;
	    	startIndex = 0;
	    	
	    	nodesData.add(n);
	    	nextStartNodeIndex = currentNode;
	    	nextStartWordIndex = endIndex;
	    	
	    	
    		}
	    if(nextStartWordIndex>=25)
	    {
	    	nextStartNodeIndex = (nextStartNodeIndex+1)%5;
	    	nextStartWordIndex = 0;
	    }
	    System.out.println("nextStartNode"+""+nextStartNodeIndex+""+nextStartWordIndex);
	    md.setLRUI(nextStartWordIndex);
	    md.setLRUN(nextStartNodeIndex);
		
		return nodesData;
	}
	
	

	
	

}
