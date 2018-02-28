package com.bwgproject.parser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class BwgScraper {
    private static final String url = "http://www.wg-gesucht.de/wg-zimmer-in-Berlin.8.0.1.0.html";
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6)";


    public String getResponse() {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(url);
        getRequest.addHeader("User-Agent", USER_AGENT);

        String response = null;
        // add logging
        try {
            HttpResponse httpResponse = client.execute(getRequest);
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status != HttpStatus.SC_OK) {
                throw new RuntimeException(String.format("Bad status: /i", status));
            }
            HttpEntity entity = httpResponse.getEntity();
            response = EntityUtils.toString(entity);
            EntityUtils.consumeQuietly(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(response);
        return response;
    }

}
