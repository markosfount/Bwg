package com.bwgproject.parser;

import com.bwgproject.model.WgResult;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class ResponseParser {

    private final ResultsMapper resultsMapper;

    public List<WgResult> parseResponse(String response) {
        Document document = Jsoup.parse(response);
        List<Element> elements = document.select("div[id~=liste-details-ad-\\d+]");

        return resultsMapper.mapResults(elements);
    }

}
