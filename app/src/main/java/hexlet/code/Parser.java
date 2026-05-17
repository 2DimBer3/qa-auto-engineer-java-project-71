package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parseContent(String content, String fileName) throws IOException {
        if (content == null || content.isBlank()) {
            return new LinkedHashMap<>();
        }

        String lowerName = fileName.toLowerCase();
        if (lowerName.endsWith(".json")) {
            return parseJson(content);
        } else if (lowerName.endsWith(".yml") || lowerName.endsWith(".yaml")) {
            return parseYaml(content);
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + fileName);
        }
    }

    private static Map<String, Object> parseJson(String content) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<>() { });
    }

    private static Map<String, Object> parseYaml(String content) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(content, new TypeReference<>() { });
    }

}
