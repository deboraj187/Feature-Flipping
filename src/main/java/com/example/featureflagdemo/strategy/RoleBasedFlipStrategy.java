package com.example.featureflagdemo.strategy;

import org.ff4j.core.FeatureStore;
import org.ff4j.core.FlippingExecutionContext;
import org.ff4j.strategy.AbstractFlipStrategy;

import java.util.Arrays;
import java.util.Map;

public class RoleBasedFlipStrategy extends AbstractFlipStrategy {

    private static final String GRANTED_ROLES_PARAM = "GRANTED_ROLES";

    @Override
    public boolean evaluate(String featureName, FeatureStore store, FlippingExecutionContext executionContext) {
        String grantedRoles = getInitParams().get(GRANTED_ROLES_PARAM);

        if(grantedRoles == null || executionContext == null) {
            return false;
        }
        String userRole = executionContext.getString("USER_ROLE");
        return Arrays.asList(grantedRoles.split(",")).contains(userRole);
    }

    @Override
    public void init(String featureName, Map<String, String> initParams) {
        super.init(featureName, initParams);
        if (!initParams.containsKey(GRANTED_ROLES_PARAM)) {
            throw new IllegalArgumentException("Cannot instantiate RoleBasedFlipStrategy without 'GRANTED_ROLES' parameter");
        }
    }
}
