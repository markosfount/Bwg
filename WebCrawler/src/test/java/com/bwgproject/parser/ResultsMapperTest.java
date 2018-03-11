package com.bwgproject.parser;

import com.bwgproject.model.WgResult;
import com.bwgproject.parser.utils.TestUtils;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.List;

class ResultsMapperTest {

    private ResultsMapper resultsMapper = new ResultsMapper();

    @Test
    public void shouldMapResults() throws Exception {
        List<WgResult> wgResults = resultsMapper.mapResults(TestUtils.getElements());
    }

    @Test
    public void shouldMapResult() throws Exception {
        List<Element> elements = TestUtils.getElements();
        WgResult wgResult = resultsMapper.mapResult(elements.get(0));
    }

}