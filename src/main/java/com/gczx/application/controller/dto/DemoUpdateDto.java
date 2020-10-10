package com.gczx.application.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DemoUpdateDto {
    private long id;
    private String name;
    private int serial;
    private int order;
}
