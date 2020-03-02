package com.algaworks.algafood.core.jackson;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>>{

	@Override
	public void serialize(Page<?> page, JsonGenerator generator, SerializerProvider serializers) throws IOException {
		generator.writeStartObject();
		generator.writeObjectField("content", page.getContent());
		generator.writeNumberField("size", page.getSize());
		generator.writeNumberField("totalElements", page.getTotalElements());
		generator.writeNumberField("totalPage", page.getTotalPages());
		generator.writeNumberField("number", page.getNumber());
		generator.writeEndObject();
		
	}

}
