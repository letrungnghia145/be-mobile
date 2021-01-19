package com.nghiale.api.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.nghiale.api.model.User;

public class UserSerialize extends StdSerializer<User> {
	private static final long serialVersionUID = -280293553600869955L;

	protected UserSerialize(Class<User> t) {
		super(t);
	}

	public UserSerialize() {
		this(null);
	}

	@Override
	public void serialize(User value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField("id", "");
		gen.writeObjectField("name", "");
		gen.writeObjectField("phone", "");
		gen.writeObjectField("address", "");
		gen.writeObjectField("", "");
		gen.writeObjectField("", "");
		gen.writeEndObject();
	}
}
