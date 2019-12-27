package com.traffic.incidents.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class WeatherReportResponse {
    private List<WeatherReport> consolidated_weather;
}
