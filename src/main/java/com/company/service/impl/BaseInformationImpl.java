package com.company.service.impl;

import com.company.common.utils.CalendarUtil;
import com.company.common.utils.enums.TransactionEvent;
import com.company.common.utils.security.SecurityUtils;
import com.company.domain.BaseInformation;
import com.company.domain.User;
import com.company.dto.BaseInfoDTO;
import com.company.dto.DropdownDTO;
import com.company.dto.FilterMeta;
import com.company.dto.SortMeta;
import com.company.map.BaseInformationMapper;
import com.company.repository.BaseInformationRepo;
import com.company.repository.UserRepo;
import com.company.service.BaseInformationService;
import com.company.service.PageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BaseInformationImpl extends PageEntity implements BaseInformationService {

    @Autowired
    private BaseInformationRepo baseInformationRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BaseInformationMapper baseInformationMapper;
//    private final BaseInformationMapper baseInformationMapper = Mappers.getMapper(BaseInformationMapper.class);

    @Override
    public Page<BaseInfoDTO> getBaseInformationPageable(Integer page, Integer pagesize, List<SortMeta> sortMetas, List<FilterMeta> filterMetas) {
        FilterMeta filterMeta = new FilterMeta();
        filterMeta.setFiledName("parent");
        filterMeta.setFiledValue(null);
        filterMeta.setOperator("eq");
        filterMetas.add(filterMeta);
        filterMetas.add(deleteEvent());
        Pageable pageRequest = createPageRequest(page, pagesize, generateSort(sortMetas));
        Specification<BaseInformation> baseInformationSpecification = null;
        Page<BaseInformation> baseInformationPage = null;
        if (filterMetas.size() > 0) {
            baseInformationSpecification = generateSearch(filterMetas);
            baseInformationPage = baseInformationRepo.findAll(baseInformationSpecification, pageRequest);
        } else {
            baseInformationPage = baseInformationRepo.findAll(pageRequest);
        }
        Page<BaseInfoDTO> dtoPage = baseInformationPage.map(baseInformation -> {
            //BaseInfoDTO baseInfoDTO = new BaseInfoDTO();
            BaseInfoDTO baseInfoDTO = baseInformationMapper.baseInformationToBaseInfoDto(baseInformation);
           /* baseInfoDTO.setId(baseInformation.getId());
            baseInfoDTO.setTitle(baseInformation.getTitle());
            baseInfoDTO.setParentTitle(baseInformation.getParent() != null ? baseInformation.getParent().getTitle() : null);
            baseInfoDTO.setParentId(baseInformation.getParent() != null ? baseInformation.getParent().getId() : null);*/
            return baseInfoDTO;
        });
        return dtoPage;
    }

    @Override
    public Page<BaseInfoDTO> getBaseInformationPageable(Integer page, Integer pagesize, Long idParent, List<SortMeta> sortMetas, List<FilterMeta> filterMetas) {
        FilterMeta filterMeta = new FilterMeta();
        filterMeta.setFiledName("parent");
        BaseInformation parentBase = baseInformationRepo.findById(idParent).get();
        filterMeta.setFiledValue(parentBase);
        filterMeta.setOperator("eq");
        filterMetas.add(filterMeta);
        filterMetas.add(deleteEvent());
        Pageable pageRequest = createPageRequest(page, pagesize, generateSort(sortMetas));
        Specification<BaseInformation> baseInformationSpecification = null;
        Page<BaseInformation> baseInformationPage = null;
        if (filterMetas.size() > 0) {
            baseInformationSpecification = generateSearch(filterMetas);
            baseInformationPage = baseInformationRepo.findAll(baseInformationSpecification, pageRequest);
        } else {
            baseInformationPage = baseInformationRepo.findAll(pageRequest);
        }
        Page<BaseInfoDTO> dtoPage = baseInformationPage.map(baseInformation -> {
            BaseInfoDTO baseInfoDTO = baseInformationMapper.baseInformationToBaseInfoDto(baseInformation);
            /*BaseInfoDTO baseInfoDTO = new BaseInfoDTO();
            baseInfoDTO.setId(baseInformation.getId());
            baseInfoDTO.setTitle(baseInformation.getTitle());
            baseInfoDTO.setParentTitle(baseInformation.getParent() != null ? baseInformation.getParent().getTitle() : null);
            baseInfoDTO.setParentId(baseInformation.getParent() != null ? baseInformation.getParent().getId() : null);*/
            return baseInfoDTO;
        });
        return dtoPage;
    }

    @Override
    public boolean saveBaseInformation(BaseInfoDTO baseInfoDTO) {
        BaseInformation baseInformation = new BaseInformation();
        User user = userRepo.findByUsername(SecurityUtils.getUsername());
        baseInformation.setRegisterUserId(user.getId());
        baseInformation.setRegisterDate(CalendarUtil.getDate(new Date(), new Locale("fa")));
        baseInformation.setEvent(TransactionEvent.ADD.toString());
        baseInformation.setTitle(baseInfoDTO.getTitle());
        if (baseInfoDTO.getParentId() != null) {
            baseInformation.setParent(baseInformationRepo.findById(Long.valueOf(baseInfoDTO.getParentId())).orElse(null));
            baseInformation.setFullName(baseInformation.getParent().getFullName() + ">" + baseInformation.getTitle());
        } else {
            baseInformation.setFullName(baseInformation.getTitle());
        }
        baseInformationRepo.saveAndFlush(baseInformation);
        return true;
    }

    @Override
    public boolean editBaseInformation(BaseInfoDTO baseInfoDTO) {
        BaseInformation baseInformation = baseInformationRepo.findById(Long.valueOf(baseInfoDTO.getId())).orElse(null);
        User user = userRepo.findByUsername(SecurityUtils.getUsername());
        baseInformation.setUpdateUserId(user.getId());
        baseInformation.setUpdateDate(CalendarUtil.getDate(new Date(), new Locale("fa")));
        baseInformation.setEvent(TransactionEvent.EDIT.toString());
        baseInformation.setTitle(baseInfoDTO.getTitle());
        if (baseInformation.getParent() != null) {
            baseInformation.setFullName(baseInformation.getParent().getFullName() + ">" + baseInformation.getTitle());
        } else {
            baseInformation.setFullName(baseInformation.getTitle());
        }
        baseInformationRepo.saveAndFlush(baseInformation);
        return true;
    }

    @Override
    public boolean deleteBaseInformation(BaseInfoDTO baseInfoDTO) {
        BaseInformation baseInformation = baseInformationRepo.findById(Long.valueOf(baseInfoDTO.getId())).orElse(null);
        User user = userRepo.findByUsername(SecurityUtils.getUsername());
        baseInformation.setUpdateUserId(user.getId());
        baseInformation.setUpdateDate(CalendarUtil.getDate(new Date(), new Locale("fa")));
        baseInformation.setEvent(TransactionEvent.DELETE.toString());
        baseInformationRepo.saveAndFlush(baseInformation);
        return true;
    }

    @Override
    public List<DropdownDTO> dropdownBaseInformation(Long idParent) {
        List<BaseInformation> baseInformations = baseInformationRepo.getByParentIdAndEventNotIgnoreCase(idParent, TransactionEvent.DELETE.toString());
        return baseInformations.stream().map(x -> new DropdownDTO(x.getTitle(), String.valueOf(x.getId()))).collect(Collectors.toList());
    }

    @Override
    public BaseInfoDTO getBaseInformation(Long id) {
        Optional<BaseInformation> baseInformation = baseInformationRepo.findById(id);
        BaseInfoDTO baseInfoDTO = null;
        if (baseInformation != null) {
            baseInfoDTO = new BaseInfoDTO();
            baseInfoDTO.setId(baseInformation.get().getId());
            baseInfoDTO.setTitle(baseInformation.get().getTitle());
            baseInfoDTO.setParentId(baseInformation.get().getParent().getId());
        }
        return baseInfoDTO;
    }

}
