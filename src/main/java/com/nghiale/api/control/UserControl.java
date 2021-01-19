package com.nghiale.api.control;

import java.util.List;

import com.nghiale.api.model.CartItem;
import com.nghiale.api.model.Evaluate;
import com.nghiale.api.model.Order;
import com.nghiale.api.model.User;

public interface UserControl {
	public List<User> getAllUsers();

	public User getUser(Long userID);

	public void addUser(User user);

	public boolean registryCutomerAccount(String token, String validationCode) throws Exception;

	public void deleteUser(Long userID);

	public void updateUserDetails(User user);

	public List<Evaluate> getAllEvaluates(Long userID);

//	public void addEvaluate(Long userID, Evaluate evaluate);

	public void deleteEvaluate(Long userID, Long evaluateID);

	public List<CartItem> getCart(Long userID);

	public void addItemToCart(Long userID, CartItem item);

	public void updateCartItem(Long userID, Long productID, String action);

	public void deleteItemIncart(Long userID, Long productID);

	public List<Order> getAllOrders(Long userID);

	public Order getOrder(Long userID, Long orderID);

//	public void addOrder(Long userID, Order order);
	public User getUserByUsername(String username);
}