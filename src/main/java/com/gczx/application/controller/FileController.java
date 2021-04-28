package com.gczx.application.controller;

import com.gczx.application.common.JsonResult;
import com.gczx.application.common.exception.BaseBusinessException;
import com.gczx.application.entity.AttachmentEntity;
import com.gczx.application.service.IAttachmentService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author leifeijin
 */
@Slf4j
@Api(tags = "文件上传控制器")
@ApiSort(3)
@RestController
public class FileController {
    @Resource
    private IAttachmentService attachmentService;
    @Value("${file.path}")
    private String filePath;

    /**
     * 上传文件简易模板
     * @param file 上传文件
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", paramType = "header", dataType = "String", required = true),
            @ApiImplicitParam(name = "file", paramType = "form", dataType = "__file", value = "上传文件名", required = true)
    })
    @PostMapping("/upload")
    public JsonResult<Object> upload(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new BaseBusinessException("上传文件为空，请选择文件");
            }
            String fileName = file.getOriginalFilename();
            File dest = new File(filePath + System.currentTimeMillis() + "_" + fileName);
            if (!dest.getParentFile().exists()) {
                boolean isMkdir = dest.getParentFile().mkdir();
                if (isMkdir) {
                    file.transferTo(dest);
                    attachmentService.add(fileName, dest.getPath());
                    return JsonResult.success("上传成功");
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return JsonResult.error("上传失败");
    }

    /**
     * 文件下载简易模板(不能返回json数据类型)
     * @param response 响应信息
     * @param id       需要下载的文件id,对应库表t_attachment中的id,实际业务可根据业务规定来传参
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", paramType = "header", dataType = "String", required = true),
            @ApiImplicitParam(name = "id", value = "下载文件id", required = true)
    })
    @GetMapping(value = "/download")
    public String download(HttpServletResponse response, Integer id) {
        BufferedInputStream bis = null;
        // 待下载文件名
        AttachmentEntity attachmentEntity = attachmentService.getById(id);
        try (
                OutputStream outputStream = response.getOutputStream();
        ) {
            // 这个路径为待下载文件的路径
            bis = new BufferedInputStream(new FileInputStream(new File(attachmentEntity.getPath())));
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(attachmentEntity.getName(), "UTF-8"));
            byte[] buff = new byte[1024];
            int read = bis.read(buff);
            // 通过while循环写入到指定了的文件夹中
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return "下载成功";
    }
}
