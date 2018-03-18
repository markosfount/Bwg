package com.bwgproject.parser;

import com.bwgproject.parser.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BwgServiceTest {

    @Mock
    private BwgScraper scraper;

    @Spy
    private ResponseParser parser = new ResponseParser(new ResultsMapper());

    @Spy
    private DataSerializer serializer = new DataSerializer(new ObjectMapper());

    @Spy
    private DataServiceCaller dataServiceCaller = new DataServiceCaller(new RestTemplate());

    @InjectMocks
    private BwgService service;

    @Test
    public void shouldRunService() throws Exception {
        when(scraper.getResponse()).thenReturn(TestUtils.getScrapedContent());
        service.run();
    }

    @Test
    public void shouldRunServiceWithMiniHtml() throws Exception {
        when(scraper.getResponse()).thenReturn(TestUtils.getScrapedContentMini());
        service.run();
    }

}