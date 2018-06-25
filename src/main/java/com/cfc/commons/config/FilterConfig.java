package com.cfc.commons.config;

import com.cfc.util.filter.RequestResponseContextHolderFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * author fangchen
 * date  2018/6/25 下午3:19
 */
@Configuration
public class FilterConfig {

    /**
     * 配置过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(requestResponseContextHolderFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("requestResponseContextHolderFilter");
        return registration;
    }

    /**
     * 创建一个bean
     * @return
     */
    @Bean(name = "requestResponseContextHolderFilter")
    public Filter requestResponseContextHolderFilter() {
        return new RequestResponseContextHolderFilter();
    }
}
