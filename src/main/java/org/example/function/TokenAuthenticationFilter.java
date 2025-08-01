package org.example.function;
import org.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String TOKEN_PREFIX = "login:token:";
    private static final String COOKIE_NAME = "auth_token";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String username = null;
        String token = null;

        try {
            // 1. 从请求的 Cookies 中查找指定名称的 Cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (COOKIE_NAME.equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            // 2. 如果找到了 Token
            if (token != null) {
                // 3. 使用 Token 从 Redis 查询对应的用户名
                username = redisTemplate.opsForValue().get(TOKEN_PREFIX + token);
                // 注意：如果 Redis 中查不到（返回 null），说明 Token 无效或已过期
            }

            // 4. ⭐如果成功获取到用户名，可以将其放入请求属性中，供后续 Controller 使用
            if (username != null) {
                request.setAttribute("currentUsername", username);
                // 你也可以在这里设置一个自定义的认证上下文，或者集成 Spring Security
            } else {
                // Token 无效，可以在这里记录日志或进行其他处理
                // 注意：这里通常不立即返回错误，而是让后续逻辑（如 Controller）去判断
            }

        } catch (Exception e) {
            // 处理可能的异常 (如 Redis 连接问题)
            logger.error("Token 验证过程中发生异常", e);
            // 可以选择忽略，让后续逻辑处理，或者设置 username 为 null
        }

        // 5. 继续执行过滤链
        filterChain.doFilter(request, response);
    }
}
