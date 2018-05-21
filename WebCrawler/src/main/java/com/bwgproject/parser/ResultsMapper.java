package com.bwgproject.parser;

import com.bwgproject.model.PreferredGender;
import com.bwgproject.model.WgResult;
import com.bwgproject.parser.model.DateAvailability;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import static com.bwgproject.parser.Constants.ALT;
import static com.bwgproject.parser.Constants.ANINT;
import static com.bwgproject.parser.Constants.ANY;
import static com.bwgproject.parser.Constants.BERLIN;
import static com.bwgproject.parser.Constants.CLASS;
import static com.bwgproject.parser.Constants.DATE_SPLIT_SHORT;
import static com.bwgproject.parser.Constants.DAYS;
import static com.bwgproject.parser.Constants.DETAIL_VIEW;
import static com.bwgproject.parser.Constants.DOT;
import static com.bwgproject.parser.Constants.ER;
import static com.bwgproject.parser.Constants.EURO;
import static com.bwgproject.parser.Constants.FEMALE;
import static com.bwgproject.parser.Constants.HOURS;
import static com.bwgproject.parser.Constants.HREF;
import static com.bwgproject.parser.Constants.ID;
import static com.bwgproject.parser.Constants.INTERVAL_SPLIT;
import static com.bwgproject.parser.Constants.INTERVAL_SPLIT_DASH;
import static com.bwgproject.parser.Constants.LIST_DETAILS;
import static com.bwgproject.parser.Constants.M;
import static com.bwgproject.parser.Constants.MALE;
import static com.bwgproject.parser.Constants.MEN_NO;
import static com.bwgproject.parser.Constants.MEN_PAT;
import static com.bwgproject.parser.Constants.MINUTES;
import static com.bwgproject.parser.Constants.NAME_TRAIL;
import static com.bwgproject.parser.Constants.NO_PRINT;
import static com.bwgproject.parser.Constants.ONLINE;
import static com.bwgproject.parser.Constants.SECONDS;
import static com.bwgproject.parser.Constants.SIZE_PRICE_SPLIT;
import static com.bwgproject.parser.Constants.SIZE_PRICE_WR;
import static com.bwgproject.parser.Constants.TOTAL_NO;
import static com.bwgproject.parser.Constants.W;
import static com.bwgproject.parser.Constants.WOMEN_NO;
import static com.bwgproject.parser.Constants.WOMEN_PAT;

@Slf4j
@Component
public class ResultsMapper {

    @VisibleForTesting
    public WgResult mapResult(Element element) {

        Element details = getDetails(element);
        String location = getLocation(details);
        String[] sizeAndPrice = getSizePrice(details);
        PreferredGender preferredGender = getPreferredGender(details);

        Map<String, Integer> flatMateInfo = getFlatmateInfo(element);
        LocalDateTime dateOfPosting = getDateOfPosting(element);
        String description = getDescription(element);

        Pair<String, DateAvailability> availabilityPair = parseDatesAvailable(element);


        WgResult wgResult = WgResult.builder()
                .extId(getExtId(element))
                .description(description)
                .text(availabilityPair.getLeft())
                .availableFrom(availabilityPair.getRight().getAvailableFrom())
                .availableTo(availabilityPair.getRight().getAvailableTo())
                .dateOfPosting(dateOfPosting)
                .isLongTerm(availabilityPair.getRight().isLongTerm())
                .size(getSize(sizeAndPrice))
                .price(getPrice(sizeAndPrice))
                .prefGender(preferredGender)
                .flatmates(flatMateInfo.get(TOTAL_NO))
                .women(flatMateInfo.get(WOMEN_NO))
                .men(flatMateInfo.get(MEN_NO))
                .location(location)
                .build();

        return wgResult;
    }

    private PreferredGender getPreferredGender(Element details) {
        String text = details.getElementsByAttributeValue(CLASS, NO_PRINT).attr(ALT);
        if (ANY.matcher(text).find()) {
            return PreferredGender.any;
        }
        if (FEMALE.matcher(text).find()) {
            return PreferredGender.female;
        }
        if (MALE.matcher(text).find()) {
            return PreferredGender.male;
        }
        log.error("No preference for flatmate gender was found. Result will be persisted with NULL instead");

        return null;
    }

