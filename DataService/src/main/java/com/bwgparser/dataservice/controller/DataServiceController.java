package com.bwgparser.dataservice.controller;

import com.bwgproject.parser.model.WgResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataServiceController {

    @PostMapping(path = "/data", consumes = "application/json")
    public String insertData(@RequestBody WgResult wgResult) {
        System.out.println(wgResult);
        return "data inserted \n";
    }


}
