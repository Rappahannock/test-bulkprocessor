package com.rappahannock.elasticsearch;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfiguration
{
    private final int bulkActionsCount;

    @Autowired
    public ESConfiguration(@Value("${bulk.processor.flush.actions.count}") int bulkActionsCount)
    {
        this.bulkActionsCount = bulkActionsCount;
    }

    @Bean
    public BulkProcessor getBulkProcessor(@Autowired RestHighLevelClient client)
    {
        return BulkProcessor.builder(client::bulkAsync, new BulkProcessorListener())
            .setBulkActions(bulkActionsCount).build();
    }
}