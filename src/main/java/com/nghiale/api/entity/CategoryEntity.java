package com.nghiale.api.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nghiale.api.model.Category;

public interface CategoryEntity extends JpaRepository<Category, Long> {
	@Query("SELECT c FROM Category c WHERE c.id = ?1")
	@EntityGraph("Category.products")
	public Optional<Category> findByIdWithProductsGraph(Long id);
}
