package com.catfe.contextawaretesting;

import com.github.javaparser.ast.type.Type;

import java.util.ArrayList;
import java.util.List;

public class MethodMetadata {
    public MethodMetadata() {}
    public String name;
    public Type returnType;
    public List<String> parameters = new ArrayList<>();
    public List<String> conditions = new ArrayList<>();
    public List<String> loops = new ArrayList<>();
    public List<String> internalCalls = new ArrayList<>();
    public List<String> thrownExceptions = new ArrayList<>();
    public boolean isRecursive = false;

    @Override
    public String toString() {
        return String.format("""
                Method: %s
                Return Type: %s
                Parameters: %s
                Conditions: %s
                Loops: %s
                Internal Calls: %s
                Thrown Exceptions: %s
                Is Recursive: %s
                ------------------------
                """, name, returnType, parameters, conditions, loops, internalCalls, thrownExceptions, isRecursive);
    }
}