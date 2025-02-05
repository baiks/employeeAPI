package com.employee.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mail")
public class NotificationConfig {
    private Smtp smtp;
    private String username;
    private String password;

    @Data
    public static class Smtp {
        private String host;
        private int port;
        private boolean auth;
        private StartTls starttls;
    }

    @Data
    public static class StartTls {
        private boolean enable;
    }
}

