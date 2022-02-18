package com.master.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;

import com.master.models.Chunk;
import com.master.models.Node;
import com.master.request.Word;
import com.master.response.BaseResponse;

import reactor.core.publisher.Mono;

public class MasterDaoImpl implements MasterDao{
	
	@Autowired
	private WebClient.Builder webClientBuilder;

	
	public BaseResponse merge(List<Node> nodes) {
		// TODO Auto-generated method stub
		BaseResponse response = new BaseResponse();
		for(Node n:nodes)
		{
			
			response = webClientBuilder.build()
					.post()
					.uri(n.getNodeUrl())
					.accept(MediaType.APPLICATION_JSON)
					.body(Mono.just(n.getChunk()),Chunk.class)
					.retrieve()
					.bodyToMono(BaseResponse.class)
					.block();	
			
		}
		
		return response;
	}


}
