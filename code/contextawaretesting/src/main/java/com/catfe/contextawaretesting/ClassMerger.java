package com.catfe.contextawaretesting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassMerger {

//    public static void main(String[] args) throws IOException {
//        String class1 = """
//            package com.example.utils;
//
//            import java.math.BigDecimal;
//            import org.jetbrains.annotations.NotNull;
//
//            public class Calculator {
//
//                @NotNull
//                @Deprecated
//                public BigDecimal add(BigDecimal a, BigDecimal b) {
//                    BigDecimal result = a.add(b);
//                    System.out.println("Adding " + a + " and " + b);
//                    return result;
//                }
//            }
//            """;
//
//        String class2 = """
//            package com.example.utils;
//
//            import java.text.DecimalFormat;
//
//            public class Formatter {
//
//                @Override
//                public String formatCurrency(double value) {
//                    DecimalFormat df = new DecimalFormat("$#.00");
//                    return df.format(value);
//                }
//            }
//            """;
//
//        mergeClasses(new String[]{class1, class2}, "com.example.utils", "Utility");
//    }

    public static void mergeClasses(List<String> classStrings, String targetPackage, String targetClassName) throws IOException {
        Set<String> imports = new HashSet<>();
        StringBuilder methods = new StringBuilder();

        for (String classString : classStrings) {
            // Collect imports
            collectImports(classString, imports);

            // Extract methods with annotations
            methods.append(extractFullMethods(classString));
        }

        // Generate consolidated class
        String classContent = generateClass(targetPackage, targetClassName, imports, methods.toString());

        // Write to file
        writeToFile(targetPackage, targetClassName, classContent);
    }

    private static void collectImports(String classString, Set<String> imports) {
        // Regex to find imports
        Pattern importPattern = Pattern.compile("import\\s+([a-zA-Z0-9_.]+);");
        Matcher matcher = importPattern.matcher(classString);
        while (matcher.find()) {
            imports.add("import " + matcher.group(1) + ";");
        }
    }

    private static String extractFullMethods(String classString) {
        StringBuilder methods = new StringBuilder();

        // Regex to match the entire method including annotations and body
        Pattern methodPattern = Pattern.compile(
                "(?s)(@\\w+(\\(.*?\\))?\\s*)*(public|protected)\\s+[\\w<>\\[\\]]+\\s+\\w+\\s*\\(.*?\\)\\s*\\{.*?\\}"
        );

        Matcher matcher = methodPattern.matcher(classString);
        while (matcher.find()) {
            String method = matcher.group(); // Extract the full method
            methods.append(method).append("\n\n");
        }

        return methods.toString();
    }

    private static String generateClass(String targetPackage, String targetClassName, Set<String> imports, String methods) {
        StringBuilder sb = new StringBuilder();

        // Package
        sb.append("package ").append(targetPackage).append(";\n\n");

        // Imports
        for (String imp : imports) {
            sb.append(imp).append("\n");
        }

        sb.append("\n");

        // Class definition
        sb.append("public class ").append(targetClassName).append(" {\n\n");

        // Methods
        sb.append(methods);

        sb.append("}\n");

        return sb.toString();
    }

    private static void writeToFile(String targetPackage, String targetClassName, String content) throws IOException {
        // Define file path
        String path = "src/test/java/" + targetPackage.replace('.', '/') + "/" + targetClassName + ".java";
        File file = new File(path);
        file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
            System.out.println("Class generated at: " + path);
        }
    }
}