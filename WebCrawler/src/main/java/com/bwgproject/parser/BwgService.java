package com.bwgproject.parser;

import com.bwgproject.model.WgResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class BwgService {

    private static final int MAX_CALLS = 10;
    private static final int INTERVAL = 20;
    private final BwgScraper scraper;
    private final ResponseParser parser;
    private final DataSerializer serializer;
    private final DataServiceCaller dataServiceCaller;

    public void run() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(runService(), 0, INTERVAL, TimeUnit.MINUTES);
    }

    private Runnable runService() {
        return () -> {
            try {
                List<WgResult> results = getResults();

                String request = mapToRequest(results);

                dataServiceCaller.postResults(request);
            } catch (Throwable t) {
                StringWriter sw = new StringWriter();
                t.printStackTrace(new PrintWriter(sw));
                System.out.println(sw.toString());
            }

        };
    }

    private List<WgResult> getResults() {
        String response = "";
        for (int i = 0; i < 1; i++) {
            response = scraper.getResponse(i);
        }
        List<WgResult> results = parser.parseResponse(response);

        return results;
    }

    private String mapToRequest(List<WgResult> results) {
        String request = serializer.serialize(results);
        return request;
    }


}
