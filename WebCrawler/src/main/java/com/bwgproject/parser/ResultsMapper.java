package com.bwgproject.parser;

import com.bwgproject.model.WgResult;
import com.bwgproject.parser.model.DateAvailability;
import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.bwgproject.parser.Constants.*;

@Component
public class ResultsMapper {

    public List<WgResult> mapResults(List<Element> elements) {
        // FIXME return directly
        List<WgResult> wgResults = elements.stream()
                .map(this::mapResult)
                .collect(Collectors.toList());

        return wgResults;
    }

    @VisibleForTesting
    public WgResult mapResult(Element element) {

        Element details = getDetails(element);
        String[] sizeAndPrice = getSizePrice(details);
        Map<String, Integer> flatMateInfo = getFlatmateInfo(element);

        // FIXME
        LocalDateTime dateOfPosting = getDateOfPosting(element);

        String description = getDescription(element);

        Pair<String, DateAvailability> availabilityPair = parseDatesAvailable(element);
        String location = getLocation(availabilityPair.getLeft());


        WgResult wgResult = WgResult.builder()
                .extId(getExtId(element))
                .name(getName(details))
                .description(description)
                .text(availabilityPair.getLeft())
                .availableFrom(availabilityPair.getRight().getAvailableFrom())
                .availableTo(availabilityPair.getRight().getAvailableTo())
                .isLongTerm(availabilityPair.getRight().isLongTerm())
                .size(getSize(sizeAndPrice))
                .price(getPrice(sizeAndPrice))
                .flatmates(flatMateInfo.get(TOTAL_NO))
                .women(flatMateInfo.get(WOMEN_NO))
                .men(flatMateInfo.get(MEN_NO))
                .location(location)
                .build();

        return wgResult;
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

    // FIXME replace Date by LocalDateTime
    private LocalDateTime getDateOfPosting(Element element) {
        String timeOnline = element.getElementsMatchingOwnText("Online").text();
        if (MINUTES.matcher(timeOnline).find()) {
            return LocalDateTime.now().minusMinutes(0);
        }
        return LocalDateTime.now();
    }

    private String getDescription(Element element) {
        return element.select(".headline").select(".detailansicht").first().text();
    }

    private Pair<String, DateAvailability> parseDatesAvailable(Element element) {
        String textToSplit = element.select("p").not(".list-details-image-wrapper").text();
        boolean isLongTerm = DATE_SPLIT.matcher(textToSplit).find();
        String[] textParts = isLongTerm ? DATE_SPLIT.split(textToSplit) : DATE_SPLIT_SHORT.split(textToSplit);
        String text = textParts[0];
        String[] interval = INTERVAL_SPLIT.split(textParts[1]);

        DateAvailability availability = DateAvailability.builder()
                .availableFrom(isLongTerm ? parseDate(textParts[1]) : parseDate(interval[0]))
                .availableTo(isLongTerm ? null : parseDate(interval[1]))
                .build();

        return Pair.of(text, availability);
    }

    private Date parseDate(String date) {
        String[] dateNums = Pattern.compile(".", Pattern.LITERAL).split(date);
        // FIXME replace deprecated date format
        return new Date(Integer.valueOf(dateNums[2]) + 100, Integer.valueOf(dateNums[1]) -1, Integer.valueOf(dateNums[0]));
    }

    private String getLocation(String text) {
        Matcher matcher = LOCATION_PAT.matcher(text);

        return matcher.find() ? COMMA_PAT.matcher(matcher.group(0)).replaceAll("") : "";
    }

    private Long getExtId(Element element) {
        return Long.valueOf(LIST_DETAILS.split(element.attr(ID))[1]);
    }

    private String getName(Element element) {
        return DASH.matcher(NAME_TRAIL.matcher(element.attr("href")).replaceAll(Matcher.quoteReplacement(""))).replaceAll(" ");
    }

    private Double getSize(String... sizeAndPrice) {
        return Double.valueOf(sizeAndPrice[0]);
    }

    private Double getPrice(String... sizeAndPrice) {
        return Double.valueOf(EURO.matcher(sizeAndPrice[1]).replaceAll(Matcher.quoteReplacement("")));
    }

}
