package com.beach.backend.service;

import com.beach.backend.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * ✅ Fetch wind speed using latitude & longitude
     * This avoids "city not found" error completely
     */
    public double getWindSpeed(double latitude, double longitude) {

        String url = String.format(
                "https://api.openweathermap.org/data/2.5/weather"
                        + "?lat=%s&lon=%s&appid=%s&units=metric",
                latitude,
                longitude,
                apiKey
        );

        try {
            WeatherResponse response =
                    restTemplate.getForObject(url, WeatherResponse.class);

            if (response != null && response.getWind() != null) {
                return response.getWind().getSpeed();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ✅ Safe fallback (no crash)
        return 0.0;
    }
}
