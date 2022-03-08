package com.master.response;

import java.util.List;

import com.slave.node.response.NodeStatusResponse;

public class StatusResponse extends BaseResponse{
	
	List<NodeStatusResponse> slaveResponse;

	public List<NodeStatusResponse> getSlaveResponse() {
		return slaveResponse;
	}

	public void setSlaveResponse(List<NodeStatusResponse> slaveResponse) {
		this.slaveResponse = slaveResponse;
	}
	
}
