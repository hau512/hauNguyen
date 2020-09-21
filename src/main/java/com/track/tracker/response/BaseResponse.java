package com.track.tracker.response;

import java.util.List;
import java.util.Set;

public class BaseResponse<R, E> {
	private String message;
	private String code;
	private List<R> result;
    private Set<E> error;
    
	public BaseResponse() {

	}

	public BaseResponse(String message, String code, List<R> result, Set<E> error) {
		this.message = message;
		this.code = code;
		this.result = result;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public void setCode(int code) {
		this.code = String.valueOf(code);
	}

	public List<R> getResult() {
		return result;
	}

	public void setResult(List<R> result) {
		this.result = result;
	}

	public Set<E> getError() {
		return error;
	}

	public void setError(Set<E> error) {
		this.error = error;
	}
}
