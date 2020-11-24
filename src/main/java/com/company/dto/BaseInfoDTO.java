package com.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseInfoDTO {
    private Long id;
    private String title;
    private String parentTitle;
    private Long parentId;
}
