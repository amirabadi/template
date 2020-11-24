package com.company.service;

import com.company.domain.Role;

public interface RoleService {
    Role findByRoleName(String roleName);
}
