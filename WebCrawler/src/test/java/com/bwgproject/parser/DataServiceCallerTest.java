package com.bwgproject.parser;

import com.bwgproject.parser.utils.TestUtils;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class DataServiceCallerTest {

    private DataServiceCaller dataServiceCaller = new DataServiceCaller(new RestTemplate());

    @Test
    public void shouldCallDataService() throws Exception {
        dataServiceCaller.sendRequest(TestUtils.getDataRequest());
    }

    @Test
    public void shouldCallDataServiceWithMiniRequest() throws Exception {
        dataServiceCaller.sendRequest(TestUtils.getDataRequestMini());
    }
}