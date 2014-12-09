package org.az.skill2peer.nuclei.config;

import java.io.File;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.MultipartConfigElement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan(basePackages = {
        "org.az.skill2peer.nuclei.common.controller",
        "org.az.skill2peer.nuclei.security.controller",
        "org.az.skill2peer.nuclei.user.controller" })
@EnableWebMvc
public class WebAppContext extends WebMvcConfigurerAdapter implements S2PAppCtx {

    private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/jsp/";

    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";

    private final int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //    @Bean
    //    public CommonsMultipartResolver getMultipartResolver() {
    //        final CommonsMultipartResolver resolver = new CommonsMultipartResolver();
    //        //        resolver.setMaxUploadSize("50000000");
    //        return resolver;
    //    }

    @Bean
    public SimpleMappingExceptionResolver exceptionResolver() {
        final SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        final Properties exceptionMappings = new Properties();

        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");

        exceptionResolver.setExceptionMappings(exceptionMappings);

        final Properties statusCodes = new Properties();

        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");

        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }

    @Bean
    public LocaleResolver localeResolver() {
        final SessionLocaleResolver slr = new SessionLocaleResolver();
        final Locale ruLocale = new Locale.Builder().setLanguage("ru").setRegion("RU").build();
        slr.setDefaultLocale(ruLocale);
        return slr;
    }

    @Bean
    public MultipartConfigElement multipartConfig() {
        //            final MultipartConfigFactory factory = new MultipartConfigFactory();
        //            factory.setMaxFileSize("128KB");
        //            factory.setMaxRequestSize("128KB");
        //
        //            return factory.createMultipartConfig();

        final File uploadDirectory = new File("imageUploads");//ServiceConfiguration.CRM_STORAGE_UPLOADS_DIRECTORY;

        final MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                        maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);

        return multipartConfigElement;
    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);

        return viewResolver;
    }
}
