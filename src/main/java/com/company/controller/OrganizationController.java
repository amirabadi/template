package com.company.controller;

import com.company.dto.BaseInfoDTO;
import com.company.dto.DataTableDTO;
import com.company.service.BaseInformationService;
import com.company.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationController {
    @PostMapping(value = "/getInfoOrg", consumes = MediaType.APPLICATION_JSON_VALUE)

    public String baseInfoPage() {
        return "Hello Organization!!!!!!!!!!";
    }
}