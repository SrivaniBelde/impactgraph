package com.impactgraph.impactgraph.controller;

import com.impactgraph.impactgraph.service.DependencyExtractor;
import com.impactgraph.impactgraph.service.GraphService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
public class UploadController {

    private final DependencyExtractor extractor;
    private final GraphService graphService;

    public UploadController(DependencyExtractor extractor,
                            GraphService graphService) {
        this.extractor = extractor;
        this.graphService = graphService;
    }

    // -----------------------------
    // Upload Single File
    // -----------------------------
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) throws Exception {

        graphService.clearProject();

        String code = new String(file.getBytes());
        String fileName = file.getOriginalFilename();
        String className = fileName.replace(".java", "");

        // Register class first
        graphService.registerClass(className, fileName);

        List<String> rawDeps = extractor.extractDependencies(code);
        List<String> resolvedDeps = resolveDependencies(rawDeps);

        graphService.addFile(fileName, resolvedDeps);

        Map<String, Object> response = new HashMap<>();
        response.put("fileName", fileName);
        response.put("message", "Upload + Dependency extraction successful");
        response.put("rawDependencies", rawDeps);
        response.put("resolvedDependencies", resolvedDeps);

        return response;
    }

    // -----------------------------
    // Upload Multiple Files
    // -----------------------------
    @PostMapping(value = "/upload-project", consumes = "multipart/form-data")
    public Map<String, Object> uploadProject(@RequestParam("files") MultipartFile[] files) throws Exception {

        graphService.clearProject();

        List<String> uploadedFiles = new ArrayList<>();
        Map<String, List<String>> rawDependencyMap = new LinkedHashMap<>();

        // STEP 1: register ALL classes first
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String className = fileName.replace(".java", "");

            graphService.registerClass(className, fileName);
            uploadedFiles.add(fileName);
        }

        // DEBUG: verify class index
        System.out.println("CLASS INDEX = " + graphService.getClassIndex());

        // STEP 2: extract raw dependencies for every file
        for (MultipartFile file : files) {
            String code = new String(file.getBytes());
            String fileName = file.getOriginalFilename();

            List<String> rawDeps = extractor.extractDependencies(code);
            rawDependencyMap.put(fileName, rawDeps);
        }

        // STEP 3: resolve raw dependencies -> file dependencies
        for (Map.Entry<String, List<String>> entry : rawDependencyMap.entrySet()) {
            String fileName = entry.getKey();
            List<String> rawDeps = entry.getValue();

            List<String> resolvedDeps = resolveDependencies(rawDeps);

            System.out.println("FILE = " + fileName);
            System.out.println("RAW  = " + rawDeps);
            System.out.println("RESOLVED = " + resolvedDeps);

            graphService.addFile(fileName, resolvedDeps);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Project uploaded successfully");
        response.put("totalFiles", uploadedFiles.size());
        response.put("uploadedFiles", uploadedFiles);

        return response;
    }

    // -----------------------------
    // Convert class dependency names
    // Example: B -> B.java
    // Keep library/method dependencies unchanged
    // -----------------------------
    private List<String> resolveDependencies(List<String> rawDeps) {
        List<String> resolved = new ArrayList<>();

        for (String dep : rawDeps) {
            String mappedFile = graphService.getFileByClass(dep);

            if (mappedFile != null) {
                resolved.add(mappedFile);
            } else {
                resolved.add(dep);
            }
        }

        return resolved;
    }
}