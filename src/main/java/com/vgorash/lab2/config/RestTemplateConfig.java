package com.vgorash.lab2.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Configuration
public class RestTemplateConfig {

    @Value("${lab2.truststore.location}")
    private String truststoreLocation;

    @Value("${lab2.truststore.password}")
    private String truststorePassword;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, CertificateException, IOException {

        SSLContext sslContext = SSLContextBuilder.create().
                loadTrustMaterial(new File(truststoreLocation), truststorePassword.toCharArray()).build();

        HttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext).
                setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);

        return builder.requestFactory(() -> requestFactory).messageConverters(
                new StringHttpMessageConverter(
                        StandardCharsets.UTF_8
                )).build();
    }

}
