package com.impactgraph.impactgraph.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RiskScoringService {

    public Map<String, Object> calculateRisk(String fileName, List<String> impactedFiles) {

        int impactedCount = impactedFiles.size();

        // Stronger, simpler score
        int score = impactedCount * 3;

        // Extra weight for wider blast radius
        if (impactedCount >= 3) {
            score += 5;
        }

        String impactLevel;
        if (score >= 10) {
            impactLevel = "HIGH";
        } else if (score >= 5) {
            impactLevel = "MEDIUM";
        } else {
            impactLevel = "LOW";
        }

        Map<String, Object> result = new HashMap<>();
        result.put("file", fileName);
        result.put("riskScore", score);
        result.put("impactLevel", impactLevel);
        result.put("impactedFiles", impactedFiles);
        result.put("impactedFileCount", impactedCount);

        return result;
    }
}