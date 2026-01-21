package com.beach.backend.service;

import com.beach.backend.dto.HistoricalPoint;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class MarineHistoryService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private final WeatherHistoryService weatherHistoryService;

    public MarineHistoryService(WeatherHistoryService weatherHistoryService) {
        this.weatherHistoryService = weatherHistoryService;
    }

    // 🔥 DAILY AVERAGE HISTORY (10 DAYS)
    public List<HistoricalPoint> fetchDailyHistory(
            double latitude,
            double longitude,
            int days
    ) {

        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(days - 1);

        String url = String.format(
                "https://marine-api.open-meteo.com/v1/marine?" +
                        "latitude=%s&longitude=%s&start_date=%s&end_date=%s&hourly=wave_height",
                latitude, longitude, start, end
        );

        Map<String, List<Double>> dailyWaves = new HashMap<>();
        Map<String, List<Double>> dailyWinds = new HashMap<>();

        try {
            // 🌊 Wave data
            String json = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(json);
            JsonNode hourly = root.path("hourly");

            JsonNode times = hourly.path("time");
            JsonNode waves = hourly.path("wave_height");

            // 💨 Wind history
            Map<String, Double> windMap =
                    weatherHistoryService.fetchWindHistory(latitude, longitude, days);

            for (int i = 0; i < times.size(); i++) {
                String date = times.get(i).asText().substring(0, 10);

                dailyWaves
                        .computeIfAbsent(date, k -> new ArrayList<>())
                        .add(waves.get(i).asDouble(0.0));

                dailyWinds
                        .computeIfAbsent(date, k -> new ArrayList<>())
                        .add(windMap.getOrDefault(times.get(i).asText(), 0.0));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ✅ DAILY AVERAGE CALCULATION
        List<HistoricalPoint> result = new ArrayList<>();

        for (String date : dailyWaves.keySet()) {
            double avgWave = dailyWaves.get(date).stream()
                    .mapToDouble(Double::doubleValue)
                    .average().orElse(0.0);

            double avgWind = dailyWinds.get(date).stream()
                    .mapToDouble(Double::doubleValue)
                    .average().orElse(0.0);

            result.add(new HistoricalPoint(date, avgWave, avgWind));
        }

        // Sort by date
        result.sort(Comparator.comparing(HistoricalPoint::getTimestamp));

        return result;
    }
}
