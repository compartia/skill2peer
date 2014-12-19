package org.az.skill2peer.nuclei.services;

import org.az.skill2peer.ColumnSensingFlatXMLDataSetLoader;
import org.az.skill2peer.config.ServicesTestContext;
import org.az.skill2peer.nuclei.config.DozerConfig;
import org.az.skill2peer.nuclei.config.PersistenceContext;
import org.az.skill2peer.nuclei.config.Skill2PeerApplicationContext;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
        ServicesTestContext.class,
        Skill2PeerApplicationContext.class,
        DozerConfig.class,
        PersistenceContext.class })
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class
})
@DbUnitConfiguration(dataSetLoader = ColumnSensingFlatXMLDataSetLoader.class)
@ActiveProfiles(profiles = "test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public abstract class AbstractServiceTest {

}
