package com.master.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.master.constant.Constants;
import com.master.dao.MasterDao;
import com.master.meta.MetaData;
import com.master.models.Chunk;
import com.master.models.Node;
import com.master.models.Word;
import com.master.response.BaseResponse;
import com.master.response.ChunkResponse;
import com.master.response.TestResponse;
import com.master.response.WordResponse;
import com.master.service.Util;

@Service("masterservice")
public class MasterServiceImpl implements MasterService{

	@Autowired
	MasterDao masterdao;

	private MetaData md = MetaData.getInstance();


	public BaseResponse saveWords(String[] words) {
		// TODO Auto-generated method stub
		StopWatch timeMeasure = new StopWatch();
		timeMeasure.start();
		BaseResponse response = new BaseResponse();
		try {

			words = Util.getWordsByMaxLimit(125,words);
			List<Node> nodesPack;
			synchronized(this)
			{
				nodesPack  = createMergePackage(md.getLRUI(),md.getLRUN(),words);
			}

			response = masterdao.merge(nodesPack);
			response.setExecutionStatus(Constants.Success);

		}

		catch(Exception e)
		{
			response.setExecutionStatus(Constants.Failure);
			response.setErrorDetails(e.getLocalizedMessage());

		}

		timeMeasure.stop();		
		response.setExecutionTime(timeMeasure.getLastTaskTimeMillis());

		return response;
	}


	public WordResponse getWords() {
		// TODO Auto-generated method stub
		StopWatch timeMeasure = new StopWatch();
		timeMeasure.start();
		WordResponse response = new WordResponse();

		List<ChunkResponse> chunks = new ArrayList<>();
		try {
			for(int i=0;i<5;i++)
			{
				ChunkResponse c = masterdao.getChunk(md.getNodeUris().get(i)+"/chunk");
				chunks.add(c);
			}

			response.setChunks(chunks);
			response.setExecutionStatus(Constants.Success);
		}

		catch(Exception e)
		{
			response.setExecutionStatus(Constants.Failure);
			response.setErrorDetails(e.getLocalizedMessage());
		}

		timeMeasure.stop();		
		response.setExecutionTime(timeMeasure.getLastTaskTimeMillis());	

		return response;
	}


	public BaseResponse deleteWords() {
		// TODO Auto-generated method stub
		
		return masterdao.deleteWords(md.getNodeUris());
	}


	public BaseResponse deleteWord(String word) {
		// TODO Auto-generated method stub

		//		   Map<Date, String> map = new TreeMap<Date, String>(new Comparator<Date>() {
		//        @Override
		//        public int compare(Date d1, Date d2) {
		//            return d1.after(d2) ? 1 : -1;
		//        }
		//    });
		StopWatch timeMeasure = new StopWatch();
		timeMeasure.start();
		BaseResponse response = new BaseResponse();

		
		try {
			for(int i=0;i<5;i++)
			{
				 response = masterdao.deleteWord(md.getNodeUris().get(i)+"/delete?word="+word);
				
			}

			
			response.setExecutionStatus(Constants.Success);
		}

		catch(Exception e)
		{
			response.setExecutionStatus(Constants.Failure);
			response.setErrorDetails(e.getLocalizedMessage());
		}

		timeMeasure.stop();		
		response.setExecutionTime(timeMeasure.getLastTaskTimeMillis());	

		return response;
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
			n.setNodeUrl(md.getNodeUris().get(currentNode)+"/words");
			Chunk c = new Chunk();
			c.setCreatedTime(date);
			c.setStartIndex(startIndex);
			c.setEndIndex(endIndex);
			c.setNodeId(currentNode);
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
