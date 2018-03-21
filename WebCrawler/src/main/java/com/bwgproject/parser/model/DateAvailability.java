package com.bwgproject.parser.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Getter
public class DateAvailability {
    private LocalDateTime availableFrom;
    private LocalDateTime availableTo;

    public boolean isLongTerm() {
        return Objects.nonNull(availableTo);
    }

}
