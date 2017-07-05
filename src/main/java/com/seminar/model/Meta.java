package com.seminar.model;

import static java.util.Arrays.asList;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MultiValuedMap;

import com.seminar.model.entity.Entity;

public class Meta {

	private static final String PRIVATE_MARKER = "_";

	public static Collection<String> signatureOf(Class<? extends Entity> klass) {
		List<String> signature = new ArrayList<String>();
		for (Field field : klass.getDeclaredFields()) {
			if (isPrivateFinal(field) && !hasCollectionType(field)) {
				signature.add(field.getName().replaceFirst(PRIVATE_MARKER, ""));
			}
		}
		return signature;
	}

	private static boolean hasCollectionType(Field field) {
		for (Class<?> klass : asList(Collection.class, MultiValuedMap.class)) {
			if (klass.isAssignableFrom(field.getType())) {
				return true;
			}
		}
		return false;
	}

	private static boolean isPrivateFinal(Field field) {
		return (field.getModifiers() & Modifier.PRIVATE & Modifier.FINAL) == (Modifier.PRIVATE & Modifier.FINAL)
				&& !field.getName().equals("this$0");
	}

	public static Map<String, String> toMap(Entity entity) {
		try {
			Map<String, String> map = new HashMap<>();
			for (Field field : entity.getClass().getDeclaredFields()) {
				if (isPrivateFinal(field) && !hasCollectionType(field)) {
					field.setAccessible(true);
					map.put(field.getName(), field.get(entity).toString());
				}
			}
			return map;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
