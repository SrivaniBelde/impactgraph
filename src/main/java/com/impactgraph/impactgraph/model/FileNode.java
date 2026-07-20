package com.impactgraph.impactgraph.model;

import java.util.*;

public class FileNode {

    private String fileName;
    private Set<String> dependencies = new HashSet<>();

    public FileNode(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public Set<String> getDependencies() {
        return dependencies;
    }

    public void addDependency(String dep) {
        dependencies.add(dep);
    }
}