    private Element getDetails(Element element) {
        return element.select(SIZE_PRICE_WR).select(DETAIL_VIEW).first();
    }

    private String[] getSizePrice(Element element) {
        return SIZE_PRICE_SPLIT.split(element.text());
    }

    private Map<String, Integer> getFlatmateInfo(Element element) {
        String flatMateInfo = element.select("h3").select("span").attr("title");
        Map<String, Integer> flatMates = new HashMap<>();
        flatMates.put(TOTAL_NO, Integer.valueOf(ER.split(flatMateInfo)[0]));
        Matcher matcher = WOMEN_PAT.matcher(flatMateInfo);
        if (matcher.find()) {
            flatMates.put(WOMEN_NO, Integer.valueOf(W.matcher(matcher.group(0)).replaceAll("")));
        }
        matcher = MEN_PAT.matcher(flatMateInfo);
        if (matcher.find()) {
            flatMates.put(MEN_NO, Integer.valueOf(M.matcher(matcher.group(0)).replaceAll("")));
        }

        return flatMates;
    }

    private LocalDateTime getDateOfPosting(Element element) {
        String timeOnline = element.getElementsMatchingOwnText("Online").text();
        long amount = 0;
        Matcher matcher = ANINT.matcher(timeOnline);
        if (matcher.find()) {
            amount = Long.valueOf(matcher.group());
        }

        if (SECONDS.matcher(timeOnline).find()) {
            return LocalDateTime.now().minusSeconds(amount);
        }
        if (MINUTES.matcher(timeOnline).find()) {
            return LocalDateTime.now().minusMinutes(amount);
        }
        if (HOURS.matcher(timeOnline).find()) {
            return LocalDateTime.now().minusHours(amount);
        }
        if (DAYS.matcher(timeOnline).find()) {
            return LocalDateTime.now().minusDays(amount);
        }

        return getDateTime(timeOnline);
    }

    private LocalDateTime getDateTime(String timeOnline) {
        String dateString = ONLINE.matcher(timeOnline).replaceAll("");
        return getDateTimeFromString(dateString);
    }

    private LocalDateTime getDateTimeFromString(String dateString) {
        String formattedDate = DOT.matcher(dateString).replaceAll("-");
        return LocalDate.parse(formattedDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")).atStartOfDay();
    }

    private String getDescription(Element element) {
        return element.select(".headline").select(".detailansicht").first().text();
    }

    private Pair<String, DateAvailability> parseDatesAvailable(Element element) {
        String textToSplit = element.select("p").not(".list-details-image-wrapper").text();
        boolean isShortTerm = INTERVAL_SPLIT.matcher(textToSplit).find();
        String[] textParts = DATE_SPLIT_SHORT.split(textToSplit);
        String text = textParts[0];
        String[] interval = INTERVAL_SPLIT_DASH.split(textParts[1]);

        DateAvailability availability = DateAvailability.builder()
                .availableFrom(isShortTerm ? getDateTimeFromString(interval[0]) : getDateTimeFromString(textParts[1]))
                .availableTo(isShortTerm ? getDateTimeFromString(interval[1]) : null)
                .build();

        return Pair.of(text, availability);
    }

    private Long getExtId(Element element) {
        return Long.valueOf(LIST_DETAILS.split(element.attr(ID))[1]);
    }

    private String getLocation(Element element) {
        return BERLIN.matcher(NAME_TRAIL.matcher(element.attr(HREF)).replaceAll("")).replaceAll("");
    }

    private Double getSize(String... sizeAndPrice) {
        return Double.valueOf(sizeAndPrice[0]);
    }

    private Double getPrice(String... sizeAndPrice) {
        return Double.valueOf(EURO.matcher(sizeAndPrice[1]).replaceAll(Matcher.quoteReplacement("")));
    }

}
