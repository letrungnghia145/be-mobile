package com.nghiale.api.control.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nghiale.api.control.CategoryControl;
import com.nghiale.api.entity.CategoryEntity;
import com.nghiale.api.model.Category;
import com.nghiale.api.model.Product;

@Service
public class CategoryControlImpl implements CategoryControl {
	@Autowired
	private CategoryEntity entity;

	@Override
	public List<Category> getAllCategory() {
		return entity.findAll();
	}

	@Override
	public void addCategory(Category category) {
		Category save = entity.save(category);
		System.out.println(save);
	}

	@Override
	public void deleteCategory(Long categoryID) {
		entity.findById(categoryID).ifPresent(category -> entity.delete(category));
	}

	@Override
	public List<Product> getAllProductsByCategory(Long categoryID) {
		List<Product> products = new ArrayList<>();
		Optional<Category> findByIdWithProductsGraph = entity.findByIdWithProductsGraph(categoryID);
		findByIdWithProductsGraph.get().getProducts().forEach(product -> {
			products.add(product);
		});
		return products;
	}

}
