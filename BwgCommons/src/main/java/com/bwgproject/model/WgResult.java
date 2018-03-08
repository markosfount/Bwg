package com.bwgproject.model;

import lombok.Builder;

import java.io.Serializable;
import java.util.Date;

@Builder
public class WgResult implements Serializable {
    private String name;
    private String description;
    private String text;
    private Date dateOfPosting;
    private Date availableFrom;
    private Date availableTo;
    private Boolean isLongTerm;
    private Long extId;
    private double price;
    private double size;
    private Integer flatmates;
    private Integer women;
    private Integer men;
    private String location;
}
