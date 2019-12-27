package com.traffic.incidents.service;

import com.traffic.incidents.model.GeographicInformation;
import com.traffic.incidents.model.WeatherReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class WeatherApiCall {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.weather.geographic}")
    private String weatherGeoApi;

    @Value("${api.weather.temperature}")
    private String weatherTemperatureApi;

    /**
     * Fetch geographic information based on latitude and longitude.
     * @param lattLong
     * @return
     */
    public List<GeographicInformation> fetchGeographicInformation(String lattLong) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(weatherGeoApi).queryParam("lattlong", lattLong);
        ResponseEntity<List<GeographicInformation>> response = restTemplate.exchange(
                uriComponentsBuilder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GeographicInformation>>(){});
        return response.getBody();
    }

    /**
     * Fetch weather report based on woeId
     * @param woeId
     * @return
     */
    public WeatherReportResponse fetchWeather(Integer woeId) {
        UriComponents urlComponents = UriComponentsBuilder.fromHttpUrl(weatherTemperatureApi).buildAndExpand(Collections.singletonMap("woeId", woeId));
        return restTemplate.getForObject(urlComponents.toUriString(), WeatherReportResponse.class);
    }
}
