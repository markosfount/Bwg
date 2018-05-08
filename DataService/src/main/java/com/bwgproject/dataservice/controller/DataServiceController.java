package com.bwgproject.dataservice.controller;

import com.bwgproject.dataservice.WgResultModelMapper;
import com.bwgproject.dataservice.WgResultRepository;
import com.bwgproject.dataservice.model.WgResultEntity;
import com.bwgproject.model.WgResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/dataService")
@RequiredArgsConstructor
public class DataServiceController {

    private final WgResultRepository wgResultRepository;

    private final WgResultModelMapper wgResultModelMapper;

    @PostMapping(path = "/addRecords", consumes = "application/json;charset=UTF-8")
    public List<WgResultEntity> insertData(@RequestBody List<WgResult> wgResults) {
        List<WgResultEntity> wgResultEntities = wgResultModelMapper.map(wgResults);
        System.out.println(String.format("%s Persisting results", LocalDateTime.now()));

        return wgResultRepository.save(wgResultEntities);
    }

    @GetMapping(path = "/getMostRecent")
    public WgResultEntity getMostRecent() {
        List<WgResultEntity> wgResultEntities = wgResultRepository.findFirstByOrderByDateOfPostingDesc();
        System.out.println(String.format("%s Returned most recent element", LocalDateTime.now()));

        return wgResultEntities.get(0);
    }

}
