package com.company.service.impl;

import com.company.domain.Role;
import com.company.repository.RoleRepo;
import com.company.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepo roleRepo;

    public Role findByRoleName(String roleName){
        return roleRepo.findByRoleIgnoreCase(roleName);
    }
}
