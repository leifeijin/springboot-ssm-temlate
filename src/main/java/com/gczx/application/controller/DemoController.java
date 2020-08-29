package com.gczx.application.controller;

import com.gczx.application.common.JsonResult;
import com.gczx.application.controller.dto.DemoAddDto;
import com.gczx.application.controller.dto.DemoGetDto;
import com.gczx.application.controller.dto.DemoUpdateDto;
import com.gczx.application.service.IDemoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/demo")
public class DemoController {
    @Resource
    private IDemoService demoService;

    @GetMapping("/get")
    public JsonResult getDemo(DemoGetDto demoGetDto) {
        return JsonResult.success(demoService.getDemo(
                demoGetDto.offset,
                demoGetDto.pageSize,
                demoGetDto.getName(),
                demoGetDto.getSerial()));
    }

    @PostMapping("/post")
    public JsonResult addDemo(DemoAddDto demoAddDto) {
        return JsonResult.success(demoService.addDemo(
                demoAddDto.getName(),
                demoAddDto.getSerial(),
                demoAddDto.getOrder()));
    }

    @PutMapping("/put")
    public JsonResult updateDemo(DemoUpdateDto demoUpdateDto) {
        return JsonResult.success(demoService.updateDemo(
                demoUpdateDto.getId(),
                demoUpdateDto.getName(),
                demoUpdateDto.getSerial(),
                demoUpdateDto.getOrder()));
    }

    @DeleteMapping("/delete")
    public JsonResult deleteDemo(Long id) {
        return JsonResult.success(demoService.deleteDemo(id));
    }
}
