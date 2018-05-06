package com.bwgproject.dataservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WgResultEntity  implements Serializable {

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
