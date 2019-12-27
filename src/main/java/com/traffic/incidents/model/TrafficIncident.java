package com.traffic.incidents.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TrafficIncident {
    private String __type;
    private String description;
    private Point point;
    private String end;
    private long incidentId;
    private String lastModified;
    private boolean roadClosed;
    private int severity;
    private int source;
    private String start;
    private Point toPoint;
    private int type;
    private boolean verified;
    private String avgMax;
    private String avgMin;

    @Getter
    @Setter
    public static class Point {
        private String type;
        private float[] coordinates;
    }
}
