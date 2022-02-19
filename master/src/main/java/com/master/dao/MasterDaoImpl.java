package com.master.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;

import com.master.constant.Constants;
import com.master.models.Chunk;
import com.master.models.Node;
import com.master.request.Word;
import com.master.response.BaseResponse;

import reactor.core.publisher.Mono;

@Service("masterdao")
public class MasterDaoImpl implements MasterDao{
	
	@Autowired
	private WebClient.Builder webClientBuilder;

	
	public BaseResponse merge(List<Node> nodes) {
		// TODO Auto-generated method stub
		BaseResponse response = new BaseResponse();
		String[] status = new String[5];
		for(int i=0;i<nodes.size();i++)
		{
			status[i] = persist(nodes.get(i).getChunk(),nodes.get(i).getNodeUrl());
			
		}
		
		for(int i=0;i<5;i++)
		{
			if(status[i]!="Success")
			{
				response.setExecutionStatus(Constants.Failure);
				response.setErrorDetails("Failed to persist at node "+i);
			}
		}
		
		return response;
	}
	
	public String persist(Chunk c,String url)
	{
		String executionStatus = webClientBuilder.build()
				.post()
				.uri(url)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(c),Chunk.class)
				.retrieve()
				.bodyToMono(String.class)
				.block();
		
		return executionStatus;
	}


}
