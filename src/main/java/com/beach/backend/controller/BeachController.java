package com.beach.backend.controller;

import com.beach.backend.dto.HistoricalPoint;
import com.beach.backend.dto.SuitabilityHistoryResponse;
import com.beach.backend.dto.SuitabilityResponse;
import com.beach.backend.model.BeachSpot;
import com.beach.backend.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BeachController {

    private final BeachSpotService beachSpotService;
    private final SuitabilityService suitabilityService;
    private final WeatherService weatherService;
    private final MarineService marineService;
    private final MarineHistoryService marineHistoryService;

    public BeachController(
            BeachSpotService beachSpotService,
            SuitabilityService suitabilityService,
            WeatherService weatherService,
            MarineService marineService,
            MarineHistoryService marineHistoryService
    ) {
        this.beachSpotService = beachSpotService;
        this.suitabilityService = suitabilityService;
        this.weatherService = weatherService;
        this.marineService = marineService;
        this.marineHistoryService = marineHistoryService;
    }

    // ✅ BEACH LIST
    @GetMapping("/beaches")
    public Iterable<BeachSpot> getAllBeaches() {
        return beachSpotService.getAllBeaches();
    }

    // ✅ LIVE SUITABILITY
    @GetMapping("/suitability/{id}")
    public SuitabilityResponse getSuitability(@PathVariable int id) {

        BeachSpot beach = beachSpotService.getBeachById(id);

        double windSpeed = weatherService.getWindSpeed(
                beach.getLatitude(),
                beach.getLongitude()
        );

        double waveHeight =
                marineService.getWaveHeight(
                        beach.getLatitude(),
                        beach.getLongitude()
                );

        return suitabilityService.calculateSuitability(
                beach.getRating(),
                waveHeight,
                windSpeed
        );
    }

    // ✅ HISTORICAL GRAPH (REAL DATA)
    @GetMapping("/suitability/history/{id}")
    public SuitabilityHistoryResponse getSuitabilityHistory(
            @PathVariable int id,
            @RequestParam(defaultValue = "7") int days
    ) {
        BeachSpot beach = beachSpotService.getBeachById(id);

        return new SuitabilityHistoryResponse(
                marineHistoryService.fetchDailyHistory(
                        beach.getLatitude(),
                        beach.getLongitude(),
                        days
                )
        );
    }
}
