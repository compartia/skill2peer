package org.az.skill2peer.security;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;

public class CsrfTokenBuilder {

    private String headerName;

    private String requestParameterName;

    private String tokenValue;

    public CsrfTokenBuilder() {

    }

    public CsrfTokenBuilder headerName(final String headerName) {
        this.headerName = headerName;
        return this;
    }

    public CsrfTokenBuilder requestParameterName(final String requestParameterName) {
        this.requestParameterName = requestParameterName;
        return this;
    }

    public CsrfTokenBuilder tokenValue(final String tokenValue) {
        this.tokenValue = tokenValue;
        return this;
    }

    public CsrfToken build() {
        return new DefaultCsrfToken(headerName, requestParameterName, tokenValue);
    }
}
