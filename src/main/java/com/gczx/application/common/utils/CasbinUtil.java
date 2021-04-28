package com.gczx.application.common.utils;

import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: leifeijin
 * @Date: 2020/9/24
 * @Description: casbin工具类
 */
@Component
public class CasbinUtil {

    private static Enforcer enforcer;

    @Autowired
    public static void setEnforcer(Enforcer enforcer) {
        CasbinUtil.enforcer = enforcer;
    }

    /**
     * 给用户添加添加角色组
     * @param sub 请求用户
     * @param obj 路由
     * @param act 动作
     * @return
     */
    public static boolean addGroupingPolicy(String sub, String obj, String act) {
        boolean addPolicy = enforcer.addGroupingPolicy(sub, obj, act);
        enforcer.savePolicy();
        return addPolicy;
    }

    /**
     * 删除用户权限
     * @param userName 用户名
     * @return
     */
    public static boolean deleteUser(String userName) {
        boolean removePolicy = enforcer.deleteUser(userName);
        enforcer.savePolicy();
        return removePolicy;
    }

    /**
     * 根据用户名查询用户角色id
     * @param userName 用户名
     * @param domain   域
     * @return 角色名列表
     */
    public static List<String> listRoleByUserNameInDomain(String userName, String domain) {
        List<String> roleNames = enforcer.getRolesForUserInDomain(userName, domain);
        List<String> roles = new ArrayList<>();
        if (null != roleNames && !roleNames.isEmpty()) {
            roleNames.forEach(item -> roles.add(item.replace("role:", "")));
        }
        return roles;
    }
}
