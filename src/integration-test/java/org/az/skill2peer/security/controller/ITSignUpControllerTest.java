package org.az.skill2peer.security.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.az.skill2peer.config.UnitTestContext;
import org.az.skill2peer.nuclei.Urls;
import org.az.skill2peer.nuclei.config.PersistenceContext;
import org.az.skill2peer.nuclei.config.SecurityContext;
import org.az.skill2peer.nuclei.config.SocialContext;
import org.az.skill2peer.nuclei.config.WebAppContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
        UnitTestContext.class,
        WebAppContext.class,
        SecurityContext.class,
        PersistenceContext.class, SocialContext.class })
@WebAppConfiguration
public class ITSignUpControllerTest {

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    public void redirectRequestToRegistrationPage_ShouldRedirectRequestToRegistrationPage() throws Exception {
        mockMvc.perform(get(Urls.USER_SIGNUP))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl(Urls.USER_REGISTER));
    }
}
