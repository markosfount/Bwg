package com.bwgproject.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
public class WgResult implements Serializable {
    private String description;
    private String text;
    private LocalDateTime dateOfPosting;
    private LocalDateTime availableFrom;
    private LocalDateTime availableTo;
    private Boolean isLongTerm;
    private Long extId;
    private double price;
    private double size;
    private Integer flatmates;
    private Integer women;
    private Integer men;
    private String location;
}
