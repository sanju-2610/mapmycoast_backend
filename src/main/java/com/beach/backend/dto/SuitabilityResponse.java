package com.beach.backend.dto;

public class SuitabilityResponse {

    private int score;
    private String message;
    private double windSpeed;
    private double waveHeight;

    public SuitabilityResponse(
            int score,
            String message,
            double windSpeed,
            double waveHeight
    ) {
        this.score = score;
        this.message = message;
        this.windSpeed = windSpeed;
        this.waveHeight = waveHeight;
    }

    public int getScore() {
        return score;
    }

    public String getMessage() {
        return message;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWaveHeight() {
        return waveHeight;
    }
}
