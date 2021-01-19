package com.nghiale.api.boundary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nghiale.api.control.OrderControl;
import com.nghiale.api.model.Order;

@RestController
@RequestMapping("/orders")
public class OrderBoundary {
	@Autowired
	private OrderControl control;

	@GetMapping
	@PreAuthorize("hasAuthority('orders:retrieve')")
	public List<Order> getAllOrders() {
		return control.getAllOrders();
	}

	@GetMapping("/{oid}")
	@PreAuthorize("hasAuthority('order:retrieve')")
	public Order getOrder(@PathVariable("oid") Long orderID) {
		return control.getOrder(orderID);
	}

	@PostMapping
	public void addOrder(@RequestBody Order order) {
		control.addOrder(order);
	}
}
