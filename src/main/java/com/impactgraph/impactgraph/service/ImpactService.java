package com.impactgraph.impactgraph.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImpactService {

    private final GraphService graphService;

    public ImpactService(GraphService graphService) {
        this.graphService = graphService;
    }

    // used by /impact API
    public Map<String, Object> findImpact(String fileName) {
        List<String> impactedFiles = findImpactedFiles(fileName);

        Map<String, Object> result = new HashMap<>();
        result.put("file", fileName);
        result.put("count", impactedFiles.size());
        result.put("impactedFiles", impactedFiles);
        return result;
    }

    // used internally by ReportService
    public List<String> findImpactedFiles(String fileName) {

        Map<String, Set<String>> reverseGraph = graphService.getReverseGraph();
        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(fileName);
        visited.add(fileName);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            Set<String> dependents =
                    reverseGraph.getOrDefault(current, Collections.emptySet());

            for (String dep : dependents) {
                if (!visited.contains(dep)) {
                    visited.add(dep);
                    queue.add(dep);
                }
            }
        }

        visited.remove(fileName);
        return new ArrayList<>(visited);
    }
}