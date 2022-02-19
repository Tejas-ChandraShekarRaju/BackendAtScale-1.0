package com.master.response;

public class BaseResponse {

	private String executionStatus;
	
	private long executionTime;
	
	private String responseCode;
	
	private String errorDetails;

	public String getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long l) {
		this.executionTime = l;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}

	
}
