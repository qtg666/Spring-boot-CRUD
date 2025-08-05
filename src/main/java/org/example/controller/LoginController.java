package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.AdminService;
//import org.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Tag(name = "用户登陆", description = "用来登陆的接口")
@RestController
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AdminService userService;

    private final String TOKEN_PREFIX = "TOKEN_";
    private static final String COOKIE_NAME = "auth_token";//
    private static final long TOKEN_EXPIRE_HOURS = 2;//token有效时间

    @Operation(summary = "登陆", description = "用户登陆，输入用户名和密码",
    parameters = {@Parameter(name = "credentials", description = "一个Map，包含两个键值对：用户、密码", required = true,
            example = "{username = xiaoming, password = 123456}")})
    @PostMapping("/login")
    //ResponseEntity 是 Spring 提供的一个类，用于封装 HTTP 响应的完整信息，包括：
    //状态码（如 200 OK、401 Unauthorized）
    //响应头（Headers）
    //响应体（Body，即返回给客户端的数据）
    //@RequestBody表示这个参数的数据应该从 HTTP 请求的 body（请求体） 中获取
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> credentials,
                                              HttpServletResponse response) {// 注入 HttpServletResponse 用于设置 Cookie
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (userService.validateAdmin(username, password)) {//登陆成功时
            // 创建UUID作为token
            String token = UUID.randomUUID().toString();

            // ⭐将token存入Redis，并设置过期时间
            redisTemplate.opsForValue().set(TOKEN_PREFIX + token, username, TOKEN_EXPIRE_HOURS, TimeUnit.HOURS);

            // 创建 HttpOnly Cookie
            Cookie tokenCookie = new Cookie(COOKIE_NAME, token);
            tokenCookie.setHttpOnly(true);  // 关键：防止 JavaScript 访问
            tokenCookie.setDomain("localhost");
            //tokenCookie.setSecure(true);    // 关键：仅通过 HTTPS 传输
            tokenCookie.setPath("/");       // Cookie 作用路径，所有路径都有效

            tokenCookie.setMaxAge((int) TimeUnit.HOURS.toSeconds(TOKEN_EXPIRE_HOURS)); // 设置过期时间 (秒)

            // 将 Cookie 添加到 HTTP 响应头
            response.addCookie(tokenCookie);

            // 返回登录成功响应 (注意：不再需要在 JSON 中返回 token)
            //return Map.of("success", true, "message", "登录成功");
            return ResponseEntity.ok(new AuthenticationResponse(token));//返回token
        } else {
            System.out.println("401错误！！！");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//返回401状态码
        }
    }
        @GetMapping("/profile")
        public String getProfile(HttpServletRequest request) {
            // 1. ⭐从请求属性中获取由过滤器设置的用户名
            String username = (String) request.getAttribute("currentUsername");

            if (username == null) {
                return "用户未登录或 Token 无效";
            }

            // 2. 此时 username 已通过 Token 验证，可以安全使用
            // 这里可以查询数据库获取用户的详细信息
            return "欢迎，" + username + "! 这是你的个人资料。";
        }

        @GetMapping("/logout")
        public String logout(HttpServletRequest request, HttpServletResponse response) {
            String username = (String) request.getAttribute("currentUsername");
            if (username == null) {
                return "用户未登录";
            }

            // 1. 从请求的 Cookie 中获取 Token
            String token = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("auth_token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            if (token != null) {
                // 2. 立即从 Redis 中删除该 Token 的映射，使其失效 (主动登出的关键!)
                redisTemplate.delete(TOKEN_PREFIX + token);
            }

            // 3. 向客户端发送一个过期的 Cookie 来清除浏览器端的 Cookie
            Cookie clearCookie = new Cookie("auth_token", "");
            clearCookie.setHttpOnly(true);
            clearCookie.setSecure(true);
            clearCookie.setPath("/");
            clearCookie.setMaxAge(0); // MaxAge=0 表示立即过期
            response.addCookie(clearCookie);


            return "登出成功";
        }


    static class AuthenticationResponse {
        private String token;

        public AuthenticationResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}