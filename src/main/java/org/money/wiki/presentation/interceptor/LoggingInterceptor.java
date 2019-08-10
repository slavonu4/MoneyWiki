package org.money.wiki.presentation.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplate.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        String requestMethod = httpRequest.getMethodValue();
        String requestUrl = httpRequest.getURI().toASCIIString();

        LOGGER.info("Sending {} request to the {}", requestMethod, requestUrl);
        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
        LOGGER.info("{} request to the {} was sent. Response: {}", requestMethod, requestUrl, response.getStatusCode());

        return response;
    }
}
