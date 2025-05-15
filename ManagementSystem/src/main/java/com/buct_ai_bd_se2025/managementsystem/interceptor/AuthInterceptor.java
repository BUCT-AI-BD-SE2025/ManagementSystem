
package com.buct_ai_bd_se2025.managementsystem.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.buct_ai_bd_se2025.managementsystem.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        if (uri.startsWith("/swagger-ui") || uri.startsWith("/v3/api-docs")) {
            return true;
        }

        if ("/login".equals(uri)) {
            return true;
        }


        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            DecodedJWT jwt = JWTUtils.getDecodedJWT(token);

            if (jwt != null) {
                // 获取用户信息
                String username = jwt.getSubject(); // username
                String uid = jwt.getClaim("uid").asString();

                // 获取角色列表
                String rolesStr = jwt.getClaim("roles").asString();
                List<String> roles = (rolesStr != null) ? Arrays.asList(rolesStr.split(",")) : List.of();

                // 获取权限列表
                String permsStr = jwt.getClaim("perms").asString();
                List<String> perms = (permsStr != null) ? Arrays.asList(permsStr.split(",")) : List.of();

                // 设置到 request 中，供 Controller 使用
                request.setAttribute("username", username);
                request.setAttribute("uid", uid);
                request.setAttribute("roles", roles);
                request.setAttribute("perms", perms);

                return true;
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token 解析失败");
                return false;
            }
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "缺少 Token");
        return false;
    }
}