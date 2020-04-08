package com.techchefs.gmailloginlogout.utility;

public class Response {

	
	private String message;
	private int statusCode;
	private String token;
	private Object obj;

	public Response(String message, int statusCode,String token,Object obj) {
		this.message = message;
		this.statusCode = statusCode;
		this.token=token;
		this.obj=obj;
	}
	public Response(String message, int statusCode,String token) {
		this.message = message;
		this.statusCode = statusCode;
		this.token=token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
}
