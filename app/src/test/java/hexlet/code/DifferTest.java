package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {

    @Test
    public void testGenerateFromJson() throws IOException {
        Map<String, Object> data1 = Parser.parseFile(getFixturePath("file1.json"));
        Map<String, Object> data2 = Parser.parseFile(getFixturePath("file2.json"));

        String actual = Differ.generate(data1, data2);
        String expected = readFixtureFile("expected_diff.txt");

        assertEquals(normalizeLineEndings(expected), actual);
    }

    @Test
    public void testGenerateFromYaml() throws Exception {
        Map<String, Object> data1 = Parser.parseFile(getFixturePath("file1.yml"));
        Map<String, Object> data2 = Parser.parseFile(getFixturePath("file2.yml"));

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
    public void testSameFiles() throws IOException {
        Map<String, Object> data1 = Parser.parseFile(getFixturePath("same_file_1.json"));
        Map<String, Object> data2 = Parser.parseFile(getFixturePath("same_file_2.json"));

        String actual = Differ.generate(data1, data2);
        String expected = readFixtureFile("expected_diff_same_file.txt");

        assertEquals(normalizeLineEndings(expected), actual);
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
