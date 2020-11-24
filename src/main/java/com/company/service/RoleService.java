package com.company.service;

import com.qorb.domain.Role;

public interface RoleService {
    Role findByRoleName(String roleName);
}
