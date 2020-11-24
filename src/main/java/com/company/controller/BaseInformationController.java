package com.company.controller;

import com.qorb.domain.BaseInformation;
import com.qorb.dto.BaseInfoDTO;
import com.qorb.dto.DataTableDTO;
import com.qorb.dto.DropdownDTO;
import com.qorb.service.BaseInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController

public class BaseInformationController {
    @Autowired
    private BaseInformationService baseInformationService;

    @Value("${baseinfo.typePermission}")
    private String typePermission;

    @PostMapping(value = "/getBaseInformationPage", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(this,'view')")
    public Page<BaseInfoDTO> baseInfoPage(@RequestBody DataTableDTO dataTableDTO) {
        if (dataTableDTO.getIdParent() == null || dataTableDTO.getIdParent().equals(0L))
            return baseInformationService.getBaseInformationPageable(dataTableDTO.getPageNumber(), dataTableDTO.getRowsIntoPage()
                    , dataTableDTO.getSortMeta(), dataTableDTO.getFilterMeta());
        else
            return baseInformationService.getBaseInformationPageable(dataTableDTO.getPageNumber(), dataTableDTO.getRowsIntoPage(),
                    dataTableDTO.getIdParent(), dataTableDTO.getSortMeta(), dataTableDTO.getFilterMeta());
    }

    @PostMapping(value = "/getBaseInformation", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(this,'view')")
    public BaseInfoDTO getInfoBase(@RequestBody Long id) {
        return baseInformationService.getBaseInformation(id);
    }

    @PostMapping(value = "/saveBaseInformation", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(this,'insert')")
    public boolean save(@RequestBody BaseInfoDTO baseInfoDTO) {
        return baseInformationService.saveBaseInformation(baseInfoDTO);
    }

    @PostMapping(value = "/editBaseInformation", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(this,'edit')")
    public boolean edit(@RequestBody BaseInfoDTO baseInfoDTO) {
        return baseInformationService.editBaseInformation(baseInfoDTO);
    }

    @PostMapping(value = "/deleteBaseInformation", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(this,'delete')")
    public boolean delete(@RequestBody BaseInfoDTO baseInfoDTO) {
        return baseInformationService.deleteBaseInformation(baseInfoDTO);
    }
   /* @PostMapping(value="/getBaseInformationChildPage",consumes= MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(this,'view')")
    public Page<BaseInfoDTO> baseInfoPageChild(@RequestBody DataTableDTO dataTableDTO) {
        return baseInformationService.getBaseInformationPageable(dataTableDTO.getPageNumber(),dataTableDTO.getRowsIntoPage(),
                dataTableDTO.getIdParent(),dataTableDTO.getSortMeta(),dataTableDTO.getFilterMeta());
    }*/

    @PostMapping(value = "/getDropDownBaseinfo", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(this,'view')")
    public List<DropdownDTO> baseDropDownInfoPage(@RequestBody Map<String, ?> input) {
        Long idParent = ((Number) input.get("idParent")).longValue();
        return baseInformationService.dropdownBaseInformation(idParent);
    }
}
