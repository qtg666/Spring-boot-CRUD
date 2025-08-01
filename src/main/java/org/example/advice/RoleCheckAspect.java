package org.example.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.annotations.CheckRole1;
import org.example.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RoleCheckAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private AdminService userService;

    //private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);//固定不变的、类级别的私有日志记录器
    private ProceedingJoinPoint joinPoint;

    private CheckRole1 checkRole;

    //ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    //HttpServletRequest request = attributes.getRequest();
    //获取当前请求的用户名（filter捞出来的）


    @Around("@annotation(checkRole)") // 拦截所有带有 @CheckRole1 注解的方法
    public Object checkRoles(ProceedingJoinPoint joinPoint, CheckRole1 checkRole) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        String usn = (String) request.getAttribute("currentUsername");
        // 从请求中获取attribute
        //Object currentUser = request.getAttribute("currentUser");
        //System.out.println("Current User: " + currentUser);

        //根据用户名查角色
        Boolean hasPermission = userService.permission(usn);

        if (!hasPermission) {
            throw new AccessDeniedException("访问被拒绝：你没有权限访问该方法。");
        }

        // 如果有权限，继续执行目标方法
        return joinPoint.proceed();
    }
}





