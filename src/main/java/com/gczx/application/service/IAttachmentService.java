package com.gczx.application.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gczx.application.entity.AttachmentEntity;

public interface IAttachmentService extends IService<AttachmentEntity> {
    /**
     * 添加附件
     * @param fileName
     * @param path
     */
    void add(String fileName, String path);
}
