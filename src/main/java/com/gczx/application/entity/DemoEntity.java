package com.gczx.application.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("t_demo")
public class DemoEntity extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField
    private String name;

    @TableField
    private int serial;

    @TableField("`order`")    // mysql关键字用法
//    @TableField("\"order\"")    // 达梦数据库关键字用法
    private int order;
}
