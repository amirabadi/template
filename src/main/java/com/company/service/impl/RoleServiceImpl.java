package com.company.service.impl;

import com.qorb.domain.Role;
import com.qorb.repository.RoleRepo;
import com.qorb.service.RoleService;
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
