package com.gczx.application.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * casbin规则表
 * @author leifeijin
 */
@Data
@TableName("casbin_rule")
public class CasbinRuleEntity {
    @TableId(type = IdType.AUTO)
    public Long id;
    /**
     * 固定有以下几个值：
     * r: 'request_definition'-请求定义
     * p: 'policy_definition'-策略定义
     * g: 'role_definition'-角色定义
     * e: 'policy_effect'-策略影响
     * m: 'matchers'-匹配器
     */
    @TableField(value = "ptype")
    public String ptype;
    /**
     * 用户或用户组（部门）或角色或角色组的name值，角色或角色组的值时，带前缀【role:】区分
     */
    @TableField
    public String v0;
    /**
     * 域值或角色/角色组的值,角色或角色组的值时，带前缀【role:】区分
     */
    @TableField
    public String v1;
    /**
     * 域/组或权限资源的值
     */
    @TableField
    public String v2;
    /**
     * v2是域或组时，其是null；否则是拥有权限的动作
     */
    @TableField
    public String v3;
    /**
     * 预留字段
     */
    @TableField
    public String v4;
    /**
     * 预留字段
     */
    @TableField
    public String v5;
}
