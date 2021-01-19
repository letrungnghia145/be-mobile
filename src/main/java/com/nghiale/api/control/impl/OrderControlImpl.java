package com.nghiale.api.control.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nghiale.api.control.OrderControl;
import com.nghiale.api.entity.OrderEntity;
import com.nghiale.api.model.Order;
import com.nghiale.api.model.OrderItem;

@Service
public class OrderControlImpl implements OrderControl {
	@Autowired
	private OrderEntity entity;

	@Override
	public List<Order> getAllOrders() {
		return entity.findAllWithItemsGraph();
	}

	@Override
	public Order getOrder(Long orderID) {
		return entity.findByIdWithItemsGraph(orderID).get();
	}

	@Override
	@Transactional
	public void addOrder(Order order) {
		Set<OrderItem> temp = new HashSet<>(order.getItems());
		order.setItems(new HashSet<>());
		Order save = entity.save(order);
		temp.forEach(item -> entity.addItem(item.getQuantity(), save.getId(), item.getProduct().getId()));
	}

}
