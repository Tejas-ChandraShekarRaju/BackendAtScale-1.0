package com.master.dao;

import java.util.List;

import com.master.models.Node;
import com.master.response.BaseResponse;
import com.master.response.ChunkResponse;
import com.slave.node.response.NodeStatusResponse;

import reactor.core.publisher.Mono;

public interface MasterDao {
	
	
	public BaseResponse merge(List<Node> nodes);
	
	public ChunkResponse getChunk(String url);

	public BaseResponse deleteWord(String url);

	BaseResponse deleteWords(List<String> uris);
	
	public Mono<NodeStatusResponse> getNodeStatus(String url);

	public BaseResponse enableDisableNode(String nodeId, boolean actionValue);

}
