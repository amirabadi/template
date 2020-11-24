package com.company.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class DataTableDTO implements Serializable {
    private Integer pageNumber;
    private Integer rowsIntoPage;
    private Long idParent;
    private List<SortMeta> sortMeta;
    private List<FilterMeta> filterMeta;
}
