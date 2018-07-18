package com.rappahannock.elasticsearch;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BulkProcessorDisposeBean implements DisposableBean
{
    private final BulkProcessor processor;

    @Autowired
    public BulkProcessorDisposeBean(BulkProcessor processor)
    {
        this.processor = processor;
    }

    @Override
    public void destroy() throws Exception
    {
        processor.close();
    }
}
