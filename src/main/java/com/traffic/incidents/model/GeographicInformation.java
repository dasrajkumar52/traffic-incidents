package com.traffic.incidents.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GeographicInformation {

    private int distance;
    private String title;
    private String location_type;
    private int woeid;
    private String latt_long;

}
