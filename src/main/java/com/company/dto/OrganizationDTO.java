package com.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationDTO {
    private Long id;

    private String typeTitle;

    private Long typeId;

    private String name;

    private String ParentTitle;

    private Long parentId;
}
