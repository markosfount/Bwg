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

    private static final String DATA_SERVICE = "http://localhost:8080/dataService";
    private static final String ADD_RECORDS_ENDPOINT = "/addRecords";
    private static final String RETRIEVE_LATEST_ENDPOINT = "/getMostRecent";
    private final RestTemplate restTemplate;


    public void postResults(String request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);

        System.out.println("Calling dataService to store results");
        ResponseEntity<String> response = restTemplate.postForEntity(URI.create(DATA_SERVICE + ADD_RECORDS_ENDPOINT), requestEntity, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Call to data service failed");
        }
    }

    public String getMostRecentRecord() {
        ResponseEntity<String> response = restTemplate.getForEntity(URI.create(DATA_SERVICE + RETRIEVE_LATEST_ENDPOINT), String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Call to data service failed");
        }

        return response.getBody();
    }
}
