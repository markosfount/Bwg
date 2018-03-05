package com.bwgproject.parser;

import com.bwgproject.parser.model.WgResult;

import java.util.List;

//@Log4j2
public class BwgService {

    private BwgScraper scraper;
    private ResponseParser parser;

    public BwgService() {
        scraper = new BwgScraper();
        parser = new ResponseParser();
    }

    public List<WgResult> getResults() {
        String response = scraper.getResponse();
        List<WgResult> results = parser.parseResponse(response);

        return results;
    }


}
