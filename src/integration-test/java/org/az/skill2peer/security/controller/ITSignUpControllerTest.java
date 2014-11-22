package org.az.skill2peer.security.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.az.skill2peer.common.controller.AbstractControllerIntegrationTest;
import org.az.skill2peer.nuclei.Urls;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class ITSignUpControllerTest extends AbstractControllerIntegrationTest {

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Test
    public void redirectRequestToRegistrationPage_ShouldRedirectRequestToRegistrationPage() throws Exception {
        mockMvc.perform(get(Urls.USER_SIGNUP))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl(Urls.USER_REGISTER));
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }
}
