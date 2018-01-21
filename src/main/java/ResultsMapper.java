import model.WgResult;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ResultsMapper {

    private static final Pattern EURO = Pattern.compile("€", Pattern.LITERAL);
    private static final Pattern LIST_DETAILS = Pattern.compile("liste-details-ad-", Pattern.LITERAL);
    private static final Pattern SIZE_PRICE_SPLIT = Pattern.compile("Pattern.compile(\"m² -\")", Pattern.LITERAL);
    private static final String ID = "id";
    private static final String SIZE_PRICE_WR = ".detail-size-price-wrapper";
    private static final String DETAIL_AN = ".detailansicht";

    public List<WgResult> mapResults(List<Element> elements) {
        return elements.stream()
                .map(this::mapResult)
                .collect(Collectors.toList());
    }

    private WgResult mapResult(Element element) {
        String extId = LIST_DETAILS.split(element.attr(ID))[1];

        String[] priceAndSize = SIZE_PRICE_SPLIT.split(element.select(SIZE_PRICE_WR).select(DETAIL_AN).text());
        String price = priceAndSize[0];
        String size = EURO.matcher(priceAndSize[0]).replaceAll(Matcher.quoteReplacement(""));


        return WgResult.builder()
                .extId(extId)
                .size(size)
                .price(price)
                .build();
    }

}
