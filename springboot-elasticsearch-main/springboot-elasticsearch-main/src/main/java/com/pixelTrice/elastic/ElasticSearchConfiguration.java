package com.pixelTrice.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration
{
    @Bean
    public RestClient getRestClient() {
		
		/*
		 * final CredentialsProvider credentialsProvider = new
		 * BasicCredentialsProvider(); credentialsProvider.setCredentials(AuthScope.ANY,
		 * new UsernamePasswordCredentials("elastic", "fU0kdJyviRqISD_=xsO1"));
		 */

		RestClient restClient = RestClient.builder(new HttpHost("10.254.0.100", 9200))
				/*
				 * .setHttpClientConfigCallback(new HttpClientConfigCallback() {
				 * 
				 * @Override public HttpAsyncClientBuilder
				 * customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
				 * httpClientBuilder.disableAuthCaching(); return
				 * httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider); } })
				 */.build();
        return restClient;
    }

    @Bean
    public  ElasticsearchTransport getElasticsearchTransport() {
        return new RestClientTransport(
                getRestClient(), new JacksonJsonpMapper());
    }


    @Bean
    public ElasticsearchClient getElasticsearchClient(){
        ElasticsearchClient client = new ElasticsearchClient(getElasticsearchTransport());
        return client;
    }

}
