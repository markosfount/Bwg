//@Log4j2
public class BwgParser {

    private BwgScraper scraper;
    private ResponseParser parser;

    public BwgParser() {
        this.scraper = new BwgScraper();
    }

    public void parse() {
        String response = scraper.getResponse();
    }


}
