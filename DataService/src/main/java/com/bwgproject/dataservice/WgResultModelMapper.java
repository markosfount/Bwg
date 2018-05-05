package com.bwgproject.dataservice;

import com.bwgproject.dataservice.model.WgResultEntity;
import com.bwgproject.model.WgResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WgResultModelMapper {

    public List<WgResultEntity> map(List<WgResult> wgResults) {
        return wgResults.stream()
                .map(this::mapResult)
                .collect(Collectors.toList());
    }

    private WgResultEntity mapResult(WgResult wgResult) {
        return WgResultEntity.builder()
                .availableFrom(wgResult.getAvailableFrom())
                .availableTo(wgResult.getAvailableTo())
                .dateOfPosting(wgResult.getDateOfPosting())
                .isLongTerm(wgResult.getIsLongTerm())
                .price(wgResult.getPrice())
                .size(wgResult.getSize())
                .location(wgResult.getLocation())
                .flatmates(wgResult.getFlatmates())
                .women(wgResult.getWomen())
                .men(wgResult.getMen())
                .text(wgResult.getText())
                .description(wgResult.getDescription())
                .extId(wgResult.getExtId())
                .build();
    }
}
