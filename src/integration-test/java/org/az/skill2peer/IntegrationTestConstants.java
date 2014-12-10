package org.az.skill2peer;

import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

public class IntegrationTestConstants {

    public enum User {

        FACEBOOK_USER("facebook@socialuser.com", null),
        REGISTERED_USER("registered@user.com", "password"),
        TWITTER_USER("twitter@socialuser.com", null);

        private final String password;

        private final String username;

        private User(final String username, final String password) {
            this.password = password;
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public String getUsername() {
            return username;
        }
    }

    public static final String CSRF_TOKEN_HEADER_NAME = "X-CSRF-TOKEN";

    public static final String CSRF_TOKEN_REQUEST_PARAM_NAME = "_csrf";

    public static final String CSRF_TOKEN_SESSION_ATTRIBUTE_NAME = HttpSessionCsrfTokenRepository.class
            .getName()
            .concat(".CSRF_TOKEN");

    public static final String CSRF_TOKEN_VALUE = "f416e226-bebc-401d-a1ed-f10f47aa9c56";
}
