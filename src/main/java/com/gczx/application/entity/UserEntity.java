package com.gczx.application.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user")
public class UserEntity extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户登录名
     */
    @TableField
    private String name;
    /**
     * 用户登录显示名
     */
    @TableField(value = "display_name")
    private String displayName;
    /**
     * 用户密码
     */
    @TableField
    private String password;
}
