package com.bwgproject.parser;

import com.bwgproject.model.WgResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class BwgService {

    private static final int MAX_CALLS = 5;
    private static final int INTERVAL = 20;
    private static final double MAGIC_NUMBER = 2.5;
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
                log.error("{}", ExceptionUtils.getStackTrace(t));
            }

        };
    }

    private LocalDateTime getDateTimeOfLastRecord() {
        String response = dataServiceCaller.getMostRecentRecord();

        return serializer.deserialize(response).getDateOfPosting();
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
                log.error("Exception while scraping {}", e);
            }
        }

//        List<WgResult> finalResults = allResults.stream()
//                .filter(datePredicate(dateOfLastRecord).negate())
//                .collect(Collectors.toList());
//
//        return finalResults;
        return allResults;
    }

    private Predicate<WgResult> datePredicate(LocalDateTime dateOfLastRecord) {
        return wgResult -> wgResult.getDateOfPosting()
                .isBefore(dateOfLastRecord.minusMinutes(Math.round(MAGIC_NUMBER * INTERVAL)));
    }

    private String mapToRequest(List<WgResult> results) {
        return serializer.serialize(results);
    }


}
