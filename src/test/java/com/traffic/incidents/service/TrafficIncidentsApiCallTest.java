package com.traffic.incidents.service;

import com.traffic.incidents.model.TrafficIncidentsResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrafficIncidentsApiCallTest {

    @InjectMocks
    private TrafficIncidentsApiCall trafficIncidentsApiCall;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void getTrafficIncidentsTest() {
        ReflectionTestUtils.setField(trafficIncidentsApiCall, "trafficIncidentsApi", "http://dev.virtualearth.net/REST/v1/Traffic/Incidents/{mapArea}");
        ReflectionTestUtils.setField(trafficIncidentsApiCall, "apiKey", "dummy key");
        when(restTemplate.getForObject(anyString(), any())).thenReturn(new TrafficIncidentsResponse());
        TrafficIncidentsResponse trafficIncidents = trafficIncidentsApiCall.getTrafficIncidents("37,-105,45,-94");
        Assert.assertNotNull(trafficIncidents); // we can check more validation
    }

}
