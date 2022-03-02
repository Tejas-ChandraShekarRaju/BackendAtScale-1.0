package com.master.dao;

import java.util.List;

import com.master.models.Node;
import com.master.response.BaseResponse;
import com.master.response.ChunkResponse;

public interface MasterDao {
	
	
	public BaseResponse merge(List<Node> nodes);
	
	public ChunkResponse getChunk(String url);

	public BaseResponse deleteWord(String url);
	

}
