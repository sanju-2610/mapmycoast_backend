package com.beach.backend.service;

import com.beach.backend.dto.HistoricalPoint;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HistoryMergeService {

    public List<HistoricalPoint> merge(
            List<HistoricalPoint> waves,
            Map<String, Double> winds
    ) {

        List<HistoricalPoint> merged = new ArrayList<>();

        for (HistoricalPoint w : waves) {
            double wind = winds.getOrDefault(w.getTimestamp(), 0.0);
            merged.add(new HistoricalPoint(
                    w.getTimestamp(),
                    w.getWaveHeight(),
                    wind
            ));
        }
        return merged;
    }
}
