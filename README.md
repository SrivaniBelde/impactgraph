# ImpactGraph – Code Impact Intelligence Engine

## Overview

ImpactGraph is a Spring Boot application that analyzes Java source code and predicts which files will be affected when a particular file is modified.

It helps developers understand code dependencies and estimate the risk of making changes.

## Features

- Upload a single Java file
- Upload an entire Java project
- Extract dependencies automatically
- Build a dependency graph
- Build a reverse dependency graph
- Perform impact analysis using BFS
- Calculate a risk score
- View the dependency graph in a browser

## Technologies Used

- Java 17
- Spring Boot
- Gradle
- HTML
- REST API

## API Endpoints

### 1. Upload an Entire Project

**Method:** POST

**URL:**

```
http://localhost:8080/upload-project
```

Uploads multiple Java files for dependency analysis.

---

### 2. View Dependency Graph

**Method:** GET

**URL:**

```
http://localhost:8080/graph
```

Returns the dependency graph of the uploaded project.

---

### 3. View Reverse Dependency Graph

**Method:** GET

**URL:**

```
http://localhost:8080/reverse-graph
```

Shows which files depend on a specific file.

---

### 4. Perform Impact Analysis

**Method:** GET

**Example URL:**

```
http://localhost:8080/impact/A.java
```

Returns all files affected if `A.java` is modified.

Example Response:

```json
{
  "file": "A.java",
  "count": 2,
  "impactedFiles": [
    "B.java",
    "C.java"
  ]
}
```

---

### 5. Generate Risk Report

**Method:** GET

**Example URL:**

```
http://localhost:8080/report/A.java
```

Calculates the risk score of modifying `A.java`.

Example Response:

```json
{
  "file": "A.java",
  "riskScore": 6,
  "impactLevel": "MEDIUM",
  "impactedFiles": [
    "B.java",
    "C.java"
  ],
  "impactedFileCount": 2
}
```

## Project Workflow

1. Upload Java files.
2. Extract dependencies.
3. Build a dependency graph.
4. Analyze impacted files using BFS.
5. Calculate the risk score.
6. Display the results through REST APIs.

## Future Enhancements

- Java AST Parsing
- Interactive Graph Visualization
- Project Metrics Dashboard
- Risk Heatmaps
- Multi-module Project Support

## Developed By

**Srivani Belde**
