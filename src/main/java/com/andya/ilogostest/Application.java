package com.andya.ilogostest;

import java.util.concurrent.Executor;

import org.apache.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@PropertySource("classpath:application.properties")
public class Application extends AsyncConfigurerSupport {
    private static final Logger logger = Logger.getLogger(Application.class);

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        try {
            SpringApplication.run(Application.class, args);
        }
        catch (Exception e) {
            logger.error(CustomExceptionRootMessage.getCause(e));
        }
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Integer.parseInt(env.getRequiredProperty("core.thread.pool.size")));
        executor.setMaxPoolSize(Integer.parseInt(env.getRequiredProperty("max.thread.pool.size")));
        executor.setQueueCapacity(Integer.parseInt(env.getRequiredProperty("queue.capacity")));
        executor.setThreadNamePrefix("xmlparser-");
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
}