package com.bwgproject.dataservice.controller;

import com.bwgproject.model.WgResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataServiceController {

    @PostMapping(path = "/data", consumes = "application/json;charset=UTF-8"  )
    public String insertData(@RequestBody WgResult... wgResults) {
        System.out.println(wgResults);
        return "data inserted \n";
    }


}
