package com.nlo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // The core number of threads
        executor.setMaxPoolSize(50); // Maximum number of threads
        executor.setQueueCapacity(100); // The size of the task queue
        executor.setThreadNamePrefix("async-executor-");
        executor.initialize();
        return executor;
    }
}