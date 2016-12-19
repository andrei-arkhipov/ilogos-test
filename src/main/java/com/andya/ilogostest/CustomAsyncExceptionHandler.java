package com.andya.ilogostest;

import org.apache.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * Created by AndyA on 16.12.2016.
 */
public class CustomAsyncExceptionHandler  implements AsyncUncaughtExceptionHandler {
    private static final Logger logger = Logger.getLogger(CustomAsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        logger.error("Exception message - " + throwable.getMessage());
        logger.error("Method name - " + method.getName());
        for (Object param : obj) {
            logger.error("Parameter value - " + param);
        }
    }
}
