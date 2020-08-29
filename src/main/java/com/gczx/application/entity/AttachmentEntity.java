package com.gczx.application.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_attachment")
public class AttachmentEntity extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 文件名
     */
    @TableField
    private String name;
    /**
     * 文件保存路劲
     */
    @TableField
    private String path;
}
