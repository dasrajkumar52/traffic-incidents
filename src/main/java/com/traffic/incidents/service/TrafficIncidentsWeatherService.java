package com.traffic.incidents.service;

import com.traffic.incidents.model.*;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
public class TrafficIncidentsWeatherService {

    @Autowired
    private TrafficIncidentsApiCall trafficIncidentsApiCall;

    @Autowired
    private WeatherApiCall weatherApiCall;


    private ForkJoinPool forkJoinPool = new ForkJoinPool(10);
    private ForkJoinPool weatherReportForkJoinPool = new ForkJoinPool(10);

    /**
     * Get the traffic incidents with weather info.
     * @param mapArea A rectangular area specified as a bounding box. E.g 37,-105,45,-94
     * @return TrafficIncidentsWeatherResponse
     */
    public TrafficIncidentsWeatherResponse trafficIncidentsWeatherResponse(String mapArea) {
        TrafficIncidentsResponse trafficIncidents = trafficIncidentsApiCall.getTrafficIncidents(mapArea);

        Map<Long, Map<String, Double>> longMapMap = forkJoinPool.submit(() -> trafficIncidents.getResourceSets().stream().parallel()
                .map(TrafficIncidentsResponse.TrafficIncidentResource::getResources)
                .flatMap(List::stream)
                .map(trafficIncident ->
                        Tuple.of(trafficIncident.getIncidentId(), trafficIncident.getPoint().getCoordinates()[0] + "," + trafficIncident.getPoint().getCoordinates()[1]))
                .map(this::fetchGraphicInformation)
                .map(t -> Tuple.of(t._1(), getWeatherReport(t._2())))
                .map(t -> Tuple.of(t._1(), findMinAverage(t._2())))
                .collect(Collectors.toMap(t -> t._1(), t -> t._2()))).join();

        List<TrafficIncident> updatedTrafficIncidents = trafficIncidents.getResourceSets().stream()
                .parallel()
                .map(trafficIncidentResource -> trafficIncidentResource.getResources())
                .flatMap(List::stream)
                .map(trafficIncident -> {
                    trafficIncident.setAvgMax(longMapMap.get(trafficIncident.getIncidentId()).get("max_avg").toString());
                    trafficIncident.setAvgMin(longMapMap.get(trafficIncident.getIncidentId()).get("min_avg").toString());
                    return trafficIncident;
                })
                .collect(Collectors.toList());

        trafficIncidents.getResourceSets().forEach(r->r.setResources(updatedTrafficIncidents));
        return TrafficIncidentsWeatherResponse.builder().trafficIncidentsResponse(trafficIncidents).build();
    }

    private Tuple2<Long, List<GeographicInformation>> fetchGraphicInformation(Tuple2<Long, String> t) {
        return Tuple.of(t._1(), weatherApiCall.fetchGeographicInformation(t._2()));
    }

    /**
     * Responsible to fetch the weather details for all woeIds
     * @param geographicInformationList
     * @return
     */
    private List<WeatherReport> getWeatherReport(List<GeographicInformation> geographicInformationList) {
        List<WeatherReport> weatherReports = weatherReportForkJoinPool.submit(() -> geographicInformationList.stream().parallel()
                .map(geographicInformation -> weatherApiCall.fetchWeather(geographicInformation.getWoeid()).getConsolidated_weather())
                .flatMap(List::stream)
                .collect(Collectors.toList())).join();
        return weatherReports;
    }

    /**
     * Calculating the average of min and max temperatures.
     * @param weatherReports
     * @return
     */
    private Map<String, Double> findMinAverage(List<WeatherReport> weatherReports) {
        Double max_avg = weatherReports.stream().map(WeatherReport::getMax_temp).mapToDouble(w -> w).summaryStatistics().getAverage();
        Double min_avg = weatherReports.stream().map(WeatherReport::getMin_temp).mapToDouble(w -> w).summaryStatistics().getAverage();
        Map<String, Double> map = new HashMap<>();
        map.put("max_avg", max_avg);
        map.put("min_avg", min_avg);
        return map;
    }
}
