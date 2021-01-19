package com.nghiale.api.control.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nghiale.api.control.UserControl;
import com.nghiale.api.entity.ProductEntity;
import com.nghiale.api.entity.UserEntity;
import com.nghiale.api.exception.ConfirmationCodeError;
import com.nghiale.api.model.CartItem;
import com.nghiale.api.model.Evaluate;
import com.nghiale.api.model.Order;
import com.nghiale.api.model.Product;
import com.nghiale.api.model.Role;
import com.nghiale.api.model.User;
import com.nghiale.api.utils.Converter;
import com.nghiale.api.utils.RandomUtils;
import com.nghiale.api.utils.TimeUtils;
import com.nghiale.api.utils.TokenUtils;

@Service
public class UserControlImpl implements UserControl {
	@Autowired
	private UserEntity userEntity;
	@Autowired
	private ProductEntity productEntity;
	@Autowired
	private ObjectMapper mapper;

	@Override
	public List<User> getAllUsers() {
		return userEntity.findAll();
	}

	@Override
	public User getUser(Long userID) {
		return userEntity.findById(userID).get();
	}

	@Override
	public void addUser(User user) {
		user.setUserCode(RandomUtils.randomUserCode());
		userEntity.save(user);
	}

	@Override
	@Transactional
	public boolean registryCutomerAccount(String token, String inputCode) throws Exception {
		Map<String, Object> information = TokenUtils.getInfomationFromToken(token);
		User user = mapper.convertValue(information.get("user"), User.class);
		String validationCode = (String) information.get("validationCode");
		Long expiredDate = (Long) information.get("expiredDate");
		if (!TimeUtils.isExpired(new Date(expiredDate)) && inputCode.equals(validationCode)) {
			user.addRole(new Role(3L));
			addUser(user);
			return true;
		} else {
			throw new ConfirmationCodeError(!inputCode.equals(validationCode) ? "Invalid code!" : "Expired code!");
		}
	}

	@Override
	@Transactional
	public void deleteUser(Long userID) {
		userEntity.findById(userID).ifPresent(user -> {
			userEntity.delete(user);
		});
	}

	@Override
	@Transactional
	public void updateUserDetails(User user) {
		userEntity.findById(user.getId()).ifPresent(bo -> {
			try {
				Converter.convert(user, bo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public List<Evaluate> getAllEvaluates(Long userID) {
		List<Evaluate> evaluates = new ArrayList<>();
		Optional<User> findById = userEntity.findByIdWithEvaluatesGraph(userID);
		findById.get().getEvaluates().forEach(evaluate -> {
			evaluates.add(evaluate);
		});
		return evaluates;
	}

	@Override
	public void deleteEvaluate(Long userID, Long evaluateID) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CartItem> getCart(Long userID) {
		List<CartItem> cartItems = new ArrayList<>();
		Optional<User> findByIdWithItemsGraph = userEntity.findByIdWithItemsGraph(userID);
		findByIdWithItemsGraph.get().getItems().forEach(item -> {
			cartItems.add(item);
		});
		return cartItems;
	}

	@Override
	@Transactional
	public void addItemToCart(Long userID, CartItem item) {
		Product mergeProduct = productEntity.getOne(item.getProduct().getId());
		userEntity.findByIdWithItemsGraph(userID)
				.ifPresent(customer -> customer.addCartItem(mergeProduct, item.getQuantity()));
	}

	@Override
	@Transactional
	public void deleteItemIncart(Long userID, Long productID) {
		Product mergeProduct = productEntity.getOne(productID);
		userEntity.findByIdWithItemsGraph(userID).ifPresent(customer -> customer.removeCartItem(mergeProduct));
	}

	@Override
	@Transactional
	public void updateCartItem(Long userID, Long productID, String action) {
		Product mergeProduct = productEntity.getOne(productID);
		userEntity.findByIdWithItemsGraph(userID).ifPresent(customer -> {
			customer.updateCart(mergeProduct, action);
		});
	}

	@Override
	public List<Order> getAllOrders(Long userID) {
		List<Order> orders = new ArrayList<>();
		Optional<User> findByIdWithOrdersGraph = userEntity.findByIdWithOrdersGraph(userID);
		findByIdWithOrdersGraph.get().getOrders().forEach(order -> {
			orders.add(order);
		});
		return orders;
	}

	@Override
	public Order getOrder(Long userID, Long orderID) {
		return userEntity.getOrderDetails(userID, orderID);
	}

	@Override
	public User getUserByUsername(String username) {
		return userEntity.findByEmail(username).get();
	}

//	@Override
//	public void addOrder(Long userID, Order order) {
//		customerEntity.findByIdWithOrdersGraph(userID).ifPresent(customer -> customer.addOrder(order));
//	}

}
