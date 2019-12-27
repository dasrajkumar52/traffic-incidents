package com.traffic.incidents.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class WeatherReport {
    private long id;
    private float min_temp;
    private float max_temp;
}
