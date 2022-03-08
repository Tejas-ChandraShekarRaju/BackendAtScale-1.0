package com.master.response;

import java.util.ArrayList;
import java.util.List;

public class NodeHealthResponse extends BaseResponse{
	
	List<Integer> activeNodes = new ArrayList<>();
	
	List<Integer> inactiveNodes = new ArrayList<>();

	public List<Integer> getActiveNodes() {
		return activeNodes;
	}

	public void setActiveNodes(List<Integer> activeNodes) {
		this.activeNodes = activeNodes;
	}

	public List<Integer> getInactiveNodes() {
		return inactiveNodes;
	}

	public void setInactiveNodes(List<Integer> inactiveNodes) {
		this.inactiveNodes = inactiveNodes;
	}
	
	
	
	
	

}
