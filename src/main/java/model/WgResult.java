package model;

import lombok.Builder;

@Builder
public class WgResult {
    private String name;
    private Long extId;
    private double price;
    private double size;
    private Integer flatmates;
    private Integer women;
    private Integer men;

}
