package com.example.featureflagdemo.controller;

import com.example.featureflagdemo.service.FF4jService;
import lombok.RequiredArgsConstructor;
import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DemoController {

    @Autowired
    private FF4j ff4j;

    private final FF4jService ff4jService;

    private static final String FEATURE_1 = "release-date-feature";

    @GetMapping("/feature/releasedate")
    public ResponseEntity<String> feature1Endpoint() {
        if (ff4j.check(FEATURE_1)) {
            return ResponseEntity.ok("Feature 1 is enabled, and the endpoint is active!");
        } else {
            return ResponseEntity.status(403).body("Feature 1 is currently disabled.");
        }
    }

    @GetMapping("/feature/role-based")
    public ResponseEntity<String> checkRoleBasedFeature(@RequestParam String role) {
        String response = ff4jService.accessRolebasedFeature(role);
        return ResponseEntity.ok(response);
    }
}
