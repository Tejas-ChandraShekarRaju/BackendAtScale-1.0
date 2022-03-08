package com.slave.node.response;

public class NodeStatusResponse extends BaseResponse{
	
	boolean isAlive;
	
	int nodeId;

	public boolean getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(boolean isAlive2) {
		this.isAlive = isAlive2;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	
	

}
