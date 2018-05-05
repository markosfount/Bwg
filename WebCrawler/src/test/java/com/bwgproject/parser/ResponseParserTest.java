package com.bwgproject.parser;

import com.bwgproject.model.WgResult;
import com.bwgproject.parser.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

class ResponseParserTest {

    private ResponseParser responseParser = new ResponseParser(new ResultsMapper());

    @Test
    public void shouldParseResponse() throws Exception{

        String response = TestUtils.getScrapedContent();
        List<WgResult> wgResults = responseParser.parseResponse(response);
    }

    @Test
    public void shouldParseResponseTest() throws Exception{

        String response = TestUtils.getScrapedContent("response_test_new.html");
        List<WgResult> wgResults = responseParser.parseResponse(response);
    }

}