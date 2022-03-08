package com.master.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.master.constant.Constants;
import com.master.models.Chunk;
import com.master.models.Node;
import com.master.response.BaseResponse;
import com.master.response.ChunkResponse;
import com.slave.node.response.NodeStatusResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("masterdao")
public class MasterDaoImpl implements MasterDao{
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(MasterDaoImpl.class);
	
	public BaseResponse merge(List<Node> nodes) {
		// TODO Auto-generated method stub
		BaseResponse response = new BaseResponse();
		Map<Integer,String> status = new HashMap<>();
		
		for(int i=0;i<nodes.size();i++)
		{
			String tempStatus = persist(nodes.get(i).getChunk(),nodes.get(i).getNodeUrl());
			status.put(nodes.get(i).getNodeId(), tempStatus);
			
		}
		
		status.forEach((k,v) ->{
			if(v!=Constants.Success)
			{
				response.setErrorDetails("Failed to persist at Node "+k);
			}
		});
		
		return response;
	}
	
	public String persist(Chunk c,String url)
	{
		BaseResponse resp = webClientBuilder.build()
				.post()
				.uri(url)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(c),Chunk.class)
				.retrieve()
				.bodyToMono(BaseResponse.class)
				.block();
		
		return resp.getExecutionStatus();
	}

	@Override
	public ChunkResponse getChunk(String url) {
		// TODO Auto-generated method stub
		 ChunkResponse resp =  webClientBuilder.build()
				.get()
				.uri(url)
				.retrieve()
				.bodyToMono(ChunkResponse.class)
				.block();
		 return resp;
	}

	@Override
	public BaseResponse deleteWord(String  url) {
		
		BaseResponse response = new BaseResponse();
		try {
			
				 response =  webClientBuilder.build()
						.delete()
						.uri(url)
						.retrieve()
						.bodyToMono(ChunkResponse.class)
						.block();
				 
				
			}
			
		catch(Exception e)
		{
			e.printStackTrace();
			response.setExecutionStatus(Constants.Failure);
		}
		return response;
	}

	@Override
	public BaseResponse deleteWords(List<String> urls) {
	
		BaseResponse response = new BaseResponse();
		
		try {
			for(String u:urls)
			{
				response = deleteWord(u+"/words");
			}
			response.setExecutionStatus(Constants.Success);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setExecutionStatus(Constants.Failure);
		}
	
		return response;
	}

	@Override
	public BaseResponse enableDisableNode(String url, boolean action) {
		// TODO Auto-generated method stub
		BaseResponse response = new BaseResponse();
		try {
			
				 response =  webClientBuilder.build()
						.patch()
						.uri(url)
						.retrieve()
						.bodyToMono(ChunkResponse.class)
						.block();
				 
				
			}
			
		catch(Exception e)
		{
			e.printStackTrace();
			response.setExecutionStatus(Constants.Failure);
		}
		return response;
	}
	
	public Mono<NodeStatusResponse> getNodeStatus(String url) {

		LOGGER.info(String.format("Masterdao getNodeStatus {%s}", url));
		
	    return webClientBuilder.build()
	    	.get()
	        .uri(url)
	        .retrieve()
	        .bodyToMono(NodeStatusResponse.class);
	}
	
		


}
