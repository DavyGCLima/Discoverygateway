package com.example.eureka_gateway.eureka_gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jhipster")
@Getter
@Setter
public class JHipsterProperties {
    private final Security security = new Security();

    @Getter
    @Setter
    public static class Security {

        private final Authentication authentication = new Authentication();

        @Getter
        @Setter
        public static class Authentication {

            private final Jwt jwt = new Jwt();

            @Getter
            @Setter
            public static class Jwt {

                private String secret = null;

                private String base64Secret = null;

                private long tokenValidityInSeconds = 1800;
            }
        }

    }

}
