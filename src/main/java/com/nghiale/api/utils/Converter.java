package com.nghiale.api.utils;

import java.lang.reflect.Method;
import java.util.Set;

public class Converter {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void convertAllProps(Object from, Object to) throws Exception {
		Class<? extends Object> objectClass = from.getClass();
		Method[] methods = objectClass.getMethods();
		for (Method method : methods) {
			String name = method.getName();
			boolean isSatisfy = name.startsWith("get") && !name.equals("getClass");
			if (isSatisfy) {
				Object value = method.invoke(from);
				boolean isNonNull = value != null;
				Class<?> returnType = method.getReturnType();
				if (isNonNull && returnType.equals(Set.class)) {
					Set fromSet = (Set) value;
					Set toSet = (Set) method.invoke(to);
					fromSet.forEach(itemInFromSet -> {
						if (!toSet.contains(itemInFromSet)) {
							toSet.add(itemInFromSet);
						} else {
							toSet.forEach(itemInToSet -> {
								if (itemInToSet.equals(itemInFromSet))
									try {
										convertAllProps(itemInFromSet, itemInToSet);
									} catch (Exception e) {
									}
							});
						}
					});
				} else if (isNonNull) {
					Method setter = objectClass.getMethod("set" + name.substring(3), returnType);
					setter.invoke(to, value);
				}
			}
		}
	}

	public static void convert(Object from, Object to) throws Exception {
		Class<? extends Object> objectClass = from.getClass();
		Method[] methods = objectClass.getMethods();
		for (Method method : methods) {
			String name = method.getName();
			boolean check = name.startsWith("get") && !name.equals("getClass")
					&& !method.getReturnType().equals(Set.class);
			if (check) {
				Object prop = method.invoke(from);
				if (prop != null) {
					Method setter = objectClass.getMethod("set".concat(name.substring(3)), method.getReturnType());
					setter.invoke(to, prop);
				}
			}
		}
	}
}
