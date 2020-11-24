package com.company.repository;

import com.company.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
@Query("select r from Role r where upper(r.roleName)=:roleName ")
    Role findByRoleIgnoreCase(@Param("roleName") String roleName);
}
