package com.nghiale.api.permission;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;

public enum UserRole {
	@JsonProperty("ROLE_SELLER")
	ROLE_SELLER(Sets.newHashSet(
//		Authority.PRODUCT_RETRIEVE_ALL,
//		Authority.PRODUCT_RETRIEVE,
		Authority.PRODUCT_CREATE,
		Authority.PRODUCT_DELETE,
		Authority.PRODUCT_UPDATE,
		Authority.EVALUATE_PRODUCT_RETRIEVE_ALL,
		Authority.USER_RETRIEVE_ALL,
		Authority.USER_RETRIEVE,
		Authority.EVALUATE_CUSTOMER_RETRIEVE_ALL,
		Authority.ORDER_CUSTOMER_RETRIEVE_ALL,
		Authority.ORDER_CUSTOMER_RETRIEVE,
//		Authority.CATEGORY_RETRIEVE_ALL,
		Authority.CATEGORY_CREATE,
		Authority.CATEGORY_DELETE,
//		Authority.PRODUCT_CATEGORY_RETRIEVE_ALL,
		Authority.ORDER_RETRIVE_ALL,
		Authority.ORDER_RETRIEVE
	), "Role seller: this have almost permission!"),
	@JsonProperty("ROLE_FORWARDER")
	ROLE_FORWARDER(Sets.newHashSet(
//		Authority.PRODUCT_RETRIEVE_ALL,
//		Authority.PRODUCT_RETRIEVE,
//		Authority.PRODUCT_CREATE,
//		Authority.PRODUCT_DELETE,
//		Authority.PRODUCT_UPDATE,
//		Authority.EVALUATE_PRODUCT_RETRIEVE_ALL,
//		Authority.EVALUATE_PRODUCT_CREATE,
//		Authority.USER_RETRIEVE_ALL,
//		Authority.USER_RETRIEVE,
//		Authority.EVALUATE_CUSTOMER_RETRIEVE_ALL,
//		Authority.CART_ITEM_RETRIEVE_ALL,
//		Authority.CART_ITEM_CREATE,
//		Authority.CART_ITEM_DELETE,
//		Authority.CART_ITEM_UPDATE,
//		Authority.ORDER_CUSTOMER_RETRIEVE_ALL,
//		Authority.ORDER_CUSTOMER_RETRIEVE,
//		Authority.CATEGORY_RETRIEVE_ALL,
//		Authority.CATEGORY_CREATE,
//		Authority.CATEGORY_DELETE,
//		Authority.PRODUCT_CATEGORY_RETRIEVE_ALL,
//		Authority.ORDER_RETRIVE_ALL,
//		Authority.ORDER_RETRIEVE,
//		Authority.ORDER_CREATE
	), "Role forwarder: only access into system when they must update order-status"),
	@JsonProperty("ROLE_CUSTOMER")
	ROLE_CUSTOMER(Sets.newHashSet(
//		Authority.PRODUCT_RETRIEVE_ALL,
//		Authority.PRODUCT_RETRIEVE,
//		Authority.EVALUATE_PRODUCT_CREATE,
		Authority.USER_RETRIEVE,
		Authority.EVALUATE_CUSTOMER_RETRIEVE_ALL,
		Authority.CART_ITEM_RETRIEVE_ALL,
		Authority.CART_ITEM_CREATE,
		Authority.CART_ITEM_DELETE,
		Authority.CART_ITEM_UPDATE,
//		Authority.PRODUCT_CATEGORY_RETRIEVE_ALL,
//		Authority.ORDER_CREATE,
		Authority.ORDER_CUSTOMER_RETRIEVE_ALL,
		Authority.ORDER_CUSTOMER_RETRIEVE
	), "Role customer: customer have permission to access their account, retrieve product, create order,...");

	private Set<Authority> permissions;
	private String description;

	private UserRole(Set<Authority> permissions, String description) {
		this.permissions = permissions;
		this.description = description;
	}

	public Set<Authority> getPermission() {
		return permissions;
	}
	
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return this.name();
	}
}
