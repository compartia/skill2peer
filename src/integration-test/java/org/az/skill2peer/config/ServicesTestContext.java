package org.az.skill2peer.config;

import static org.mockito.Mockito.mock;

import org.az.skill2peer.nuclei.common.controller.CourseController;
import org.az.skill2peer.nuclei.common.controller.S3Plugin;
import org.az.skill2peer.nuclei.services.CalendarService;
import org.az.skill2peer.nuclei.services.CalendarServiceImpl;
import org.az.skill2peer.nuclei.services.CourseService;
import org.az.skill2peer.nuclei.services.CourseServiceImpl;
import org.az.skill2peer.nuclei.user.service.UserService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 *
 * @author Artem Zaborskiy
 *
 */
@Profile("test")
@Configuration
@EnableAutoConfiguration
public class ServicesTestContext {

    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

    @Bean
    public CalendarService calendarService() {
        return new CalendarServiceImpl();
    }

    @Bean
    public CourseController courseController() {
        return mock(CourseController.class);
    }

    @Bean
    public CourseService courseService() {
        return new CourseServiceImpl();
    }

    @Bean
    public MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public S3Plugin s3Plugin() {
        return mock(S3Plugin.class);
    }

    @Bean
    public UsersConnectionRepository usersConnectionRepository() {
        return mock(UsersConnectionRepository.class);
    }

    @Bean
    public UserService userService() {
        return mock(UserService.class);
    }

}
