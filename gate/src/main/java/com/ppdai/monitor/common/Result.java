package com.ppdai.monitor.common;
import lombok.Data;

@Data
public class Result<T> {

	private boolean success;
	
	private T data;
	
	private String message;
	
	public Result(boolean success){
		this.success = success;
	}
	
	public Result(){
		this(true);
	}
	
	public Result(boolean success,String message){
		this.success = success;
		this.message = message;
	}
}