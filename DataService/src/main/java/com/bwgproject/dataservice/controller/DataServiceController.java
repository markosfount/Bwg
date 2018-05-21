package com.bwgproject.dataservice.controller;

import com.bwgproject.dataservice.WgResultModelMapper;
import com.bwgproject.dataservice.WgResultRepository;
import com.bwgproject.dataservice.model.NoElementsFoundException;
import com.bwgproject.dataservice.model.WgResultEntity;
import com.bwgproject.model.WgResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dataService")
@RequiredArgsConstructor
public class DataServiceController {

    private final WgResultRepository wgResultRepository;

    private final WgResultModelMapper wgResultModelMapper;

    @PostMapping(path = "/addRecords", consumes = "application/json;charset=UTF-8")
    public List<WgResultEntity> insertData(@RequestBody List<WgResult> wgResults) {
        List<WgResultEntity> wgResultEntities = wgResultModelMapper.map(wgResults);

        List<WgResultEntity> storedEntities = wgResultRepository.save(wgResultEntities);
        log.info("Persisting {} results", storedEntities.size());

        return storedEntities;
    }

    @GetMapping(path = "/getMostRecent")
    public WgResultEntity getMostRecent() throws NoElementsFoundException {
        List<WgResultEntity> wgResultEntities = wgResultRepository.findFirstByOrderByDateOfPostingDesc();
        if (wgResultEntities.isEmpty()) {
            log.warn("No elements found in database");
            throw new NoElementsFoundException("No elements found");
        }

        log.info("Returned most recent element");
        return wgResultEntities.get(0);
    }

    @ExceptionHandler(NoElementsFoundException.class)
    public ResponseEntity<WgResultEntity> exceptionHanlder() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
