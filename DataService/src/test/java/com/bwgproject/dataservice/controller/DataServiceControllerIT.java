package com.bwgproject.dataservice.controller;

import com.bwgproject.dataservice.WgResultRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class DataServiceControllerIT {

    @Autowired
    private WgResultRepository wgResultRepository;

    @Test
    public void shouldFindResultsInDb() {
//        Sort sort = new Sort(Order.asc("dateOfPosting"));
//        wgResultRepository.findAll()
//        List<WgResultEntity> wgResultEntities = wgResultRepository.findAllOrderByDateOfPostingAsc();
//        assertThat(wgResultEntities, notNullValue());

    }

}