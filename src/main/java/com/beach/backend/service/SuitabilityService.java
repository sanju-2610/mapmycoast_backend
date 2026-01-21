package com.beach.backend.service;

import com.beach.backend.dto.SuitabilityResponse;
import org.springframework.stereotype.Service;

@Service
public class SuitabilityService {

    public SuitabilityResponse calculateSuitability(
            double rating,
            double waveHeight,
            double windSpeed
    ) {

        int score = 0;

        // ⭐ Rating weight
        score += rating * 10;

        // 🌊 Wave height AI logic
        if (waveHeight < 1)
            score += 30;
        else if (waveHeight < 2)
            score += 20;
        else
            score += 10;

        // 💨 Wind speed AI logic
        if (windSpeed < 5)
            score += 30;
        else if (windSpeed < 10)
            score += 20;
        else
            score += 10;

        score = Math.min(score, 100);

        String message;
        if (score >= 80)
            message = "Safe for swimming and recreation";
        else if (score >= 60)
            message = "Moderate conditions. Exercise caution";
        else
            message = "Unsafe conditions. Avoid activities";

        return new SuitabilityResponse(
                score,
                message,
                windSpeed,
                waveHeight
        );
    }
}
