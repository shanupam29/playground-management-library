package com.somesystems.playground.config;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

import java.io.IOException;

@Configuration
public class WebConfig {

    @Order(0)
    @Bean
    public FilterRegistrationBean openEntityManagerFilterRegistrationBean() {
        // Set upload filter
        final MultipartFilter multipartFilter = new MultipartFilter();
        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(multipartFilter);
        filterRegistrationBean.addInitParameter("multipartResolverBeanName", "commonsMultipartResolver");

        return filterRegistrationBean;
    }

    @Bean
    public CommonsMultipartResolver commonsMultipartResolver() {

        FileSystemResource fileSystemResource = new FileSystemResource("./csv");
        final CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        try {
            commonsMultipartResolver.setUploadTempDir(fileSystemResource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return commonsMultipartResolver;
    }
}
