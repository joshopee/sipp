package com.shopee.sipp.system.config;

import com.shopee.sipp.system.filter.SippWebFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = "com.shopee.sipp")
public class ApplicationConfig {
    @Bean
    public FilterRegistrationBean filterRegistration(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SippWebFilter());
        registration.addUrlPatterns("/*");
//        registration.setName("someFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public RestTemplate customRestTemplate()
    {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(1);
        httpRequestFactory.setConnectTimeout(1);
        httpRequestFactory.setReadTimeout(1);
        RestTemplate r = new RestTemplate(httpRequestFactory);
        return r;
    }


}
