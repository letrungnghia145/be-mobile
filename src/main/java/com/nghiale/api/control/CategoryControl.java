package com.nghiale.api.control;

import java.util.List;

import com.nghiale.api.model.Category;
import com.nghiale.api.model.Product;

public interface CategoryControl {
	public List<Category> getAllCategory();

	public void addCategory(Category category);

	public void deleteCategory(Long categoryID);

	public List<Product> getAllProductsByCategory(Long categoryID);
}
