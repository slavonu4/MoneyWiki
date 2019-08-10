package org.money.wiki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLogConfig {
    @Bean
    public CommonsRequestLoggingFilter requestLogFilter(){
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();

        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludeHeaders(true);

        return loggingFilter;
    }
}
