package com.impactgraph.impactgraph.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DependencyExtractor {

    public List<String> extractDependencies(String code) {

        Set<String> dependencies = new HashSet<>();

        // -----------------------------
        // 1. Extract Imports
        // -----------------------------
        Pattern importPattern =
                Pattern.compile("import\\s+([\\w\\.]+);");

        Matcher importMatcher = importPattern.matcher(code);

        while (importMatcher.find()) {

            String fullImport = importMatcher.group(1);

            String className =
                    fullImport.substring(fullImport.lastIndexOf('.') + 1);

            dependencies.add(className);
        }

        // -----------------------------
        // 2. Extract Object Creation
        // Example:
        // new PaymentService()
        // -----------------------------
        Pattern newPattern =
                Pattern.compile("new\\s+([A-Z][A-Za-z0-9_]*)\\s*\\(");

        Matcher newMatcher = newPattern.matcher(code);

        while (newMatcher.find()) {

            dependencies.add(newMatcher.group(1));
        }

        // -----------------------------
        // 3. Extract Variable Declarations
        // Example:
        // PaymentService service;
        // -----------------------------
        Pattern classPattern =
                Pattern.compile("\\b([A-Z][A-Za-z0-9_]*)\\s+[a-zA-Z_][A-Za-z0-9_]*");

        Matcher classMatcher = classPattern.matcher(code);

        while (classMatcher.find()) {

            String className = classMatcher.group(1);

            if (!isJavaClass(className)) {
                dependencies.add(className);
            }
        }

        // -----------------------------
        // 4. Extract Static Calls
        // Example:
        // Arrays.sort()
        // Collections.sort()
        // -----------------------------
        Pattern staticPattern =
                Pattern.compile("\\b([A-Z][A-Za-z0-9_]*)\\.([a-zA-Z_][A-Za-z0-9_]*)\\(");

        Matcher staticMatcher = staticPattern.matcher(code);

        while (staticMatcher.find()) {

            String className = staticMatcher.group(1);

            if (!isJavaClass(className)) {
                dependencies.add(className);
            }
        }

        // -----------------------------
        // 5. Extract Method Calls
        // -----------------------------
        Pattern methodPattern =
                Pattern.compile("\\b[a-z][A-Za-z0-9_]*\\.([a-zA-Z0-9_]+)\\s*\\(");

        Matcher methodMatcher = methodPattern.matcher(code);

        while (methodMatcher.find()) {

            dependencies.add("METHOD:" + methodMatcher.group(1));
        }

        return new ArrayList<>(dependencies);
    }

    private boolean isJavaClass(String className) {

        return Set.of(
                "String",
                "Integer",
                "Long",
                "Double",
                "Float",
                "Boolean",
                "Character",
                "Byte",
                "Short",
                "Object",
                "System",
                "Arrays",
                "Collections",
                "List",
                "ArrayList",
                "LinkedList",
                "HashMap",
                "HashSet",
                "Map",
                "Set",
                "Queue",
                "Stack",
                "Scanner",
                "Math"
        ).contains(className);
    }
}