package com.catfe.contextawaretesting;

import com.catfe.contextawaretesting.service.JavaCodeParser;
import com.catfe.contextawaretesting.service.OllamaComm;
import com.catfe.contextawaretesting.service.SymbolicExecutor;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.catfe.contextawaretesting.Const.WATCHED_FILE_DIR_PATH;


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
        String utilsFileAbsPath = WATCHED_FILE_DIR_PATH;
        List<MethodMetadata> methodMetadataList = javaCodeParser.parse(utilsFileAbsPath);
        String className = utilsFileAbsPath.substring(utilsFileAbsPath.lastIndexOf("/") + 1, utilsFileAbsPath.indexOf(".java"));

        List<String> testClasses = ollamaComm.generateTestClass(methodMetadataList, className);
        ClassMerger.mergeClasses(testClasses, "com.catfe.contextawaretesting.Utils", className + "Test");
    }
}
