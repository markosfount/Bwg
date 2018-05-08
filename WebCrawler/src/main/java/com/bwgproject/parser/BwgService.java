package com.bwgproject.parser;

import com.bwgproject.model.WgResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class BwgService {

    private static final int MAX_CALLS = 5;
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

                LocalDateTime dateOfLastRecord = getDateTimeOfLastRecord();

                List<WgResult> results = getResults(dateOfLastRecord);

                String request = mapToRequest(results);

                dataServiceCaller.postResults(request);
            } catch (Throwable t) {
                StringWriter sw = new StringWriter();
                t.printStackTrace(new PrintWriter(sw));
                System.out.println(sw.toString());
            }

        };
    }

    private LocalDateTime getDateTimeOfLastRecord() {
        String response = dataServiceCaller.getMostRecentRecord();
        LocalDateTime dateOfPosting = serializer.deserialize(response).getDateOfPosting();

        return dateOfPosting;
    }

    private List<WgResult> getResults(LocalDateTime dateOfLastRecord) {
        List<WgResult> allResults = new ArrayList<>();

        for (int i = 0; i < MAX_CALLS; i++) {
            String response = scraper.getResponse(i);
            List<WgResult> results = parser.parseResponse(response);
            allResults.addAll(results);
            if (results.stream().anyMatch(datePredicate(dateOfLastRecord))) {
                break;
            }
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        List<WgResult> finalResults = allResults.stream()
//                .filter(datePredicate(dateOfLastRecord).negate())
//                .collect(Collectors.toList());

//        return finalResults;
        return allResults;
    }

    private Predicate<WgResult> datePredicate(LocalDateTime dateOfLastRecord) {
        return wgResult -> wgResult.getDateOfPosting().isBefore(dateOfLastRecord);
    }

    private String mapToRequest(List<WgResult> results) {
        String request = serializer.serialize(results);
        return request;
    }


}
