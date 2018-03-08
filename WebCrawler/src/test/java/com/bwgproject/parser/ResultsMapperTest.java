package com.bwgproject.parser;

import com.bwgproject.model.WgResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class ResultsMapperTest {

    private ResultsMapper resultsMapper = new ResultsMapper();

    @Test
    public void shouldMapResults() throws Exception {
        List<WgResult> wgResults = resultsMapper.mapResults(getElements());
    }

    @Test
    public void shouldMapResult() throws Exception {
        List<Element> elements = getElements();
        WgResult wgResult = resultsMapper.mapResult(elements.get(0));
    }

    private List<Element> getElements() throws Exception {
        String response = new String(Files.readAllBytes(Paths.get(getClass().getResource("response.html").toURI())));
        Document document = Jsoup.parse(response);
        return document.select("div[id~=liste-details-ad-\\d+]");

    }
}