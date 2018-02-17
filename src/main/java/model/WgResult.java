package model;

import lombok.Builder;

import java.util.Date;

@Builder
public class WgResult {
    private String name;
    private String description;
    private String text;
    private Date dateOfPosting;
    private Date availableFrom;
    private Date availableTo;
    private boolean isLongTerm;
    private Long extId;
    private double price;
    private double size;
    private Integer flatmates;
    private Integer women;
    private Integer men;

}
