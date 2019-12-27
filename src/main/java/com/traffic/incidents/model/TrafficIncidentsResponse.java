package com.traffic.incidents.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class TrafficIncidentsResponse {
    private String authenticationResultCode;
    private List<TrafficIncidentResource> resourceSets;

    @Setter@Getter
    public static class TrafficIncidentResource {
        private int estimatedTotal;
        private List<TrafficIncident> resources;
    }


}
