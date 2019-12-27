package com.traffic.incidents.resource;

import com.traffic.incidents.model.TrafficIncidentsWeatherResponse;
import com.traffic.incidents.service.TrafficIncidentsWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TrafficIncidentsResource {

    @Autowired
    private TrafficIncidentsWeatherService trafficIncidentsWeatherService;

    /**
     * Get the traffic incidents with weather info.
     * @param mapArea A rectangular area specified as a bounding box. E.g 37,-105,45,-94
     * @return TrafficIncidentsWeatherResponse
     */
    @RequestMapping("/traffic-weather")
    public TrafficIncidentsWeatherResponse getTrafficIncidentsAlongWeather(@RequestParam("mapArea") String mapArea) {
        TrafficIncidentsWeatherResponse trafficIncidentsWeatherResponse = trafficIncidentsWeatherService.trafficIncidentsWeatherResponse(mapArea);
        return trafficIncidentsWeatherResponse;
    }

}
