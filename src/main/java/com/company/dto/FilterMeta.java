package com.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterMeta {
    private String filedName;
    private Object filedValue;
    private String operator;
}
