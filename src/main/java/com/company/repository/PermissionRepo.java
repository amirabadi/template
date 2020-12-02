package com.company.repository;

import com.company.domain.BaseInformation;
import com.company.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepo extends JpaRepository<Permission,Long> , JpaSpecificationExecutor<BaseInformation> {
}
