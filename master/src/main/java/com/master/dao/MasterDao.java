package com.master.dao;

import java.util.List;

import com.master.models.Node;
import com.master.response.BaseResponse;

public interface MasterDao {
	
	
	public BaseResponse merge(List<Node> nodes);
	
	

}
