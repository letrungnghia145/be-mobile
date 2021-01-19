package com.nghiale.api.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.nghiale.api.model.Evaluate;

public class EvaluateSerialize extends StdSerializer<Evaluate> {
	private static final long serialVersionUID = 1718388979291846383L;

	protected EvaluateSerialize(Class<Evaluate> t) {
		super(t);
	}

	public EvaluateSerialize() {
		this(null);
	}

	@Override
	public void serialize(Evaluate value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField("id", value.getId());
		gen.writeStringField("customerName", value.getCustomer() != null ? value.getCustomer().getName() : null);
		gen.writeStringField("comment", value.getComment());
		gen.writeNumberField("value", value.getValue() != null ? value.getValue() : null);
		gen.writeEndObject();
	}
}
