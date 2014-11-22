package org.az.skill2peer.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.az.skill2peer.ColumnSensingFlatXMLDataSetLoader;
import org.az.skill2peer.IntegrationTestConstants;
import org.az.skill2peer.common.controller.AbstractControllerIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = ColumnSensingFlatXMLDataSetLoader.class)
@DatabaseSetup("/org/az/skill2peer/nuclei/user/users.xml")
public class ITFormLoginTest extends AbstractControllerIntegrationTest {

    private static final String NOT_FOUND_USER = "not@found.com";

    private static final String INVALID_PASSWORD = "invalidPassword";

    private static final String REQUEST_PARAM_PASSWORD = "password";

    private static final String REQUEST_PARAM_USERNAME = "username";

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Test
    public void login_CredentialsAreCorrect_ShouldRedirectUserToFrontPage() throws Exception {
        final CsrfToken csrfToken = new CsrfTokenBuilder()
                .headerName(IntegrationTestConstants.CSRF_TOKEN_HEADER_NAME)
                .requestParameterName(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME)
                .tokenValue(IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .build();

        mockMvc.perform(post("/login/authenticate")
                .param(REQUEST_PARAM_USERNAME, IntegrationTestConstants.User.REGISTERED_USER.getUsername())
                .param(REQUEST_PARAM_PASSWORD, IntegrationTestConstants.User.REGISTERED_USER.getPassword())
                .param(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME,
                        IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .sessionAttr(IntegrationTestConstants.CSRF_TOKEN_SESSION_ATTRIBUTE_NAME, csrfToken)
                )
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void login_InvalidPassword_ShouldRedirectUserToLoginForm() throws Exception {
        final CsrfToken csrfToken = new CsrfTokenBuilder()
                .headerName(IntegrationTestConstants.CSRF_TOKEN_HEADER_NAME)
                .requestParameterName(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME)
                .tokenValue(IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .build();

        mockMvc.perform(post("/login/authenticate")
                .param(REQUEST_PARAM_USERNAME, IntegrationTestConstants.User.REGISTERED_USER.getUsername())
                .param(REQUEST_PARAM_PASSWORD, INVALID_PASSWORD)
                .param(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME,
                        IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .sessionAttr(IntegrationTestConstants.CSRF_TOKEN_SESSION_ATTRIBUTE_NAME, csrfToken)
                )
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/login?error=bad_credentials"));
    }

    @Test
    public void login_UserNotFound_ShouldRedirectUserToLoginForm() throws Exception {
        final CsrfToken csrfToken = new CsrfTokenBuilder()
                .headerName(IntegrationTestConstants.CSRF_TOKEN_HEADER_NAME)
                .requestParameterName(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME)
                .tokenValue(IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .build();

        mockMvc.perform(post("/login/authenticate")
                .param(REQUEST_PARAM_USERNAME, NOT_FOUND_USER)
                .param(REQUEST_PARAM_PASSWORD, INVALID_PASSWORD)
                .param(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME,
                        IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .sessionAttr(IntegrationTestConstants.CSRF_TOKEN_SESSION_ATTRIBUTE_NAME, csrfToken)
                )
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/login?error=bad_credentials"));
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }
}
