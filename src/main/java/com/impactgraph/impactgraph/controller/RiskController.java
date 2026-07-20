package com.impactgraph.impactgraph.controller;

import com.impactgraph.impactgraph.service.ImpactService;
import com.impactgraph.impactgraph.service.RiskScoringService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/risk")
public class RiskController {

    private final ImpactService impactService;
    private final RiskScoringService riskScoringService;

    public RiskController(ImpactService impactService,
                          RiskScoringService riskScoringService) {
        this.impactService = impactService;
        this.riskScoringService = riskScoringService;
    }

    @GetMapping("/{fileName}")
    public Map<String, Object> getRisk(@PathVariable String fileName) {
        List<String> impactedFiles = impactService.findImpactedFiles(fileName);
        return riskScoringService.calculateRisk(fileName, impactedFiles);
    }
}