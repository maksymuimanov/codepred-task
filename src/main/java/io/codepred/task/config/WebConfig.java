package io.codepred.task.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration for the application.
 * <p>
 * Configures API versioning and other web-related settings
 * for the Spring MVC framework.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * Default API version used by API versioning.
     */
    private static final String DEFAULT_API_VERSION = "1.0";
    /**
     * Default URL path segment index for API versioning.
     * <p>
     * Indicates that the second segment of the URL path (index 1)
     * contains the API version.
     */
    public static final int DEFAULT_PATH_SEGMENT_INDEX = 1;

    /**
     * Configures API versioning for the application.
     * <p>
     * Sets up version 1.0 as the default and configures path-based
     * versioning using the second URL segment.
     *
     * @param configurer the API version configurer
     */
    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer.addSupportedVersions(DEFAULT_API_VERSION)
                .setDefaultVersion(DEFAULT_API_VERSION)
                .usePathSegment(DEFAULT_PATH_SEGMENT_INDEX);
    }
}
