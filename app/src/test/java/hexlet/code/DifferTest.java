package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class DifferTest {

    @Test
    public void testGenerate() throws IOException {
        Map<String, Object> data1 = readJsonFile("file1.json");
        Map<String, Object> data2 = readJsonFile("file2.json");

        String actual = Differ.generate(data1, data2);
        String expected = readFixtureFile("expected_diff.txt");

        assertEquals(normalizeLineEndings(expected), actual);
    }

    @Test
    public void testEmptyFiles() {
        Map<String, Object> data1 = Map.of();
        Map<String, Object> data2 = Map.of();

        String actual = Differ.generate(data1, data2);
        String expected = "{\n}";

        assertEquals(expected, actual);
    }

    @Test
    public void testSameFiles() {
        Map<String, Object> data1 = Map.of("host", "hexlet.io", "timeout", 50);
        Map<String, Object> data2 = Map.of("host", "hexlet.io", "timeout", 50);

        String actual = Differ.generate(data1, data2);
        String expected = "{\n    host: hexlet.io\n    timeout: 50\n}";

        assertEquals(expected, actual);
    }

    private Map<String, Object> readJsonFile(String fileName) throws IOException {
        String content = readFixtureFile(fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<>() { });
    }

    private static String readFixtureFile(String fileName) throws IOException {
        Path path = getFixturePath(fileName);
        return Files.readString(path).trim();
    }

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    private static String normalizeLineEndings(String s) {
        return s.replace("\r\n", "\n").replace("\r", "\n");
    }

}
