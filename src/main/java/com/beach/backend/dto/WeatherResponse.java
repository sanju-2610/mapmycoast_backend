package com.beach.backend.dto;

public class WeatherResponse {

    private Wind wind;

    public Wind getWind() {
        return wind;
    }

    public static class Wind {
        private double speed;

        public double getSpeed() {
            return speed;
        }
    }
}
