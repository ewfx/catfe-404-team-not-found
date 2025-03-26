package com.catfe.contextawaretesting.service;

import com.catfe.contextawaretesting.MethodMetadata;
import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.types.OllamaModelType;
import io.github.ollama4j.utils.OptionsBuilder;
import org.jsoup.internal.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OllamaComm {
    OllamaAPI ollamaAPI;
    public OllamaComm() throws RuntimeException {
        // Create an OllamaAPI instance with the timeout
        ollamaAPI = new OllamaAPI();
        ollamaAPI.setRequestTimeoutSeconds(600);
        boolean isOllamaReachable = ollamaAPI.ping();
        if(!isOllamaReachable)
            throw new RuntimeException();
    }

    public List<String> generateTestClass(List<MethodMetadata> methodMetadataList, String className) throws OllamaBaseException, IOException, InterruptedException {
        List<String> testClasses = new ArrayList<>();

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
                    Generate comprehensive JUnit tests for the %s method with the following metadata:
                    - Metadata: %s
                    - Ensure the test names clearly reflect the scenarios being tested.
                    - Cover all possible edge cases, including boundary conditions and invalid inputs.
                    - Return only the Java unit tests without any comments, explanations, or extra text.
                    """, methodName, methodMetadata);
            System.out.println("generating tests for: " + methodName);
            testClasses.add(generateResultFromPrompt(prompt));
        }
        return testClasses;
    }

    public String generateResultFromPrompt(String prompt) throws OllamaBaseException, IOException, InterruptedException {
        OllamaResult ollamaResult = this.ollamaAPI.generate(OllamaModelType.DEEPSEEK_R1, prompt, Boolean.TRUE, new OptionsBuilder().build());
        return getJavaClassFromDeepseekResponse(ollamaResult.getResponse());
    }

    private String getJavaClassFromDeepseekResponse(String deepseekResponse) {
        Pattern pattern = Pattern.compile("(?s)(?<=```java\\n)(.*?)(?=\\n```)");
        Matcher matcher = pattern.matcher(deepseekResponse);

        if(matcher.find()){
            return matcher.group(1);
        }
        return null;
    }
}
