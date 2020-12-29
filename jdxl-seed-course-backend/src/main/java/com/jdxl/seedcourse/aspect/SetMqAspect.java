package com.jdxl.seedcourse.aspect;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.jdxl.seedcourse.annotation.SetMq;
import com.jdxl.seedcourse.config.RabbitmqTemplate;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author ynght
 * @date 2020-04-14
 */
@Aspect
@Component
@Slf4j
public class SetMqAspect {
    @Autowired
    private RabbitmqTemplate rabbitmqTemplate;

    @Pointcut("execution(public * *(..))  && @annotation(com.jdxl.seedcourse.annotation.SetMq)")
    @Order(99)
    public void pointcut() {
    }

    @AfterReturning(returning = "result", pointcut = "pointcut()")
    public void handle(JoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SetMq annotation = signature.getMethod().getAnnotation(SetMq.class);
        String exchange = annotation.exchange();
        String routingKey = annotation.routingKey();
        if (Strings.isNullOrEmpty(exchange) || null == result) {
            log.warn("[SetMqInterceptor] is error. params is not complete,exchange: {}, routingKey:{} message:{}",
                    exchange, routingKey, result);
            return;
        }
        String message;
        try {
            if (result instanceof String) {
                message = (String) result;
            } else {
                message = JSON.toJSONString(result);
            }
            rabbitmqTemplate.setMq(message, exchange, routingKey);
        } catch (Exception e) {
            log.warn("[SetMqInterceptor] is fail. exchange: {}, routingKey:{} message:{}, e:{}",
                    exchange, routingKey, result, e);
        }
    }
}