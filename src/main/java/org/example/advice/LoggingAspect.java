package org.example.advice;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect//标记为了切面类
@Component//必须声明为spring的bean
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);//固定不变的、类级别的私有日志记录器
    //环绕通知
    @Around("execution(* org.example.service..*(..)) || execution(* org.example.controller..*(..))") // 指定需要拦截的方法
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {//声明该方法可能抛异常
        String methodName = joinPoint.getSignature().getName();//获取方法名
        Object[] args = joinPoint.getArgs();//用一个数组存传入的参数值

        // 记录输入参数
        logger.info("方法名: " + methodName);
        logger.info("输入参数: ");
        for (Object arg : args) {
            logger.info(arg.toString());
        }

        long start = System.currentTimeMillis();//记录方法起始时间
        Object proceed = joinPoint.proceed(); // 执行目标方法
        long executionTime = System.currentTimeMillis() - start;//当前时间减起始时间得到执行时间

        // 记录输出参数或返回值
        logger.info("方法 " + methodName + " 执行了 " + executionTime + "ms");
        logger.info("返回值: " + proceed);

        return proceed;//将拦截到的方法的返回值还给原接收方
    }
}
