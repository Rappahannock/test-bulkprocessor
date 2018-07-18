package com.rappahannock.elasticsearch;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestClientDisposeBean implements DisposableBean
{
    private final RestClient restClient;
    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public RestClientDisposeBean(RestClient restClient, RestHighLevelClient restHighLevelClient)
    {
        this.restClient = restClient;
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public void destroy() throws Exception
    {
        restClient.close();
        //for ES 6.2.4
        //restHighLevelClient.close();
    }
}
