package com.bwgproject.parser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    public static final Pattern EURO = Pattern.compile("€", Pattern.LITERAL);
    public static final Pattern NAME_TRAIL = Pattern.compile("\\.\\d+.*");
    public static final Pattern DASH = Pattern.compile("-", Pattern.LITERAL);
    public static final Pattern WOMEN_PAT = Pattern.compile("\\dw");
    public static final Pattern MEN_PAT = Pattern.compile("\\dm");
    public static final Pattern DATE_SPLIT = Pattern.compile(" Verfügbar: ab ");
    public static final Pattern DATE_SPLIT_SHORT = Pattern.compile(" Verfügbar: ");
    public static final Pattern INTERVAL_SPLIT = Pattern.compile(" - ");
    public static final Pattern LOCATION_PAT = Pattern.compile(" [a-zA-Z]+,");
    public static final String ID = "id";
    public static final String SIZE_PRICE_WR = ".detail-size-price-wrapper";
    public static final String DETAIL_VIEW = ".detailansicht";
    public static final String TOTAL_NO = "total";
    public static final String WOMEN_NO = "women";
    public static final String MEN_NO = "men";
    public static final Pattern COMMA_PAT = Pattern.compile(",");
    public static final Pattern MINUTES = Pattern.compile("Minuten");
    public static final Pattern SIZE_PRICE_SPLIT = Pattern.compile("m² - ");
    public static final Pattern ER = Pattern.compile("er");
    public static final Pattern W = Pattern.compile("w");
    public static final Pattern LIST_DETAILS = Pattern.compile("liste-details-ad-");
    public static final Pattern M = Pattern.compile("m");

}
