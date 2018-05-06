package com.bwgproject.dataservice;

import com.bwgproject.dataservice.model.WgResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WgResultRepository extends JpaRepository<WgResultEntity, Long> {

    List<WgResultEntity> findFirstByOrderByDateOfPostingDesc();

}
