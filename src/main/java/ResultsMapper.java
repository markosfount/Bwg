import model.WgResult;
import org.jsoup.nodes.Element;

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

        String properName = element.select(".headline").select(".detailansicht").first().text();

        //TODO add + parse date ( .tausch_rent_by_day_hide )
        String textAndDate = element.select("p").not(".list-details-image-wrapper").text();
        WgResult wgResult = WgResult.builder()
                .extId(getExtId(element))
                .name(getName(details))
                .size(getSize(sizeAndPrice))
                .price(getPrice(sizeAndPrice))
                .flatmates(flatMateInfo.get(TOTAL_NO))
                .women(flatMateInfo.get(WOMEN_NO))
                .men(flatMateInfo.get(MEN_NO))
                .build();

        return wgResult;
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

}
