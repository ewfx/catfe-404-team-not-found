package com.catfe.contextawaretesting.service;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.Type;
import org.springframework.stereotype.Service;
import com.catfe.contextawaretesting.MethodMetadata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class JavaCodeParser {

    public List<MethodMetadata> parse(String absFilePath) throws Exception {
        File file = new File(absFilePath);
        JavaParser jp = new JavaParser();
        var compilationUnit = jp.parse(file).getResult();

        List<MethodMetadata> methods = new ArrayList<>();
        compilationUnit.ifPresent(cu -> {

            String className = cu.getType(0).getNameAsString();
            System.out.println("Class: " + className);


            // Extract method metadata
            cu.findAll(MethodDeclaration.class).forEach(method -> {
                MethodMetadata metadata = new MethodMetadata();
                metadata.name = method.getNameAsString();
                metadata.returnType = method.getType();

                // Parameters
                method.getParameters().forEach(param -> {
                    metadata.parameters.add(param.getName() + " (" + param.getType() + ")");
                });

                // Conditions (if-else)
                method.findAll(IfStmt.class).forEach(ifStmt -> {
                    metadata.conditions.add(ifStmt.getCondition().toString());
                });

                // Loops (for, while)
                method.findAll(ForStmt.class).forEach(forStmt -> {
                    metadata.loops.add(forStmt.toString());
                });
                method.findAll(WhileStmt.class).forEach(whileStmt -> {
                    metadata.loops.add(whileStmt.toString());
                });

                // Try-catch handling
                method.findAll(TryStmt.class).forEach(tryStmt -> {
                    tryStmt.getCatchClauses().forEach(catchClause -> {
                        metadata.thrownExceptions.add(catchClause.getParameter().getType().toString());
                    });
                });

                // Internal calls (to other methods in the same class)
                method.findAll(MethodCallExpr.class).forEach(callExpr -> {
                    if (callExpr.getScope().isEmpty()) { // Internal calls only
                        metadata.internalCalls.add(callExpr.getNameAsString());
                        if (callExpr.getNameAsString().equals(method.getNameAsString())) {
                            metadata.isRecursive = true;
                        }
                    }
                });
                method.findAll(SwitchStmt.class).forEach(switchStmt -> {
                    metadata.conditions.add("Switch on: " + switchStmt.getSelector());
                });
                method.findAll(IfStmt.class).forEach(ifStmt -> {
                    ifStmt.getElseStmt().ifPresent(elseStmt -> {
                        metadata.conditions.add("Has ELSE branch");
                    });
                });

                methods.add(metadata);
            });

            // Print out metadata
            methods.forEach(System.out::println);
        });
        return methods;
        // Extract class name
    }
}
