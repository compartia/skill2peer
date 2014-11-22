package org.az.skill2peer.common.controller;

import org.az.skill2peer.config.UnitTestContext;
import org.az.skill2peer.nuclei.config.PersistenceContext;
import org.az.skill2peer.nuclei.config.SecurityContext;
import org.az.skill2peer.nuclei.config.Skill2PeerApplicationContext;
import org.az.skill2peer.nuclei.config.SocialContext;
import org.az.skill2peer.nuclei.config.WebAppContext;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
public abstract class AbstractControllerIntegrationTest {

}
