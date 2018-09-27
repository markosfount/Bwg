package com.bwgproject.parser;

import org.junit.jupiter.api.Test;

class BwgScraperTest {

    private BwgScraper bwgScraper = new BwgScraper();

    @Test
    public void shouldFormatUrlWithPageNumber() {
        String formattedUrl = bwgScraper.formatUrl(0);
    }
}