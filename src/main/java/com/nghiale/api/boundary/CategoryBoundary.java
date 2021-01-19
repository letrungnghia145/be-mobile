package com.nghiale.api.boundary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nghiale.api.control.CategoryControl;
import com.nghiale.api.model.Category;
import com.nghiale.api.model.Product;

@RestController
@RequestMapping("/categories")
public class CategoryBoundary {
	@Autowired
	private CategoryControl control;

	@GetMapping
	public List<Category> getAllCategory() {
		return control.getAllCategory();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('category:create')")
	public void addCategory(@RequestBody Category category) {
		control.addCategory(category);
	}

	@DeleteMapping("/{cid}")
	@PreAuthorize("hasAuthority('category:delete')")
	public void deleteCategory(@PathVariable("cid") Long categoryID) {
		control.deleteCategory(categoryID);
	}

	@GetMapping("/{cid}/products")
	public List<Product> getAllProductsByCategory(@PathVariable("cid") Long categoryID) {
		return control.getAllProductsByCategory(categoryID);
	}
}
