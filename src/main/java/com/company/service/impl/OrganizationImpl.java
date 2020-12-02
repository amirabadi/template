package com.company.service.impl;

import com.company.domain.BaseInformation;
import com.company.domain.Organization;
import com.company.dto.*;
import com.company.repository.OrganizationRepo;
import com.company.repository.UserRepo;
import com.company.service.BaseInformationService;
import com.company.service.OrganizationService;
import com.company.service.PageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationImpl extends PageEntity implements OrganizationService {
    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private BaseInformationService baseInformationService;
    @Autowired
    private UserRepo userRepo;
    @Value("${baseinfo.typeUnit}")
    private Long typeUnit;
    @Override
    public Page<OrganizationDTO> getOrganizationPageable(Integer page, Integer pagesize, List<SortMeta> sortMetas, List<FilterMeta> filterMetas) {
        FilterMeta filterMeta = new FilterMeta();
        filterMeta.setFiledName("parent");
        filterMeta.setFiledValue(null);
        filterMeta.setOperator("eq");
        filterMetas.add(filterMeta);
        filterMetas.add(deleteEvent());
        Pageable pageRequest = createPageRequest(page, pagesize, generateSort(sortMetas));
        Specification<Organization> organizationSpecification = null;
        Page<Organization> organizationPage = null;
        if (filterMetas.size() > 0) {
            organizationSpecification = generateSearch(filterMetas);
            organizationPage = organizationRepo.findAll(organizationSpecification, pageRequest);
        } else {
            organizationPage = organizationRepo.findAll(pageRequest);
        }
        List<DropdownDTO> dropdownBaseInformation=baseInformationService.dropdownBaseInformation(typeUnit);
        Page<OrganizationDTO> dtoPage = organizationPage.map(organization -> {
            OrganizationDTO organizationDTO = new OrganizationDTO();
            organizationDTO.setId(organization.getId());
            organizationDTO.setName(organization.getName());
            organizationDTO.setTypeTitle(dropdownBaseInformation.stream().filter(s->s.getValue().equalsIgnoreCase(organization.getType().toString())).findFirst().get().getText());
            organizationDTO.setTypeId(organization.getType());
            organizationDTO.setParentTitle(organization.getParent() != null ? organization.getParent().getName() : null);
            organizationDTO.setParentId(organization.getParent() != null ? organization.getParent().getId() : null);
            return organizationDTO;
        });
        return dtoPage;
    }

    @Override
    public Page<OrganizationDTO> getOrganizationPageable(Integer page, Integer pagesize, Long idParent, List<SortMeta> sortMetas, List<FilterMeta> filterMetas) {
        FilterMeta filterMeta = new FilterMeta();
        filterMeta.setFiledName("parent");
        Organization parentOrganization=organizationRepo.findById(idParent).get();
        filterMeta.setFiledValue(parentOrganization);
        filterMeta.setOperator("eq");
        filterMetas.add(filterMeta);
        filterMetas.add(deleteEvent());
        Pageable pageRequest = createPageRequest(page, pagesize, generateSort(sortMetas));
        Specification<Organization> organizationSpecification = null;
        Page<Organization> organizationPage = null;
        if (filterMetas.size() > 0) {
            organizationSpecification = generateSearch(filterMetas);
            organizationPage = organizationRepo.findAll(organizationSpecification, pageRequest);
        } else {
            organizationPage = organizationRepo.findAll(pageRequest);
        }
        List<DropdownDTO> dropdownBaseInformation=baseInformationService.dropdownBaseInformation(typeUnit);
        Page<OrganizationDTO> dtoPage = organizationPage.map(organization -> {
            OrganizationDTO organizationDTO = new OrganizationDTO();
            organizationDTO.setId(organization.getId());
            organizationDTO.setName(organization.getName());
            organizationDTO.setTypeTitle(dropdownBaseInformation.stream().filter(s->s.getValue().equalsIgnoreCase(organization.getType().toString())).findFirst().get().getText());
            organizationDTO.setTypeId(organization.getType());
            organizationDTO.setParentTitle(organization.getParent() != null ? organization.getParent().getName() : null);
            organizationDTO.setParentId(organization.getParent() != null ? organization.getParent().getId() : null);
            return organizationDTO;
        });
        return dtoPage;
    }

    @Override
    public boolean saveOrganization(OrganizationDTO baseInfoDTO) {
        return false;
    }

    @Override
    public boolean editOrganization(OrganizationDTO baseInfoDTO) {
        return false;
    }

    @Override
    public boolean deleteOrganization(OrganizationDTO baseInfoDTO) {
        return false;
    }

    @Override
    public OrganizationDTO getOrganization(Long id) {
        return null;
    }

    @Override
    public List<OrganizationDTO> getChildOrganizationList(Long idParent) {
        return null;
    }
}
