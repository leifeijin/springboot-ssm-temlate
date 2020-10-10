package com.gczx.application.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: leifeijin
 * @Date: 2020/9/24
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("idpm_role")
public class RoleEntity extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 角色名
     */
    @TableField
    private String name;
    /**
     * 角色显示名
     */
    @TableField(value = "display_name")
    private String displayName;
}
