package com.company.service;

import com.company.dto.BaseInfoDTO;
import com.company.dto.DropdownDTO;
import com.company.dto.FilterMeta;
import com.company.dto.SortMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BaseInformationService {
    Page<BaseInfoDTO> getBaseInformationPageable(Integer page, Integer pagesize, List<SortMeta> sortMetas, List<FilterMeta> filterMetas);

    Page<BaseInfoDTO> getBaseInformationPageable(Integer page, Integer pagesize, Long idParent, List<SortMeta> sortMetas, List<FilterMeta> filterMetas);


    boolean saveBaseInformation(BaseInfoDTO baseInfoDTO);

    boolean editBaseInformation(BaseInfoDTO baseInfoDTO);

    boolean deleteBaseInformation(BaseInfoDTO baseInfoDTO);
    List<DropdownDTO> dropdownBaseInformation(Long idParent);

    BaseInfoDTO getBaseInformation(Long id);
}
