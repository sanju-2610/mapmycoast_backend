package com.beach.backend.dto;

public class HistoricalPoint {

    private String timestamp;
    private double waveHeight;
    private double windSpeed;

    public HistoricalPoint(String timestamp, double waveHeight, double windSpeed) {
        this.timestamp = timestamp;
        this.waveHeight = waveHeight;
        this.windSpeed = windSpeed;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getWaveHeight() {
        return waveHeight;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}
