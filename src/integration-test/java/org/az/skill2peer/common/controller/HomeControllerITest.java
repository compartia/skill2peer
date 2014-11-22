package org.az.skill2peer.common.controller;

import static org.springframework.test.web.server.samples.context.SecurityRequestPostProcessors.userDetailsService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.az.skill2peer.ColumnSensingFlatXMLDataSetLoader;
import org.az.skill2peer.IntegrationTestConstants;
import org.az.skill2peer.config.UnitTestContext;
import org.az.skill2peer.nuclei.config.PersistenceContext;
import org.az.skill2peer.nuclei.config.SecurityContext;
import org.az.skill2peer.nuclei.config.Skill2PeerApplicationContext;
import org.az.skill2peer.nuclei.config.SocialContext;
import org.az.skill2peer.nuclei.config.WebAppContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@ActiveProfiles(profiles = "test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { UnitTestContext.class,
        WebAppContext.class,
        Skill2PeerApplicationContext.class,
        SecurityContext.class,
        PersistenceContext.class,
        SocialContext.class })
@WebAppConfiguration
@IntegrationTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = ColumnSensingFlatXMLDataSetLoader.class)
@DatabaseSetup("/org/az/skill2peer/nuclei/user/users.xml")
public class HomeControllerITest {

    private MockMvc mockMvc;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    /**
     * Should render Login page
     * @throws Exception
     */
    @Test
    public void showHomePageAsAnonymous() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    /**
     * Should render Home page
     * @throws Exception
     */
    @Test
    public void showHomePageAsFacebookUser() throws Exception {
        mockMvc.perform(get("/")
                .with(userDetailsService(IntegrationTestConstants.User.FACEBOOK_USER.getUsername()))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/index.jsp"));
    }

    /**
     * Should render Home page
     * @throws Exception
     */
    @Test
    public void showHomePageAsRegisteredUser() throws Exception {
        mockMvc.perform(get("/")
                .with(userDetailsService(IntegrationTestConstants.User.REGISTERED_USER.getUsername()))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/index.jsp"));
    }

    /**
     * Should render Home page
     * @throws Exception
     */
    @Test
    public void showHomePageAsTwitterUser() throws Exception {
        mockMvc.perform(get("/")
                .with(userDetailsService(IntegrationTestConstants.User.TWITTER_USER.getUsername()))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/index.jsp"));
    }
}
