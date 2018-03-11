package com.bwgproject.parser;

import com.bwgproject.model.WgResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class BwgService {

    private final BwgScraper scraper;
    private final ResponseParser parser;
    private final DataSerializer serializer;

    public void run() {
        List<WgResult> results = getResults();

        String request = mapToRequest(results);
    }

    private List<WgResult> getResults() {
        String response = scraper.getResponse();
        List<WgResult> results = parser.parseResponse(response);

        return results;
    }

    private String mapToRequest(List<WgResult> results) {
        String request = serializer.serialize(results);
        return request;
    }


}
