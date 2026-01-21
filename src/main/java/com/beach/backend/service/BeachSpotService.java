package com.beach.backend.service;

import com.beach.backend.model.BeachSpot;
import com.beach.backend.repository.BeachSpotRepository;
import org.springframework.stereotype.Service;

@Service
public class BeachSpotService {

    private final BeachSpotRepository beachSpotRepository;

    public BeachSpotService(BeachSpotRepository beachSpotRepository) {
        this.beachSpotRepository = beachSpotRepository;
    }

    public Iterable<BeachSpot> getAllBeaches() {
        return beachSpotRepository.findAll();
    }

    // 🔴 THIS METHOD IS MUST
    public BeachSpot getBeachById(int id) {
        return beachSpotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Beach not found"));
    }
}
