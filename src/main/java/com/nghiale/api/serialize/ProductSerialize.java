package com.nghiale.api.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.nghiale.api.model.Product;

public class ProductSerialize extends StdSerializer<Product> {

	private static final long serialVersionUID = 6456735392352956788L;

	protected ProductSerialize(Class<Product> t) {
		super(t);
	}

	public ProductSerialize() {
		this(null);
	}

	@Override
	public void serialize(Product value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField("id", value.getId());
		gen.writeObjectField("productCode", value.getProductCode());
		gen.writeObjectField("name", value.getName());
		gen.writeObjectField("price", value.getPrice());
		gen.writeObjectField("mfg", value.getMfg());
		gen.writeObjectField("exp", value.getExp());
		gen.writeObjectField("stock", value.getStock());
		gen.writeObjectField("description", value.getDescription());
		gen.writeObjectField("category", value.getCategory());
		gen.writeObjectField("images", value.getImages());
		gen.writeEndObject();
	}
}
