package com.beach.backend.service;

import com.beach.backend.dto.MarineWaveResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service   // 🔥 THIS WAS MISSING
public class MarineService {

    public double getWaveHeight(double latitude, double longitude) {

        String url =
                "https://marine-api.open-meteo.com/v1/marine?"
                        + "latitude=" + latitude
                        + "&longitude=" + longitude
                        + "&hourly=wave_height";

        RestTemplate restTemplate = new RestTemplate();
        MarineWaveResponse response =
                restTemplate.getForObject(url, MarineWaveResponse.class);

        if (response == null ||
                response.getHourly() == null ||
                response.getHourly().getWave_height().length == 0) {
            return 1.5; // safe fallback
        }

        return response.getHourly().getWave_height()[0];
    }
}
