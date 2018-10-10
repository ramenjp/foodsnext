package com.dev_training.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "todo")
@PropertySource(value = "classpath:code.properties", encoding = "UTF-8")
public class Priority {

    private Map<String, String> priority;

    public Map<String, String> getPriority() {
        return priority;
    }

    public void setPriority(Map<String, String> priority) {
        this.priority = priority;
    }
}
