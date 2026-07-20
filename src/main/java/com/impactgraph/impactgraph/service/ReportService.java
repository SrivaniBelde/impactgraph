package com.impactgraph.impactgraph.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final ImpactService impactService;
    private final RiskScoringService riskScoringService;

    public ReportService(ImpactService impactService,
                         RiskScoringService riskScoringService) {
        this.impactService = impactService;
        this.riskScoringService = riskScoringService;
    }

    public Map<String, Object> generateReport(String fileName) {
        List<String> impactedFiles = impactService.findImpactedFiles(fileName);
        return riskScoringService.calculateRisk(fileName, impactedFiles);
    }
}