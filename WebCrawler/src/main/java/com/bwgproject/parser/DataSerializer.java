package com.bwgproject.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;


@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class DataSerializer {

    private final ObjectMapper objectMapper;

    public String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize data", e);
        }
    }
}
