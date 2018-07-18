package com.rappahannock.elasticsearch;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BulkProcessorListener implements BulkProcessor.Listener
{
    private static final Logger log = LoggerFactory.getLogger(BulkProcessorListener.class);

    @Override
    public void beforeBulk(long executionId, BulkRequest request)
    {
        log.trace("beforeBulk: executionId {}, BulkRequest {}", executionId, request);
    }

    @Override
    public void afterBulk(long executionId, BulkRequest request, BulkResponse response)
    {
        log.trace("afterBulk: executionId {}, BulkRequest {}, BulkResponse {}", executionId, request, response);
    }

    @Override
    public void afterBulk(long executionId, BulkRequest request, Throwable failure)
    {
        log.error("Failed to execute request {}, error is {}", request, failure);
    }
}
