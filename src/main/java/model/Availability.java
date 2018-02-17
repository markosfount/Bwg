package model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;

@Builder
@Getter
public class Availability {
    private Date availableFrom;
    private Date availableTo;

    public boolean isLongTerm() {
        return Objects.nonNull(availableTo);
    }

}
