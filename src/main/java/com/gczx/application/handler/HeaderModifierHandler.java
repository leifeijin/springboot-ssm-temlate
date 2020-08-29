package com.gczx.application.handler;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gczx.application.common.exception.ResponseCodeEnum;
import com.gczx.application.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@ControllerAdvice
public class HeaderModifierHandler implements ResponseBodyAdvice<Object> {
    @Value("${some.work.jwtKey}")
    private String jwtKey;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ServletServerHttpRequest ssReq = (ServletServerHttpRequest)request;
        ServletServerHttpResponse ssResp = (ServletServerHttpResponse)response;
        HttpServletRequest req = ssReq.getServletRequest();
        HttpServletResponse res = ssResp.getServletResponse();
        String uri = req.getRequestURI();
        if (res.getStatus() == ResponseCodeEnum.SUCCESS.getCode() && uri.contains("/login")) {
            JSON parse = JSONUtil.parse(body);
            UserEntity user = parse.getByPath("data", UserEntity.class);
            if (null != user) {
                String token = JWT.create()
                        .withAudience(user.getName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000))
                        .sign(Algorithm.HMAC256(jwtKey));
                res.setHeader("token", token);
            }
        }
        return body;
    }
}
