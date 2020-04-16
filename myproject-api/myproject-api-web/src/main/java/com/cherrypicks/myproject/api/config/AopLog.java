package com.cherrypicks.myproject.api.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AopLog {

    private Logger logger = LoggerFactory.getLogger(getClass());;
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void destroy() {
    }

    @Pointcut("execution(public * com.cherrypicks.myproject.api.controller..*.*(..))")// && !execution(* com.cherrypicks.myproject.api.controller.AppErrorController.*(..))
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(final JoinPoint joinPoint) throws Throwable {

		startTime.set(System.currentTimeMillis());

        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes != null) {
			final HttpServletRequest request = attributes.getRequest();
			logger.info("Requset Url:{},Http Method:{},Ip:{},Args:{}", request.getRequestURI().toString(), request.getMethod(),
					request.getRemoteAddr(), request.getQueryString());
		}
    }

	@AfterReturning(returning = "retObject", pointcut = "webLog()")
	public void doAfterReturning(final JoinPoint joinPoint, final Object retObject) throws Throwable {
		logger.info("Response Result:{}",retObject);
		logger.info("Response Success Spend Time : {} ms", (System.currentTimeMillis() - startTime.get()));
	}

	@AfterThrowing(throwing = "exception", pointcut = "webLog()")
	public void doAfterThrowing(final JoinPoint joinPoint, final Throwable exception) throws Throwable {
		logger.info("Response Failed: {}", exception.toString());
	}

}
