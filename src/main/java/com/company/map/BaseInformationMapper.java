package com.company.map;

import com.company.domain.BaseInformation;
import com.company.dto.BaseInfoDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseInformationMapper {
    BaseInformationMapper INSTANCE = Mappers.getMapper(BaseInformationMapper.class);

    @Mapping(source = "baseInformation.parent.title", target = "parentTitle")
    @Mapping(source = "baseInformation.parent.id", target = "parentId")
    BaseInfoDTO baseInformationToBaseInfoDto(BaseInformation baseInformation);
    @InheritInverseConfiguration
    BaseInformation baseInfoDTOToBaseInformation(BaseInfoDTO baseInfoDTO);
}
