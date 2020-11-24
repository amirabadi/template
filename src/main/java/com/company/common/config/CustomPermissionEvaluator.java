package com.company.common.config;

import com.company.domain.Role;
import com.company.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("customPermissionEvaluator")
public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Autowired
    private RoleService roleService;
    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }
        final String targetType = targetDomainObject.getClass().getSimpleName();
        return hasPrivilege(auth, targetType, permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, targetType.toUpperCase(), permission.toString().toUpperCase());
    }

    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {

        /*if (targetType.equals("UserRoleController".toUpperCase())) {
            for (final GrantedAuthority grantedAuth : auth.getAuthorities()) {
                if (grantedAuth.getAuthority().contains(tempPermission)) {
                    return true;
                }
            }
        }*/
        boolean hasPermission=false;
        for (final GrantedAuthority grantedAuth : auth.getAuthorities()) {
            Role  role=roleService.findByRoleName(grantedAuth.toString());

            if (role.getPermissions().stream().anyMatch(x-> x.getNamePermission().equalsIgnoreCase(permission)&&
                x.getObjectPermission().equalsIgnoreCase(targetType))) {
                hasPermission= true;
            }
        }
        return hasPermission;
    }
}
