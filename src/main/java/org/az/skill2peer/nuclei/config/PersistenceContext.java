package org.az.skill2peer.nuclei.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableJpaRepositories(basePackages = { "org.az.skill2peer.nuclei.user.repository" })
@EnableTransactionManagement
public class PersistenceContext implements S2PAppCtx {

    private static final String[] PROPERTY_PACKAGES_TO_SCAN = {
            "org.az.skill2peer.nuclei.common.model",
            "org.az.skill2peer.nuclei.user.model" };

    protected static final String PROP_DATABASE_DRIVER = "db.driver";

    protected static final String PROP_DATABASE_PASSWORD = "db.password";

    protected static final String PROP_DATABASE_URL = "db.url";

    protected static final String PROP_DATABASE_USERNAME = "db.username";

    private static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";

    private static final String PROP_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";

    private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

    private static final String PROP_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";

    private static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {
        final BoneCPDataSource dataSource = new BoneCPDataSource();

        dataSource.setDriverClass(env.getRequiredProperty(PROP_DATABASE_DRIVER));
        dataSource.setJdbcUrl(env.getRequiredProperty(PROP_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROP_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(PROPERTY_PACKAGES_TO_SCAN);

        final Properties jpaProperties = new Properties();
        jpaProperties.put(PROP_HIBERNATE_DIALECT, env.getRequiredProperty(PROP_HIBERNATE_DIALECT));
        jpaProperties.put(PROP_HIBERNATE_FORMAT_SQL, env.getRequiredProperty(PROP_HIBERNATE_FORMAT_SQL));
        jpaProperties.put(PROP_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(PROP_HIBERNATE_HBM2DDL_AUTO));
        jpaProperties.put(PROP_HIBERNATE_NAMING_STRATEGY, env.getRequiredProperty(PROP_HIBERNATE_NAMING_STRATEGY));
        jpaProperties.put(PROP_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
