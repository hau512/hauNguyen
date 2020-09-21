package com.track.tracker.response;

import java.util.Set;

public class ErrorResponse {
	private String code;
    private String message;
    private Set<String> details;
     
    public ErrorResponse() {
    	
    }
    
	public ErrorResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public ErrorResponse(String code, String message, Set<String> details) {
		this.code = code;
		this.message = message;
		this.details = details;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Set<String> getDetail() {
		return details;
	}

	public void setDetail(Set<String> details) {
		this.details = details;
	}
}
