package com.emu.camunda.config.camunda;


import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * Copyright 2021-2022 By Dirac Systems.
 * <p>
 * Created by khalid.nouh on 30/3/2021.
 */
@Configuration
public class CamundaSecurityFilter {

    @Bean
    public FilterRegistrationBean<Filter> processEngineAuthenticationFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setName("camunda-auth");
        registration.setFilter(getProcessEngineAuthenticationFilter());
        registration.addInitParameter("authentication-provider",
            "org.camunda.bpm.engine.rest.security.auth.impl.HttpBasicAuthenticationProvider");
        registration.addUrlPatterns("/engine-rest/*");
        registration.setOrder(1);
        return registration;
    }

        @Bean
        public Filter getProcessEngineAuthenticationFilter () {
            return new ProcessEngineAuthenticationFilter();
        }
    }
