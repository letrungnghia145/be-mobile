package com.nghiale.api.permission;

public enum Authority {
//	PRODUCT_RETRIEVE_ALL("products:retrieve"),
//	PRODUCT_RETRIEVE("product:retrieve"),
	PRODUCT_CREATE("product:create"),
	PRODUCT_DELETE("product:delete"),
	PRODUCT_UPDATE("product:update"),
	EVALUATE_PRODUCT_RETRIEVE_ALL("product-evaluates:retrieve"),
//	EVALUATE_PRODUCT_CREATE("product-evaluate:create"),
	USER_RETRIEVE_ALL("users:retrieve"),
	USER_RETRIEVE("user:retrieve"),
	EVALUATE_CUSTOMER_RETRIEVE_ALL("customer-evaluates:retrieve"),
	CART_ITEM_RETRIEVE_ALL("cart:retrieve"),
	CART_ITEM_CREATE("cart-item:create"),
	CART_ITEM_DELETE("cart-item:delete"),
	CART_ITEM_UPDATE("cart-item:update"),
	ORDER_CUSTOMER_RETRIEVE_ALL("customer-orders:retrieve"),
	ORDER_CUSTOMER_RETRIEVE("customer-order:retrieve"),
//	CATEGORY_RETRIEVE_ALL("categories:retrieve"),
	CATEGORY_CREATE("category:create"),
	CATEGORY_DELETE("category:delete"),
//	PRODUCT_CATEGORY_RETRIEVE_ALL("category-products:retrieve"),
	ORDER_RETRIVE_ALL("orders:retrieve"),
//	ORDER_CREATE("order:create"),
	ORDER_RETRIEVE("order:retrieve");

	private final String permission;

	private Authority(String permission) {
		this.permission = permission;
	}
	
	@Override
	public String toString() {
		return permission;
	}
}
