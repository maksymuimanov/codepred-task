package io.codepred.task.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String DEFAULT_API_VERSION = "1.0";
    public static final int DEFAULT_PATH_SEGMENT_INDEX = 1;

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer.addSupportedVersions(DEFAULT_API_VERSION)
                .setDefaultVersion(DEFAULT_API_VERSION)
                .usePathSegment(DEFAULT_PATH_SEGMENT_INDEX);
    }
}
