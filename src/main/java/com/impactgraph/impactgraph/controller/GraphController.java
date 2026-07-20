package com.impactgraph.impactgraph.controller;

import com.impactgraph.impactgraph.service.GraphService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
public class GraphController {

    private final GraphService graphService;

    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/graph")
    public Map<String, Set<String>> getGraph() {
        return graphService.getGraph();
    }

    @GetMapping("/reverse-graph")
    public Map<String, Set<String>> getReverseGraph() {
        return graphService.getReverseGraph();
    }
}