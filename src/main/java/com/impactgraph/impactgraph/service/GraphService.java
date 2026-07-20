package com.impactgraph.impactgraph.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphService {

    // File -> Dependencies
    private final Map<String, Set<String>> graph = new LinkedHashMap<>();

    // Dependency -> Files that use it
    private final Map<String, Set<String>> reverseGraph = new LinkedHashMap<>();

    // Class name -> File name
    private final Map<String, String> classIndex = new LinkedHashMap<>();

    public void addFile(String fileName, List<String> dependencies) {

        graph.put(fileName, new LinkedHashSet<>(dependencies));

        for (String dep : dependencies) {
            reverseGraph
                    .computeIfAbsent(dep, k -> new LinkedHashSet<>())
                    .add(fileName);
        }
    }

    public void registerClass(String className, String fileName) {
        classIndex.put(className, fileName);
    }

    public String getFileByClass(String className) {
        return classIndex.get(className);
    }

    public Map<String, String> getClassIndex() {
        return classIndex;
    }

    public Map<String, Set<String>> getGraph() {
        return graph;
    }

    public Map<String, Set<String>> getReverseGraph() {
        return reverseGraph;
    }

    public void clearProject() {
        graph.clear();
        reverseGraph.clear();
        classIndex.clear();
    }
}