package org.lemon.yhj.annotation.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * execution(* org.lemon.yhj..*.*(..)) 切点表达式含义解析
     *
     * execution()	表达式的主体
     * 第一个“*”符号	表示返回值的类型任意
     * org.lemon.yhj	AOP所切的服务的包名，即，需要进行横切的业务类
     * 包名后面的“..”	表示当前包及多级子包
     * 第二个“*”	表示类名，*即所有类
     * .*(..)	表示任何方法名，括号表示任意参数，两个点表示任何参数类型
     */
    @Pointcut("execution(* org.lemon.yhj..*.*(..))")
    public void point(){}

    @Before(value = "point()")
    public void before(JoinPoint joinPoint){
        logger.warn("before");
    }

    /**
     * 环绕通知
     * @param joinPoint 切点
     * @param log 通过@annotation(log)的方式可以获取注解参数（跟execution意义相同）
     *            参见：https://blog.csdn.net/u012887385/article/details/54600706
     * @return 返回代理方法的return，一定要，不然代理方法如果有返回值时会丢失
     * @throws Throwable 抛出所有异常的父类
     */
    @Around(value = "@annotation(log)")
    public Object around(JoinPoint joinPoint, Log log) throws Throwable {
        String name = joinPoint.getTarget().getClass().getName();
        Object[] params = joinPoint.getArgs();
        String method = joinPoint.getSignature().getName();
        logger.info("target={},signature={},args={},log={}", name, method, params, log.param());
        ProceedingJoinPoint proceedingJoinPoint = (ProceedingJoinPoint) joinPoint;

        //方法代理调用-时间统计
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        logger.info("This method cast {} ms!", System.currentTimeMillis() - start);
        return result;
    }

    @After(value = "point()")
    public void after(){
        logger.error("after");
    }

    @AfterReturning(value = "point()")
    public void afterReturn(JoinPoint joinPoint){}

    @AfterThrowing(pointcut = "point()", throwing = "e")
    public void exception(JoinPoint joinPoint, Throwable e){
        String name = joinPoint.getTarget().getClass().getName();
        Object[] params = joinPoint.getArgs();
        String method = joinPoint.getSignature().getName();
        logger.error("{}.{} is error! param={}, cause={}", name, method, params, e);
    }

}
