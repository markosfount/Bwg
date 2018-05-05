package com.bwgproject.parser;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class WebCrawlerIntegrationTest {

    @Autowired
    private BwgService service;

    @Test
    @Ignore
    public void shouldRunService() throws Exception {
        service.run();
    }

}