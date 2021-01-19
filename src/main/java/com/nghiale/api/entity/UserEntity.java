package com.nghiale.api.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nghiale.api.model.Order;
import com.nghiale.api.model.User;

public interface UserEntity extends JpaRepository<User, Long> {
	public Optional<User> findByUserCode(String userCode);

	@Query("SELECT count(u)>0 FROM User u WHERE u.userCode = ?1")
	public boolean isExistingUserCode(String userCode);

	@Query("SELECT count(u)>0 FROM User u WHERE u.email = ?1")
	public boolean isExistingUserEmail(String email);

	@EntityGraph("Customer.orders")
	@Query("SELECT c FROM User c WHERE c.id = ?1")
	public Optional<User> findByIdWithOrdersGraph(Long customerID);

	@EntityGraph("Customer.evaluates")
	@Query("SELECT c FROM User c WHERE c.id = ?1")
	public Optional<User> findByIdWithEvaluatesGraph(Long customerID);

	@EntityGraph("Customer.items")
	@Query("SELECT c FROM User c WHERE c.id = ?1")
	public Optional<User> findByIdWithItemsGraph(Long customerID);

	@Query("SELECT o FROM Order o WHERE o.id = ?2 AND o.customer.id = ?1")
	public Order getOrderDetails(Long customerID, Long orderID);

	@EntityGraph("User.roles")
	public Optional<User> findByEmail(String email);

	@Override
	@EntityGraph("User.roles")
	public List<User> findAll();

	@Override
	@EntityGraph("User.roles")
	public Optional<User> findById(Long id);

	@EntityGraph("User.roles")
	public Optional<User> findUserByEmail(String email);

}
