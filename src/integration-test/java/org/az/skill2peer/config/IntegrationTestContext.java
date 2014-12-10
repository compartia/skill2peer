package org.az.skill2peer.config;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
public class IntegrationTestContext {

    private static final String LIQUIBASE_CHANGELOG_FILE = "classpath:db.changelog.xml";

    private static final String LIQUIBASE_CONTEXT = "integrationtest";

    @Autowired
    private DataSource dataSource;

    @Bean
    public SpringLiquibase liquibase() {
        final SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(LIQUIBASE_CHANGELOG_FILE);
        liquibase.setContexts(LIQUIBASE_CONTEXT);

        return liquibase;
    }
}
