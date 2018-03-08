package com.bwgproject.parser;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class DataServiceCaller {

    private static final String DATA_SERVICE = "";
    private final RestTemplate restTemplate;

    public void sendRequest(String request) {
//        restTemplate.postForEntity(URI.create(DATA_SERVICE), request)
    }
}
