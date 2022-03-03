package com.master.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;




 public class SlaveConfigDto {
	
	@JsonProperty
	List<NodeConfigDto> slaveNodes;

	public List<NodeConfigDto>getSlaveNodes() {
		return slaveNodes;
	}

	public void setSlaveNodes(List<NodeConfigDto> slaveNodes) {
		this.slaveNodes = slaveNodes;
	}
	
	

}
