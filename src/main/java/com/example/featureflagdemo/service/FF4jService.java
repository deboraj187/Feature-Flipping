package com.example.featureflagdemo.service;

import lombok.RequiredArgsConstructor;
import org.ff4j.FF4j;
import org.ff4j.core.FlippingExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FF4jService {

    private final FF4j ff4j;

    @Bean
    public CommandLineRunner getCommandLineRunner() {
        return args -> {
            System.out.println("Available features: " + ff4j.getFeatures());
            while(true) {
                if (ff4j.check("release-date-feature")) {
                    System.out.println("release-date-feature is enabled");
                } else {
                    System.out.println("release-date-feature is disabled");
                }
                Thread.sleep(5000);
            }
        };
    }

    public String accessRolebasedFeature(String role) {
        FlippingExecutionContext context = new FlippingExecutionContext();
        context.addValue("USER_ROLE", role);
        if (ff4j.check("role-based-feature", context)) {
            return "Granted for role " + role;
        } else {
            return "Access denied for role " + role;
        }
    }
}
