package com.bwgproject.parser;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class DataServiceCaller {

    private static final String DATA_SERVICE = "http://localhost:8080/dataService/addRecords";
    private final RestTemplate restTemplate;

    public void sendRequest(String request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(URI.create(DATA_SERVICE), requestEntity, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Call to data service failed");
        }
    }
}
