package com.gczx.application.common.interceptor;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gczx.application.common.JsonResult;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author leifeijin
 */
public class CasbinInterceptor implements HandlerInterceptor {
    @Resource
    private Enforcer enforcer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI().toLowerCase();
        if (!uri.contains("/login")) {
            String token = request.getHeader("token");
            DecodedJWT decode = JWT.decode(token);
            String name = decode.getAudience().get(0);
            String domain = "demo";
            String method = request.getMethod().toUpperCase();
            boolean enforce = enforcer.enforce(name, domain, uri, method);
            if (!enforce) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                JsonResult<Object> result = JsonResult.error(null, "没有操作权限", HttpStatus.FORBIDDEN.value());
                out.write(JSONUtil.toJsonStr(result));
                return false;
            }
        }
        return true;
    }
}
