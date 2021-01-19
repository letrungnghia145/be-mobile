package com.nghiale.api.control;

import java.util.List;

import com.nghiale.api.model.Order;

public interface OrderControl {
	public List<Order> getAllOrders();

	public Order getOrder(Long orderID);

	public void addOrder(Order order);
}
