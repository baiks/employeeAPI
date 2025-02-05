package com.employee.management.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LimitSortDto {
    private int offset; //i.e 1
    private int limit; //i.e 10
    private String sortBy; //id
    private String sort; //DESC,ASC
}
