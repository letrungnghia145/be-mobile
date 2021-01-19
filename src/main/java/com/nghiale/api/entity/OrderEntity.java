package com.nghiale.api.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nghiale.api.model.Order;

public interface OrderEntity extends JpaRepository<Order, Long> {
	@Query("SELECT o FROM Order o")
	@EntityGraph("Order.items")
	public List<Order> findAllWithItemsGraph();

	@Modifying
	@Query(value = "insert into order_item (quantity, `order_id`, product_id) values (?1, ?2, ?3)", nativeQuery = true)
	public void addItem(Long quantity, Long order_id, Long product_id);

	@Query("SELECT o FROM Order o WHERE id = :id")
	@EntityGraph("Order.items")
	public Optional<Order> findByIdWithItemsGraph(@Param("id") Long id);
}
