package com.rappahannock.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Set;

@Configuration
public class RestClientConfiguration
{
    private final HostsParser hostsParser;
    private final String restAddresses;

    @Autowired
    public RestClientConfiguration(HostsParser hostsParser,
                                   @Value("${elasticsearch.hosts}") String restAddresses)
    {
        this.hostsParser = hostsParser;
        this.restAddresses = restAddresses;
    }

    private void addAuthorization(RestClientBuilder restClientBuilder, String login, String password)
    {
        if (!StringUtils.isEmpty(login))
        {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(login, password));
            restClientBuilder.setHttpClientConfigCallback(
                httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        }
    }

    private RestClientBuilder getRestClientBuilder()
    {
        Set<ElasticNode> nodes = hostsParser.parse(restAddresses);
        HttpHost[] hosts = nodes.stream()
            .map(node -> new HttpHost(node.getHost(), node.getPort()))
            .toArray(HttpHost[]::new);

        return RestClient.builder(hosts);
    }

    @Bean
    public RestClientBuilder getRestClientBuilder(@Value("${elasticsearch.login}") String login,
                                                  @Value("${elasticsearch.password}") String password)
    {
        RestClientBuilder restClientBuilder = getRestClientBuilder();
        addAuthorization(restClientBuilder, login, password);

        return restClientBuilder;
    }

    @Bean
    public RestClient getRestClient(RestClientBuilder restClientBuilder)
    {
        return restClientBuilder.build();
    }

    // for ES 5.6.6
    @Bean
    public RestHighLevelClient getRestHighLevelClient(RestClient restClient)
    {
        return new RestHighLevelClient(restClient);
    }

    // for ES 6.2.4
//    @Bean
//    public RestHighLevelClient getRestHighLevelClient(RestClientBuilder restClientBuilder)
//    {
//        return new RestHighLevelClient(restClientBuilder);
//    }
}
