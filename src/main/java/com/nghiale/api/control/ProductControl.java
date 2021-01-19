package com.nghiale.api.control;

import java.util.List;

import com.nghiale.api.model.Evaluate;
import com.nghiale.api.model.Image;
import com.nghiale.api.model.Product;

public interface ProductControl {
	public List<Product> getAllProducts();

	public Product getProduct(Long productID);

	public void addProduct(Product product);

	public void deleteProduct(Long productID);

	public void updateProduct(Product product);

	public List<Evaluate> getAllEvaluates(Long productID);

	public void addEvaluate(Long productID, Evaluate evaluate);
	
	public List<Image> getAllImages(Long productID);
	
//	public void addImage(Long productID, Image image);
	
	public void deleteImage(Long productID, Long imageID);
	
}
