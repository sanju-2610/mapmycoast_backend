package com.beach.backend.controller;

import com.beach.backend.dto.SuitabilityHistoryResponse;
import com.beach.backend.service.MarineHistoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "*")
public class HistoryController {

    private final MarineHistoryService marineHistoryService;

    public HistoryController(MarineHistoryService marineHistoryService) {
        this.marineHistoryService = marineHistoryService;
    }

    @GetMapping("/{beachId}")
    public SuitabilityHistoryResponse getHistory(
            @PathVariable int beachId,
            @RequestParam double lat,
            @RequestParam double lon
    ) {
        return new SuitabilityHistoryResponse(
                marineHistoryService.fetchDailyHistory(lat, lon, 10)
        );
    }
}
