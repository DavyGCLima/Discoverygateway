package com.example.eureka_gateway.eureka_gateway.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityUtils {

    public static final String USER_ACTIVE = "ACTIVE";
    public static final String USER_INACTIVE = "INACTIVE";
    public static final Integer INTERNAL_USER = 0;
    public static final String ROLE_INTERNAL_USER = "ROLE_INTERNAL_USER";
    public static final String ROLE_EXTERNAL_USER = "ROLE_EXTERNAL_USER";

}
