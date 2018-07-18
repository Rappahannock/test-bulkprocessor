package com.rappahannock.elasticsearch;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class ElasticNode
{
    private final String host;
    private final int port;

    public ElasticNode(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public String getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
            return true;

        if (object == null || getClass() != object.getClass())
            return false;

        ElasticNode that = (ElasticNode) object;

        return port == that.port &&
            Objects.equals(host, that.host);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(host, port);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
            .add("host", host)
            .add("port", port)
            .toString();
    }
}
