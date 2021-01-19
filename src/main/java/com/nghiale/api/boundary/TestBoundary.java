package com.nghiale.api.boundary;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nghiale.api.control.OrderControl;
//import com.nghiale.api.dto.Wrapper;
import com.nghiale.api.model.Order;

@RestController
public class TestBoundary {
	@Autowired
	private OrderControl control;

//	@GetMapping("/api/test")
//	public List<Wrapper> test() {
//		List<Order> allOrders = control.getAllOrders();
//		List<Wrapper> wrappers = new ArrayList<>();
//		allOrders.forEach(order -> wrappers.add(new Wrapper(order, "http://localhost:8080/orders/" + order.getId())));
//		return wrappers;
//	}
}
