package org.az.skill2peer.nuclei.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.az.skill2peer.nuclei.UnitTestContext;
import org.az.skill2peer.nuclei.config.WebAppContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {WebAppContext.class, UnitTestContext.class})
////@ContextConfiguration(locations = {"classpath:unitTestContext.xml", "classpath:exampleApplicationContext-web.xml"})
//@WebAppConfiguration

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { UnitTestContext.class, WebAppContext.class })
@WebAppConfiguration
@IntegrationTest
public class SignUpControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .build();
    }

    @Test
    public void redirectRequestToRegistrationPage_ShouldRedirectToRegistrationPage() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/user/register"));
    }
}
