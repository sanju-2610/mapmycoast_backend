package com.beach.backend.dto;

public class MarineWaveResponse {
    private Hourly hourly;

    public Hourly getHourly() {
        return hourly;
    }

    public static class Hourly {
        private double[] wave_height;

        public double[] getWave_height() {
            return wave_height;
        }
    }
}
