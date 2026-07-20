package com.impactgraph.impactgraph.controller;

import com.impactgraph.impactgraph.service.ImpactService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/impact")
public class ImpactController {

    private final ImpactService impactService;

    public ImpactController(ImpactService impactService) {
        this.impactService = impactService;
    }

    @GetMapping("/{fileName}")
    public Map<String, Object> getImpact(@PathVariable String fileName) {
        return impactService.findImpact(fileName);
    }
}