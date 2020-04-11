package com.bookstore.config;

import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.cloud.netflix.archaius.ArchaiusAutoConfiguration;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {ElasticsearchAutoConfiguration.class})
@EnableAsync
@EnableHystrix
public class ApplicationConfig {

    @Value("${es.host}")
    private String esHost;

    @Value("${es.port}")
    private Integer esPort;

    @Value("${es.user}")
    private String esusername;

    @Value("${es.password}")
    private String espassword;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient esClient() {

        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(esusername, espassword));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost(esHost, esPort, "https"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient(builder);
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
