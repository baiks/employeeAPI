package com.employee.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ListDataDto {
    private Long count;
    private Object results;
}
