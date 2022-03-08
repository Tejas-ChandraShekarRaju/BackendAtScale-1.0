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
import java.util.function.Predicate;

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
import com.master.response.NodeHealthResponse;
import com.master.response.StatusResponse;
import com.master.response.TestResponse;
import com.master.response.WordResponse;
import com.master.service.Util;
import com.slave.node.response.NodeStatusResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("masterservice")
public class MasterServiceImpl implements MasterService{

	@Autowired
	MasterDao masterdao;

	private MetaData md = MetaData.getInstance();
	
	   public static Predicate<String> actionType = new Predicate<String>() {
	        @Override
	        public boolean test(String a)
	        {
	            return a.equalsIgnoreCase(Constants.Enabled);
	        }
	    };
	    
	    public static Predicate<String> cleanWord = new Predicate<String>() {
	        @Override
	        public boolean test(String a)
	        {
	            return (a!=null && !a.equalsIgnoreCase(Constants.Deleted));
	        }
	    };
	    



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
		List<String> words = new ArrayList<>();
		try {
			for(int i=0;i<5;i++)
			{
				ChunkResponse c = masterdao.getChunk(md.getNodeUris().get(i)+"/chunk");
				c.getWords().stream()
				.forEach(word ->{

					if(cleanWord.test(word.getWord())) words.add(word.getWord());

				});


			}

			response.setWords(words);
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


	public BaseResponse enableDisableNode(int nodeId, String action) {
		// TODO Auto-generated method stub
		BaseResponse response = new BaseResponse();
		boolean actionValue = actionType.test(action);
		response = masterdao.enableDisableNode(md.getNodeUris().get(nodeId)+"/node/enableDisable?action="+actionValue,actionValue);
			
			if(response.getExecutionStatus().equals(Constants.Success))
			{
				md.setSlaveStatus(action);
			}
		return response;
	}
	



	public NodeHealthResponse getNodesByType(String nodeType) {
		// TODO Auto-generated method stub
		
		NodeHealthResponse nhr = new NodeHealthResponse();
		try {
			StatusResponse response = new StatusResponse();
			List<NodeStatusResponse> slaveResponse =  Flux.fromIterable(md.getNodeUris())
					.flatMap(url -> masterdao.getNodeStatus(url+"/status").onErrorResume(e -> Mono.empty()))
					.collectList().block();
			response.setSlaveResponse(slaveResponse);



			slaveResponse.stream()
			.forEach(ns->{

				if(ns.getIsAlive()) 
				{
					nhr.getActiveNodes().add(ns.getNodeId());
				}
				else
				{
					nhr.getInactiveNodes().add(ns.getNodeId());
				}

			});
			
			nhr.setExecutionStatus(Constants.Success);
		}
		catch(Exception e)
		{
			nhr.setExecutionStatus(Constants.Failure);
		}

		return nhr;
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


	@Override
	public boolean preHandle() {
		
		synchronized(this)
		{
			if(md.getSlaveStatus().equals(Constants.Disabled)) return false;
					
			else return true;
		}
		
		
	}


}
