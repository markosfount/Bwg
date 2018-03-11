package com.bwgproject.parser;

import com.bwgproject.model.WgResult;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class ResponseParserTest {

    private ResponseParser responseParser;

    @Test
    public void shouldParseRespones() throws Exception{

        String response = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("response.html").toURI())));
        List<WgResult> wgResults = responseParser.parseResponse(response);
    }

}