import model.Availability;
import model.WgResult;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ResultsMapper {

    private static final Pattern EURO = Pattern.compile("€", Pattern.LITERAL);
    private static final Pattern NAME_TRAIL = Pattern.compile("\\.\\d+.*");
    private static final Pattern DASH = Pattern.compile("-", Pattern.LITERAL);
    private static final Pattern WOMEN_PAT = Pattern.compile("\\dw");
    private static final Pattern MEN_PAT = Pattern.compile("\\dm");
    private static final Pattern DATE_SPLIT = Pattern.compile(" Verfügbar: ab ");
    private static final Pattern DATE_SPLIT_SHORT = Pattern.compile(" Verfügbar: ");
    private static final Pattern INTERVAL_SPLIT = Pattern.compile(" - ");
    private static final String LIST_DETAILS = "liste-details-ad-";
    private static final String SIZE_PRICE_SPLIT = "m² - ";
    private static final String ID = "id";
    private static final String SIZE_PRICE_WR = ".detail-size-price-wrapper";
    private static final String DETAIL_VIEW = ".detailansicht";
    private static final String ER = "er";
    private static final String TOTAL_NO = "total";
    private static final String WOMEN_NO = "women";
    private static final String MEN_NO = "men";

    public List<WgResult> mapResults(List<Element> elements) {
        // FIXME return directly
        List<WgResult> wgResults = elements.stream()
                .map(this::mapResult)
                .collect(Collectors.toList());

        return wgResults;
    }

    private WgResult mapResult(Element element) {

        Element details = getDetails(element);
        String[] sizeAndPrice = getSizePrice(details);
        Map<String, Integer> flatMateInfo = getFlatmateInfo(element);

        String description = element.select(".headline").select(".detailansicht").first().text();

        Pair<String, Availability> availabilityPair = parseDatesAvailable(element);

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
                .build();

        return wgResult;
    }

    private Pair<String, Availability> parseDatesAvailable(Element element) {
        String textToSplit = element.select("p").not(".list-details-image-wrapper").text();
        boolean isLongTerm = DATE_SPLIT.matcher(textToSplit).find();
        String[] textParts = isLongTerm ? DATE_SPLIT.split(textToSplit) : DATE_SPLIT_SHORT.split(textToSplit);
        String text = textParts[0];
        String[] interval = INTERVAL_SPLIT.split(textParts[1]);

        Availability availability = Availability.builder()
                .availableFrom(isLongTerm ? parseDate(textParts[1]) : parseDate(interval[0]))
                .availableTo(isLongTerm ? null : parseDate(interval[1]))
                .build();

        return Pair.of(text, availability);
    }

    private Long getExtId(Element element) {
        return Long.valueOf(element.attr(ID).split(LIST_DETAILS)[1]);
    }

    private Element getDetails(Element element) {
        return element.select(SIZE_PRICE_WR).select(DETAIL_VIEW).first();
    }

    private String getName(Element element) {
        return DASH.matcher(NAME_TRAIL.matcher(element.attr("href")).replaceAll(Matcher.quoteReplacement(""))).replaceAll(" ");
    }

    private String[] getSizePrice(Element element) {
        return element.text().split(SIZE_PRICE_SPLIT);
    }

    private Double getPrice(String... sizeAndPrice) {
        return Double.valueOf(EURO.matcher(sizeAndPrice[1]).replaceAll(Matcher.quoteReplacement("")));
    }

    private Double getSize(String... sizeAndPrice) {
        return Double.valueOf(sizeAndPrice[0]);
    }

    private Map<String, Integer> getFlatmateInfo(Element element) {
        String flatMateInfo = element.select("h3").select("span").attr("title");
        Map<String, Integer> flatMates = new HashMap<>();
        flatMates.put(TOTAL_NO, Integer.valueOf(flatMateInfo.split(ER)[0]));
        Matcher matcher = WOMEN_PAT.matcher(flatMateInfo);
        if (matcher.find()) {
            flatMates.put(WOMEN_NO, Integer.valueOf(matcher.group(0).replaceAll("w", "")));
        }
        matcher = MEN_PAT.matcher(flatMateInfo);
        if (matcher.find()) {
            flatMates.put(MEN_NO, Integer.valueOf(matcher.group(0).replaceAll("m", "")));
        }

        return flatMates;
    }

    private Date parseDate(String date) {
        String[] dateNums = Pattern.compile(".", Pattern.LITERAL).split(date);
        // FIXME replace deprecated date format
        return new Date(Integer.valueOf(dateNums[2]) + 100, Integer.valueOf(dateNums[1]) -1, Integer.valueOf(dateNums[0]));
    }

}
