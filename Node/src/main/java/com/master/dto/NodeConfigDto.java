package com.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeConfigDto {
	
	@JsonProperty
	String nodeUrl;

	public String getNodeUrl() {
		return nodeUrl;
	}

	public void setNodeUrl(String nodeUrl) {
		this.nodeUrl = nodeUrl;
	}
	
	

}
