package com.gczx.application.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gczx.application.common.JsonResult;
import com.gczx.application.controller.dto.DemoAddDto;
import com.gczx.application.controller.dto.DemoGetDto;
import com.gczx.application.controller.dto.DemoUpdateDto;
import com.gczx.application.entity.DemoEntity;
import com.gczx.application.service.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author leifeijin
 */
@Api(tags = "demo控制器")
@RestController
@RequestMapping("/api/demo")
public class DemoController {
    @Resource
    private IDemoService demoService;

    @ApiImplicitParam(name = "token", paramType = "header", dataType = "String", required = true)
    @GetMapping("/get")
    public JsonResult<Page<DemoEntity>> getDemo(DemoGetDto demoGetDto) {
        return JsonResult.success(demoService.getDemo(
                demoGetDto.offset,
                demoGetDto.pageSize,
                demoGetDto.getName(),
                demoGetDto.getSerial()));
    }

    @ApiImplicitParam(name = "token", paramType = "header", dataType = "String", required = true)
    @PostMapping("/post")
    public JsonResult<Object> addDemo(DemoAddDto demoAddDto) {
        return JsonResult.success(demoService.addDemo(
                demoAddDto.getName(),
                demoAddDto.getSerial(),
                demoAddDto.getOrder()));
    }

    @ApiImplicitParam(name = "token", paramType = "header", dataType = "String", required = true)
    @PutMapping("/put")
    public JsonResult<Object> updateDemo(DemoUpdateDto demoUpdateDto) {
        return JsonResult.success(demoService.updateDemo(
                demoUpdateDto.getId(),
                demoUpdateDto.getName(),
                demoUpdateDto.getSerial(),
                demoUpdateDto.getOrder()));
    }

    @ApiImplicitParam(name = "token", paramType = "header", dataType = "String", required = true)
    @DeleteMapping("/delete")
    public JsonResult<Object> deleteDemo(Long id) {
        return JsonResult.success(demoService.deleteDemo(id));
    }
}
