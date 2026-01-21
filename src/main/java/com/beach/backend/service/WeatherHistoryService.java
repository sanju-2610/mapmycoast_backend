package com.beach.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherHistoryService {

    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public Map<String, Double> fetchWindHistory(
            double latitude,
            double longitude,
            int days
    ) {

        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(days - 1);

        String url = String.format(
                "https://api.open-meteo.com/v1/forecast?" +
                        "latitude=%s&longitude=%s" +
                        "&start_date=%s&end_date=%s" +
                        "&hourly=wind_speed_10m",
                latitude, longitude, start, end
        );

        Map<String, Double> windMap = new HashMap<>();

        try {
            String json = rest.getForObject(url, String.class);
            JsonNode root = mapper.readTree(json);
            JsonNode hourly = root.path("hourly");

            JsonNode times = hourly.path("time");
            JsonNode winds = hourly.path("wind_speed_10m");

            for (int i = 0; i < times.size(); i++) {
                windMap.put(times.get(i).asText(), winds.get(i).asDouble());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return windMap;
    }
}
