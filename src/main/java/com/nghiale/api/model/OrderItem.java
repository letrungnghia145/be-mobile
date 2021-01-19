package com.nghiale.api.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nghiale.api.serialize.OrderItemSerialize;

import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonSerialize(using = OrderItemSerialize.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItem {
	@EmbeddedId
	private PkOrderItem pk;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("orderId")
	@Include
	private Order order;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("productId")
	@Include
	private Product product;
	@Column(columnDefinition = "int(5)")
	private Long quantity;

	public OrderItem(Order order, Product product, Long quantity) {
		super();
		this.pk = new PkOrderItem(order.getId(), product.getId());
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}

	@JsonCreator
	public OrderItem(Long productID, Long quantity) {
		super();
		this.product = new Product(productID);
		this.quantity = quantity;
	}
}
