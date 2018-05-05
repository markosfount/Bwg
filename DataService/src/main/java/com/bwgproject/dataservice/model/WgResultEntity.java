package com.bwgproject.dataservice.model;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Entity
public class WgResultEntity  implements Serializable {

    // TODO add toString
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private LocalDateTime dateOfPosting;
    private LocalDateTime availableFrom;
    private LocalDateTime availableTo;
    private Boolean isLongTerm;
    private double price;
    private double size;
    private String location;
    private Integer flatmates;
    private Integer women;
    private Integer men;
    private String text;
    private String description;
    private Long extId;
}
