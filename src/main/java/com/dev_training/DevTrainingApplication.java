package com.dev_training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * アプリケーションのメインクラス。
 */
@SpringBootApplication
public class DevTrainingApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(DevTrainingApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DevTrainingApplication.class);
    }
}

