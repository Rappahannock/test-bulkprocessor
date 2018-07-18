package com.rappahannock.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HostsParser {
    public Set<ElasticNode> parse(String parameterValue) {
        String addresses = unquote(parameterValue);
        String[] addressesArray = addresses.split(",[ ]+");

        return Arrays.stream(addressesArray)
                .map(this::unquote)
                .map(hostAndPort -> hostAndPort.split(":"))
                .map(array -> {
                    String host = array[0];
                    String port = array.length > 1 ? array[1] : "-1";
                    log.debug("Host {} and port {}", host, port);
                    return new ElasticNode(host, Integer.parseInt(port));
                })
                .collect(Collectors.toSet());
    }

    private String unquote(String quotedString) {
        return quotedString.substring(1, quotedString.length() - 1);
    }
}
