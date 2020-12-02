package com.company.repository;

import com.company.domain.BaseInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;




@Repository
public interface BaseInformationRepo extends JpaRepository<BaseInformation, Long> , JpaSpecificationExecutor<BaseInformation> {

    BaseInformation getById(Long id);

    List<BaseInformation> getByParentIdAndEventNotIgnoreCase(Long id, String event);
    // @Query("select b from BaseInformation b ")
//    Page<BaseInformation> findAll(Pageable pageable);
}
