package com.master.dto;

import java.util.List;




 public class SlaveConfigDto {
	
	List<NodeConfigDto> slaveNodes;

	public List<NodeConfigDto>getSlaveNodes() {
		return slaveNodes;
	}

	public void setSlaveNodes(List<NodeConfigDto> slaveNodes) {
		this.slaveNodes = slaveNodes;
	}
	
	

}
