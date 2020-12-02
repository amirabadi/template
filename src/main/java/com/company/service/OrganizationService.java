package com.company.service;

import com.company.domain.Organization;
import com.company.dto.FilterMeta;
import com.company.dto.OrganizationDTO;
import com.company.dto.SortMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrganizationService {
    Page<OrganizationDTO> getOrganizationPageable(Integer pageNumber, Integer rowsIntoPage, List<SortMeta> sortMeta, List<FilterMeta> filterMeta);

    Page<OrganizationDTO> getOrganizationPageable(Integer pageNumber, Integer rowsIntoPage, Long idParent, List<SortMeta> sortMeta, List<FilterMeta> filterMeta);

    boolean saveOrganization(OrganizationDTO baseInfoDTO);

    boolean editOrganization(OrganizationDTO baseInfoDTO);

    boolean deleteOrganization(OrganizationDTO baseInfoDTO);
    //List<DropdownDTO> dropdownBaseInformation(Long idParent);

    OrganizationDTO getOrganization(Long id);
    List<OrganizationDTO> getChildOrganizationList(Long idParent);
}
