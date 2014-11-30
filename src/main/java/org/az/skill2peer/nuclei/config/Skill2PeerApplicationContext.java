package org.az.skill2peer.nuclei.config;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@ComponentScan(basePackages = { "org.az.skill2peer.nuclei.user.service" })
@PropertySource("classpath:application.properties")
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class Skill2PeerApplicationContext implements S2PAppCtx {

    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

    @Bean
    public DozerBeanMapperFactoryBean configureDozerBeanMapperFactoryBean() {
        final DozerBeanMapperFactoryBean mapper = new DozerBeanMapperFactoryBean();
        mapper.setMappingFiles(new Resource[] {
                new ClassPathResource("dozer-mapping.xml")
        });
        return mapper;
    }

    @Bean
    public javax.validation.Validator localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
