package com.gczx.application.controller.dto;

import com.gczx.application.common.PaginationDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DemoGetDto extends PaginationDto {
    private String name;
    private int serial;
}
