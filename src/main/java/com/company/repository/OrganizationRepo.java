package com.company.repository;

import com.qorb.domain.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Long> {
    @Query("select r from Organization r ")
    Page<Organization> getAll(Pageable pageable);

    //   Page<Person> getByFilter(Pageable pageRequest, GridFilter filter);
}

