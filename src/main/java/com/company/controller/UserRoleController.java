package com.company.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRoleController {
 /*   @Autowired
    RoleService roleService;*/

  /*  @PreAuthorize("hasPermission(this,'view')")
    @PostMapping(produces=MediaType.APPLICATION_JSON_VALUE)*/
    @RequestMapping("/UserRoleSelectPanel")
    public Object userRoleSelectPanel(@RequestParam(name = "Page") Integer page, @RequestParam(name="PageSize") Integer pageSize,
                                                    @RequestParam(name="Filter") String filter, @RequestParam(name="Sort") String sort) {
        Object principal = SecurityContextHolder.getContext().getAuthentication();
        return null;
    }
}
