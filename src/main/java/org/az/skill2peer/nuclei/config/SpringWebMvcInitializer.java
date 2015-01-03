package org.az.skill2peer.nuclei.config;

import java.io.File;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringWebMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private final int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB

    @Override
    protected void customizeRegistration(final ServletRegistration.Dynamic registration) {

        final File uploadDirectory = new File("imageUploads");//ServiceConfiguration.CRM_STORAGE_UPLOADS_DIRECTORY;

        final MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                        maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);

        registration.setMultipartConfig(multipartConfigElement);

    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
                SecurityContext.class,
                SocialContext.class,
                PersistenceContext.class,
                Skill2PeerApplicationContext.class, WebAppContext.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;//new Class[] {  };
    }

    //    @Override
    //    protected Filter[] getServletFilters() {
    //        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    //        characterEncodingFilter.setEncoding("UTF-8");
    //        characterEncodingFilter.setForceEncoding(true);
    //
    //        return new Filter[] {
    //                characterEncodingFilter,
    //                new HiddenHttpMethodFilter(),
    //                // new MultipartFilter(),
    //                new ConfigurableSiteMeshFilter() };
    //    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

}
