package com.catfe.contextawaretesting.service;

import com.catfe.contextawaretesting.MethodMetadata;
import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.types.OllamaModelType;
import io.github.ollama4j.utils.OptionsBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class OllamaComm {
    OllamaAPI ollamaAPI;
    public OllamaComm() throws RuntimeException {
        // Create an OllamaAPI instance with the timeout
        ollamaAPI = new OllamaAPI();
        ollamaAPI.setRequestTimeoutSeconds(60);
        boolean isOllamaReachable = ollamaAPI.ping();
        if(!isOllamaReachable)
            throw new RuntimeException();
    }

    public void generateTestClass(List<MethodMetadata> methodMetadataList, String className) throws OllamaBaseException, IOException, InterruptedException {
        String imports = "", testClassName, tests = "";
        testClassName = className + "Test";
        String testClassStructure = String.format("""
                %s
                public class %s {
                    %s
                }
                """, imports, testClassName, tests );


        for (MethodMetadata methodMetadata : methodMetadataList) {
            String methodName = methodMetadata.name;
            String prompt = String.format("""
                    create all necessary junits for the method named '%s' that: 
                    - has the following metadata:
                        %s
                    - make sure the junit is appropriately named for the scenario it is testing
                    - also make sure that all possible edge cases are covered
                    - return only the junits without any comments and clarifications
                    """, methodName, methodMetadata);

            generateResultFromPrompt(prompt);
        }
    }

    public void generateResultFromPrompt(String prompt) throws OllamaBaseException, IOException, InterruptedException {
        OllamaResult ollamaResult = this.ollamaAPI.generate(OllamaModelType.CODELLAMA, prompt, Boolean.TRUE, new OptionsBuilder().build());
        System.out.println(ollamaResult.getResponse());
    }
}
