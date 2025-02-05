package com.employee.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class NotificationDto {
    private List<String> recipients;
    private String content;
    private String subject;
}
