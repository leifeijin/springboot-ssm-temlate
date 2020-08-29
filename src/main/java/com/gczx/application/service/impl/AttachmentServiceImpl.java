package com.gczx.application.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gczx.application.entity.AttachmentEntity;
import com.gczx.application.service.IAttachmentService;
import com.gczx.application.service.dao.IAttachmentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AttachmentServiceImpl extends ServiceImpl<IAttachmentMapper, AttachmentEntity> implements IAttachmentService {
    @Resource
    private IAttachmentMapper attachmentMapper;

    @Override
    public void add(String fileName, String path) {
        AttachmentEntity attachmentEntity = new AttachmentEntity();
        attachmentEntity.setName(fileName);
        attachmentEntity.setPath(path);
        attachmentMapper.insert(attachmentEntity);
    }
}
