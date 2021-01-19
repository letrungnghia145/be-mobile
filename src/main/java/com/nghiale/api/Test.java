package com.nghiale.api;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nghiale.api.model.Image;
import com.nghiale.api.model.Product;

public class Test {
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

	public static void main(String[] args) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Set<Image> images = new HashSet<>();
		images.add(new Image(3L, "Image3", "des img3"));
		images.add(new Image(1L, "image1", "image"));
		Product product = new Product("Product 01", 1L, BigDecimal.valueOf(50.00), new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 10L, null, null);

		Set<Image> images2 = new HashSet<>();
		Image i1 = new Image(1L, "image1", "des img1");
		Image i2 = new Image(2L, "image2", "des img2");
		images2.add(i1);
		images2.add(i2);
		Product product2 = new Product("Product 01", 2L, BigDecimal.valueOf(50.00),
				new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 7L, null, images2);
		System.out.println("  1 : " + mapper.writeValueAsString(product));
		System.out.println("  2 : " + mapper.writeValueAsString(product2));

		Long start = System.currentTimeMillis();

		Test.convert(product, product2);

		Long end = System.currentTimeMillis();
		System.out.println(end - start + "ms");

		System.out.println("1->2: " + mapper.writeValueAsString(product2));

	}
}
