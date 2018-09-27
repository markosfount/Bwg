package com.bwgproject.parser;

import com.bwgproject.model.WgResult;
import com.bwgproject.parser.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

class ResponseParserTest {

    private ResponseParser responseParser = new ResponseParser(new ResultsMapper());

    @Test
    public void shouldParseResponse() throws Exception{

        String response = TestUtils.getScrapedContent();
        List<WgResult> wgResults = responseParser.parseResponse(response);

        assertThat(wgResults, notNullValue());
    }

    @Test
    public void shouldParseResponseTest() throws Exception{

        String response = TestUtils.getScrapedContent("response_test_new.html");
        List<WgResult> wgResults = responseParser.parseResponse(response);

        assertThat(wgResults, notNullValue());
    }

}