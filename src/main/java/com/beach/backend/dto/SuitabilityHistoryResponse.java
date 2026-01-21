package com.beach.backend.dto;

import java.util.List;

public class SuitabilityHistoryResponse {

    private List<HistoricalPoint> points;

    public SuitabilityHistoryResponse(List<HistoricalPoint> points) {
        this.points = points;
    }

    public List<HistoricalPoint> getPoints() {
        return points;
    }
}
