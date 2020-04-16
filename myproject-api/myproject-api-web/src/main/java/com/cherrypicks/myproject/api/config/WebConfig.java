package com.cherrypicks.myproject.api.config;

import java.text.SimpleDateFormat;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cherrypicks.myproject.util.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
@ComponentScan(basePackages = {"com.cherrypicks.myproject.api.controller", "com.cherrypicks.myproject.service",
									"com.cherrypicks.myproject.dao"})
public class WebConfig implements WebMvcConfigurer {
	
    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }
    
    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> filterRegistrationBean(@Value("${spring.http.encoding.charset}") final String characterEncoding) {
    	final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding(characterEncoding);

        FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<CharacterEncodingFilter>();
        registrationBean.setFilter(characterEncodingFilter);
        registrationBean.addUrlPatterns("/**");
        return registrationBean;
    }
    
    @Bean
    public ObjectMapper jsonMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat(DateUtil.DATETIME_PATTERN_DEFAULT));
        return objectMapper;
    }
    
    @Bean
    MultipartConfigElement multipartConfigElement(@Value("${spring.servlet.multipart.max-file-size}") final Long multipartMaxFileSize,
    												@Value("${spring.servlet.multipart.max-request-size}") final Long multipartMaxRequesteSize) {
        final MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.of(multipartMaxFileSize, DataUnit.MEGABYTES));
        factory.setMaxRequestSize(DataSize.of(multipartMaxRequesteSize, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }
    
    @Bean
    public MessageSource messageSource(@Value("${spring.messages.basename}") final String baseName, @Value("${spring.messages.cache-duration}") final Integer cacheSeconds,
    									@Value("${spring.messages.encoding}") final String encoding) {
    	ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    	messageSource.setBasename(baseName);
    	messageSource.setCacheSeconds(cacheSeconds);
    	messageSource.setDefaultEncoding(encoding);
    	return messageSource;
    }
    
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        final ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setQueueCapacity(50);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        pool.setKeepAliveSeconds(300);
        return pool;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
    	
    }
    
}
