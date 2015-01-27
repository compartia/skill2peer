package org.az.skill2peer.nuclei.config;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 *
 * @author Artem Zaborskiy
 *
 */
@Configuration
public class DozerConfig {

    @Bean
    public DozerBeanMapperFactoryBean configureDozerBeanMapperFactoryBean() {
        final DozerBeanMapperFactoryBean mapper = new DozerBeanMapperFactoryBean();
        mapper.setMappingFiles(new Resource[] {
                new ClassPathResource("dozer-mapping.xml")
        });

        return mapper;
    }

}
