package com.seminar.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import com.seminar.model.entity.Entity;
import com.seminar.model.rule.Rule;

public class EntityModel {

	private final MultiValuedMap<String, Rule> _rules;
	private final Map<String, String> _requestMap;
	private final Class<? extends Entity> _klass;
	
	public EntityModel(Class<? extends Entity> klass, MultiValuedMap<String, Rule> rules, Map<String, String> requestMap) {
		_klass = klass;
		_rules = rules;
		_requestMap = requestMap;
	}

	public MultiValuedMap<String, String> validate(){
		MultiValuedMap<String, String> errors = new HashSetValuedHashMap<String, String>();
		for (Entry<String, Collection<Rule>> pair: new HashMap<String, Collection<Rule>>(_rules.asMap()).entrySet()) {
			Collection<Rule> rules = pair.getValue();
			for (Rule rule : rules) {
				String value = _requestMap.get(pair.getKey());
				if(!rule.applyOn(value)){
					errors.put(pair.getKey(), rule.message());
				}
			}
		}
		return errors;
	}

	public boolean isValid() {
		return validate().isEmpty();
	}

	public boolean isBrokenOn(String label) {
		return validate().containsKey(label);
	}
	
	public <T> T  create() {
		try {
			return (T)_klass.getConstructor(java.util.Map.class).newInstance(_requestMap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public Iterable<String> signature(){
		return Meta.signatureOf(_klass);
	}
}
