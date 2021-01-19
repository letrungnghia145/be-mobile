package com.nghiale.api.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nghiale.api.serialize.ProductSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedEntityGraph(name = "Product.graph", attributeNodes = { @NamedAttributeNode("category"),
		@NamedAttributeNode("images") })
@NamedEntityGraph(name = "Product.images", attributeNodes = { @NamedAttributeNode("images") })
@NamedEntityGraph(name = "Product.delete.graph", attributeNodes = { @NamedAttributeNode("category"),
		@NamedAttributeNode("images"), @NamedAttributeNode("evaluates"), @NamedAttributeNode("items"), })
@NamedEntityGraph(name = "Product.evaluates", attributeNodes = {
		@NamedAttributeNode(value = "evaluates", subgraph = "evaluates.graph") }, subgraphs = @NamedSubgraph(name = "evaluates.graph", attributeNodes = @NamedAttributeNode(value = "customer")))
@JsonSerialize(using = ProductSerialize.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class Product extends AbstractModel {
	private static final long serialVersionUID = 8907993020628938819L;
	@Column(unique = true)
	private String productCode;
	@Column(unique = true)
	private String name;
	private BigDecimal price;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/mm/yyyy")
	private Date mfg;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/mm/yyyy")
	private Date exp;
	private Long stock;
	@Column(columnDefinition = "text")
	private String description;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
	private Set<Image> images;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
	private Set<Evaluate> evaluates;
	@ManyToOne(fetch = FetchType.LAZY)
	private Category category;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "wishlist")
	private Set<User> customers;

	@OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<CartItem> items = new HashSet<>();

	@OneToMany(mappedBy = "product", orphanRemoval = false)
	private Set<OrderItem> orderItems = new HashSet<>();

	public Product(Long id) {
		super(id);
	}

	@JsonCreator
	public Product(String name, Long category, BigDecimal price, Date mfg, Date exp, Long stock, String description,
			Set<Image> images) {
		super();
		this.name = name;
		this.price = price;
		this.mfg = mfg;
		this.exp = exp;
		this.stock = stock;
		this.description = description;
		this.setCategory(new Category(category));
		this.images = new HashSet<>();
		if (images != null) {
			images.forEach(image -> this.addImage(image));
		}
	}

	public void addImage(Image image) {
		this.images.add(image);
		image.setProduct(this);
	}

	public void deleteImage(Image image) {
		this.images.remove(image);
		image.setProduct(null);
	}

	public void addEvaluate(Evaluate evaluate) {
		this.evaluates.add(evaluate);
		evaluate.setProduct(this);
	}

	public void deleteEvaluate(Evaluate evaluate) {
		this.evaluates.remove(evaluate);
		evaluate.setProduct(null);
	}

//	public Image getImage(Long imageID) {
//		for (Iterator<Image> iterator = this.images.iterator(); iterator.hasNext();) {
//			Image next = iterator.next();
//			if (next.getId().equals(imageID)) {
//				return next;
//			}
//		}
//		return null;
//	}

	public void setCategory(Category category) {
		this.category = category;
		category.getProducts().add(this);
	}
//
//	public Evaluate getEvaluate(Long id) {
//		for (Iterator<Evaluate> iterator = this.evaluates.iterator(); iterator.hasNext();) {
//			Evaluate next = iterator.next();
//			if (next.getId().equals(id)) {
//				return next;
//			}
//		}
//		return null;
//	}
}
