package com.bwgproject.parser.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtils {

    public static List<Element> getElements() throws Exception {
        String response = new String(Files.readAllBytes(Paths.get(TestUtils.class.getClassLoader().getResource("response.html").toURI())));
        Document document = Jsoup.parse(response);
        return document.select("div[id~=liste-details-ad-\\d+]");

    }

    public static String getScrapedContent() throws Exception {
        String response = new String(Files.readAllBytes(Paths.get(TestUtils.class.getClassLoader().getResource("response.html").toURI())));
        return response;
    }

}
