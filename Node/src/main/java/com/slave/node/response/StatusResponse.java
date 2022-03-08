package com.slave.node.response;

public class StatusResponse extends BaseResponse{
	
	boolean isAlive;
	
	int nodeId;
	
	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public boolean getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(boolean isAlive2) {
		this.isAlive = isAlive2;
	}
	
	

}
