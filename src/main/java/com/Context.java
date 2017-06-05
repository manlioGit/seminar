package com;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Context {

	private final HttpServletResponse _response;
	private final HttpServletRequest _request;
	private final Connection _connection;

	public Context(HttpServletRequest request, HttpServletResponse response, Connection connection) {
		_response = response;
		_request = request;
		_connection = connection;
	}
	
	public HttpServletRequest request() {
		return _request;
	}
	
	public HttpServletResponse response() {
		return _response;
	}
	
	public String parameter(String key){
		return _request.getParameter(key);
	}
	
	public String requestUri() {
		return _request.getRequestURI();
	}
	
	public boolean post() {
		return matches("POST");
	}

	public boolean get() {
		return matches("GET");
	}
	
	public Map<String, String> requestMap(){
		Map<String, String> convert = new HashMap<String, String>();
		for (Object key : _request.getParameterMap().keySet()) {
			convert.put(key.toString(), _request.getParameter(key.toString()));
		}
		return convert;
	}
	
	private boolean matches(String method){
		return _request.getMethod().equals(method);
	}

	public Connection connection() {
		return _connection;
	}
}
