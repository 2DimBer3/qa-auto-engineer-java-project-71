package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parseContent(String content, String fileName) throws IOException {
        String lowerName = fileName.toLowerCase();
        if (lowerName.endsWith(".json")) {
            return parseJson(content);
        } else if (lowerName.endsWith(".yml") || lowerName.endsWith(".yaml")) {
            return parseYaml(content);
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + fileName);
        }
    }

    public static Map<String, Object> parseFile(Path filePath) throws IOException {
        String content = Files.readString(filePath);
        String fileName = filePath.getFileName().toString();
        return parseContent(content, fileName);
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
