package com;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.join;

import java.util.ArrayList;
import java.util.List;

public class Route {

	private final String[] _regEx;

	public Route(String...regEx) {
		_regEx = regEx;
	}

	public boolean matches(String url){
		for (String regEx : _regEx) {
			if(url.matches(regEx)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		List<String> clean = new ArrayList<String>();
		for (String token : _regEx[0].split("/")) {
			if(!isRegEx(token)){
				clean.add(token);
			}
		} 
		return clean.isEmpty() ?  _regEx[0] : join(clean, "/");
	}

	private boolean isRegEx(String token) {
		List<String> regExElements= asList("?", "[", "]", "-");
		for (String ch : regExElements) {
			if(token.contains(ch)){
				return true;
			}
		}
		return false;
	}
}
