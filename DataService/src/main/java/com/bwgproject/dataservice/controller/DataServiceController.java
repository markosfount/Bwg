package com.bwgproject.dataservice.controller;

import com.bwgproject.dataservice.WgResultModelMapper;
import com.bwgproject.dataservice.WgResultRepository;
import com.bwgproject.dataservice.model.WgResultEntity;
import com.bwgproject.model.WgResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class DataServiceController {

    @Autowired
    private WgResultRepository wgResultRepository;

    @Autowired
    private WgResultModelMapper wgResultModelMapper;

    @PostMapping(path = "/addRecords", consumes = "application/json;charset=UTF-8"  )
    public List<WgResultEntity> insertData(@RequestBody WgResult... wgResults) {
        System.out.println(wgResults);
        List<WgResultEntity> wgResultEntities = wgResultModelMapper.map(Arrays.asList(wgResults));

        return wgResultRepository.save(wgResultEntities);
    }


}
