package com.example.featureflagdemo.config;


import com.example.featureflagdemo.strategy.RoleBasedFlipStrategy;
import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.core.FlippingStrategy;
import org.ff4j.strategy.time.ReleaseDateFlipStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FF4jConfig {

    private static final String RELEASE_DATE_FEATURE = "release-date-feature";
    private static final String ROLE_BASED_FEATURE = "role-based-feature";

    @Bean
    public FF4j ff4j() {
        FF4j ff4j = new FF4j();

        Feature releaseDateFeature = new Feature(RELEASE_DATE_FEATURE);
        releaseDateFeature.setEnable(true);
        FlippingStrategy strategy = new ReleaseDateFlipStrategy("2024-12-09-17:57");
        releaseDateFeature.setFlippingStrategy(strategy);

        Feature roleBasedFeature = new Feature(ROLE_BASED_FEATURE);
        roleBasedFeature.setEnable(true);
        Map<String, String> params = new HashMap<>();
        params.put("GRANTED_ROLES", "ADMIN,USER");
        FlippingStrategy roleBasedStrategy = new RoleBasedFlipStrategy();
        roleBasedStrategy.init(ROLE_BASED_FEATURE, params);
        roleBasedFeature.setFlippingStrategy(roleBasedStrategy);

        ff4j.createFeature(releaseDateFeature);
        ff4j.createFeature(roleBasedFeature);
        ff4j.audit(true);
        return ff4j;
    }
}
