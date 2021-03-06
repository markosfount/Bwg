package com.bwgproject.parser;

import com.bwgproject.model.WgResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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

    public WgResult deserialize(String json) {
        try {
            return objectMapper.readValue(json, WgResult.class);
        } catch (Exception e) {
            throw new RuntimeException("Could not serialize data", e);
        }
    }
}
