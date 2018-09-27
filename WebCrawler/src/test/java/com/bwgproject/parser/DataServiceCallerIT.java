package com.bwgproject.parser;

import com.bwgproject.parser.utils.TestUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class DataServiceCallerIT {

    private DataServiceCaller dataServiceCaller = new DataServiceCaller(new RestTemplate());

    @Test
    @Ignore
    public void shouldPostResults() throws Exception {
        dataServiceCaller.postResults(TestUtils.getDataRequest());
    }

    @Test
    public void shouldGetFirstResult() throws Exception {
        String response = dataServiceCaller.getMostRecentRecord();
        assertThat(response, notNullValue());
    }
}