package com.traffic.incidents.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TrafficIncidentsWeatherResponse {
    private TrafficIncidentsResponse trafficIncidentsResponse;
}
