package com.traffic.incidents.service;

import com.traffic.incidents.model.TrafficIncidentsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Service
public class TrafficIncidentsApiCall {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.traffic-incidents}")
    private String trafficIncidentsApi;

    @Value("${api.key}")
    private String apiKey;

    public TrafficIncidentsResponse getTrafficIncidents(String mapArea) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(trafficIncidentsApi)
                .queryParam("key", apiKey)
                .buildAndExpand(Collections.singletonMap("mapArea", mapArea));
        return restTemplate.getForObject(uriComponents.toUriString(), TrafficIncidentsResponse.class);
    }

}
