package com.impactgraph.impactgraph.controller;

import com.impactgraph.impactgraph.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{fileName}")
    public Map<String, Object> getReport(@PathVariable String fileName) {
        return reportService.generateReport(fileName);
    }
}