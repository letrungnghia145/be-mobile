package com.nghiale.api.control.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nghiale.api.control.ProductControl;
import com.nghiale.api.entity.ProductEntity;
import com.nghiale.api.model.Evaluate;
import com.nghiale.api.model.Image;
import com.nghiale.api.model.Product;
import com.nghiale.api.utils.Converter;
import com.nghiale.api.utils.RandomUtils;

@Service
public class ProductControlImpl implements ProductControl {
	@Autowired
	private ProductEntity entity;

	@Override
	public List<Product> getAllProducts() {
		return entity.findAll();
	}

	@Override
	public Product getProduct(Long productID) {
		return entity.findById(productID).get();
	}

	@Override
	@Transactional
	public void addProduct(Product product) {
		product.setProductCode(RandomUtils.randomUUIDCode());
		Product save = entity.save(product);
	}

	@Override
	@Transactional
	public void deleteProduct(Long productID) {
		entity.findByIdToDelete(productID).ifPresent(product -> {
			entity.delete(product);
		});
	}

	@Override
	@Transactional
	public void updateProduct(Product product) {
		entity.findById(product.getId()).ifPresent(bo -> {
			try {
				Converter.convert(product, bo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public List<Evaluate> getAllEvaluates(Long productID) {
		List<Evaluate> evaluates = new ArrayList<>();
		Optional<Product> findByIdWithEvaluatesGraph = entity.findByIdWithEvaluatesGraph(productID);
		findByIdWithEvaluatesGraph.get().getEvaluates().forEach(evaluate -> {
			evaluates.add(evaluate);
		});
		return evaluates;
	}

	@Override
	@Transactional
	public void addEvaluate(Long productID, Evaluate evaluate) {
		evaluate.setEvaluateCode(RandomUtils.randomUUIDCode());
		entity.findByIdWithEvaluatesGraph(productID).ifPresent(product -> product.addEvaluate(evaluate));
	}

	@Override
	public List<Image> getAllImages(Long productID) {
		List<Image> images = new ArrayList<>();
		Optional<Product> findByIdWithImagesGraph = entity.findByIdWithImagesGraph(productID);
		findByIdWithImagesGraph.get().getImages().forEach(image -> {
			images.add(image);
		});
		return images;
	}

//	@Override
//	@Transactional
//	public void addImage(Long productID, Image image) {
//		entity.findByIdWithImagesGraph(productID).ifPresent(product -> product.addImage(image));
//	}

	@Override
	@Transactional
	public void deleteImage(Long productID, Long imageID) {
		entity.findByIdWithImagesGraph(productID).ifPresent(product -> product.deleteImage(new Image(imageID)));
	}

}
