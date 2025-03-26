package com.catfe.contextawaretesting;

import com.catfe.contextawaretesting.service.JavaCodeParser;
import com.catfe.contextawaretesting.service.OllamaComm;
import com.catfe.contextawaretesting.service.SymbolicExecutor;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class Runner {
    @Autowired
    OllamaComm ollamaComm;
    @Autowired
    JavaCodeParser javaCodeParser;
    @Autowired
    SymbolicExecutor symbolicExecutor;

    @PostConstruct
    private void testOpenAiApi() throws Exception {
        String utilsFileAbsPath = "/Users/avr/Projects/catfe-404-team-not-found/code/contextawaretesting/src/main/java/com/catfe/contextawaretesting/Utils/FinancialUtils.java";
        List<MethodMetadata> methodMetadataList = javaCodeParser.parse(utilsFileAbsPath);
        String className = utilsFileAbsPath.substring(utilsFileAbsPath.lastIndexOf("/"), utilsFileAbsPath.indexOf(".java") + 1);

        ollamaComm.generateTestClass(methodMetadataList, className);


    }
}
