package com.aashdit.prod.cmc.framework.core;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ServiceOutcome<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2366194261510551608L;
	
	private T data;
	private Boolean outcome;
	private String message;



    public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Boolean getOutcome() {
		return outcome;
	}
	public void setOutcome(Boolean outcome) {
		this.outcome = outcome;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public ServiceOutcome()
	{
		this.setOutcome(true);
	}

}
