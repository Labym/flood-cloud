package com.labym.flood.common.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.PersistenceException;
import java.io.IOException;

public class JsonJpaConverter<T> implements AttributeConverter<T, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final TypeReference<T> typeReference = new TypeReference<T>() {};

    @Override
    public String convertToDatabaseColumn(T attribute) {

        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new PersistenceException(String.format("convert {} to String failed!", typeReference));
        }

    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        if (StringUtils.isEmpty(dbData)) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, typeReference);
        } catch (IOException e) {
            throw new PersistenceException(String.format("read value as {},failed", typeReference));
        }
    }
}
