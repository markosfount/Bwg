import model.WgResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public class ResponseParser {

    private ResultsMapper resultsMapper;

    public ResponseParser() {
        this.resultsMapper = new ResultsMapper();
    }

    public List<WgResult> parseResponse(String response) {
        Document document = Jsoup.parse(response);
        List<Element> elements = document.select("div[id~=liste-details-ad-\\d+]");

        return resultsMapper.mapResults(elements);
    }

}
