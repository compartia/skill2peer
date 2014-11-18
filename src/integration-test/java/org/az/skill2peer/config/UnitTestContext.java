package org.az.skill2peer.nuclei;

import static org.mockito.Mockito.mock;

import org.az.skill2peer.nuclei.common.controller.CourseController;
import org.az.skill2peer.nuclei.common.controller.S3Plugin;
import org.az.skill2peer.nuclei.user.service.UserService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@EnableAutoConfiguration
public class UnitTestContext {

    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

    @Bean
    public MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean
    public UserService userService() {
        return mock(UserService.class);
    }

    @Bean
    public CourseController courseController() {
        return mock(CourseController.class);
    }

    @Bean
    public S3Plugin s3Plugin() {
        return mock(S3Plugin.class);
    }

}
