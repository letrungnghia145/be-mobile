package com.nghiale.api.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nghiale.api.serialize.OrderSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
@NoArgsConstructor
@JsonSerialize(using = OrderSerialize.class)
@NamedEntityGraph(name = "Order.items", attributeNodes = @NamedAttributeNode(value = "items", subgraph = "items.product"), subgraphs = @NamedSubgraph(name = "items.product", attributeNodes = @NamedAttributeNode("product")))
public class Order extends AbstractModel {
	private static final long serialVersionUID = 560226111253281070L;
	@Column(unique = true)
	private String orderCode;
	@Temporal(TemporalType.TIMESTAMP)
	private Date purchaseDate;
	private BigDecimal total;
	@ManyToOne(fetch = FetchType.LAZY)
	private User customer;
	private String consigneeName;
	private String consigneePhone;
	private String address;
//	**
	private String payMethod;
	@OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<OrderItem> items;

	@JsonCreator
	public Order(Long customerID, BigDecimal total, String consigneeName, String consigneePhone, String address,
			String payMethod, Set<OrderItem> items) {
		super();
		this.customer = customerID != null ? new User(customerID) : null;
		this.purchaseDate = new Date(System.currentTimeMillis());
		this.total = total;
		this.consigneeName = consigneeName;
		this.consigneePhone = consigneePhone;
		this.address = address;
		this.payMethod = payMethod;
		this.items = items;
	}

	public void addItem(Product product, Long quantity) {
		OrderItem item = new OrderItem(this, product, quantity);
		this.items.add(item);
	}

}
