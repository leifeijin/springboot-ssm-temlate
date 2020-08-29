package com.gczx.application.common.interceptor;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gczx.application.common.exception.BaseBusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Value("${some.work.jwtMethod}")
    private String jwtMethod;
    @Value("${some.work.jwtKey}")
    private String jwtKey;

    /**
     * 前置处理方法
     * @param request
     * @param response
     * @param object
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
        // 校验jwt中的信息
        String token = request.getHeader("token");
        if(!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)object;
        Method method = handlerMethod.getMethod();
        if(jwtMethod.equals(method.getName())) {
            return true;
        }
        if(token == null || token.length() <= 0)
            throw new BaseBusinessException("token为空");
        DecodedJWT decode = JWT.decode(token);
        int compare = DateUtil.compare(decode.getExpiresAt(), new Date());
        if (compare < 0) {
            throw new BaseBusinessException("token已过期");
        }
        try {
            String user = decode.getAudience().get(0);
            if (StringUtils.isBlank(user)) {
                throw new BaseBusinessException("错误的token");
            }
        } catch (JWTDecodeException e) {
            throw new BaseBusinessException("错误的token");
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtKey)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new BaseBusinessException("token为非法或已过期");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
