package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parseContent(String content, String format) throws IOException {
        if (content == null || content.isBlank()) {
            return new LinkedHashMap<>();
        }

        if ("json".equalsIgnoreCase(format)) {
            return parseJson(content);
        } else if ("yml".equalsIgnoreCase(format) || "yaml".equalsIgnoreCase(format)) {
            return parseYaml(content);
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + format);
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
