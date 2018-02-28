package com.bwgproject.parser;

import com.bwgproject.parser.model.WgResult;

import java.util.List;

//@Log4j2
public class BwgParser {

    private BwgScraper scraper;
    private ResponseParser parser;

    public BwgParser() {
        scraper = new BwgScraper();
        parser = new ResponseParser();
    }

    public void parse() {
        String response = scraper.getResponse();
        List<WgResult> results = parser.parseResponse(response);

    }


}
