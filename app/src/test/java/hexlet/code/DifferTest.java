package hexlet.code;

import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {

    @Test
    public void testGenerateFromJson() throws IOException {
        Map<String, Object> data1 = Parser.parseFile(getFixturePath("file1.json"));
        Map<String, Object> data2 = Parser.parseFile(getFixturePath("file2.json"));

        List<DiffEntry> diff = Differ.computeDiff(data1, data2);
        String actual = new StylishFormatter().format(diff);
        String expected = readFixtureFile("expected_diff.txt");

        assertEquals(normalizeLineEndings(expected), actual);
    }

    @Test
    public void testGenerateFromYaml() throws IOException {
        Map<String, Object> data1 = Parser.parseFile(getFixturePath("file1.yml"));
        Map<String, Object> data2 = Parser.parseFile(getFixturePath("file2.yml"));

        List<DiffEntry> diff = Differ.computeDiff(data1, data2);
        String actual = new StylishFormatter().format(diff);
        String expected = readFixtureFile("expected_diff.txt");

        assertEquals(normalizeLineEndings(expected), actual);
    }

    @Test
    public void testEmptyFiles() {
        Map<String, Object> data1 = Map.of();
        Map<String, Object> data2 = Map.of();

        List<DiffEntry> diff = Differ.computeDiff(data1, data2);
        String actual = new StylishFormatter().format(diff);
        String expected = "{\n}";

        assertEquals(expected, actual);
    }

    @Test
    public void testSameFiles() throws IOException {
        Map<String, Object> data1 = Parser.parseFile(getFixturePath("same_file_1.json"));
        Map<String, Object> data2 = Parser.parseFile(getFixturePath("same_file_2.json"));

        List<DiffEntry> diff = Differ.computeDiff(data1, data2);
        String actual = new StylishFormatter().format(diff);
        String expected = readFixtureFile("expected_diff_same_file.txt");

        assertEquals(normalizeLineEndings(expected), actual);
    }

    @Test
    public void testStylishFormat() throws IOException {
        Map<String, Object> data1 = Parser.parseFile(getFixturePath("file1_test_format.json"));
        Map<String, Object> data2 = Parser.parseFile(getFixturePath("file2_test_format.json"));

        List<DiffEntry> diff = Differ.computeDiff(data1, data2);
        String actual = new StylishFormatter().format(diff);
        String expected = readFixtureFile("expected_diff_stylish.txt");

        assertEquals(normalizeLineEndings(expected), actual);
    }

    @Test
    public void testPlainFormat() throws IOException {
        Map<String, Object> data1 = Parser.parseFile(getFixturePath("file1_test_format.json"));
        Map<String, Object> data2 = Parser.parseFile(getFixturePath("file2_test_format.json"));

        List<DiffEntry> diff = Differ.computeDiff(data1, data2);
        String actual = new PlainFormatter().format(diff);
        String expected = readFixtureFile("expected_diff_plain.txt");

        assertEquals(normalizeLineEndings(expected), actual);
    }

    @Test
    public void testGenerateStylish() {
        Map<String, Object> data1 = Map.of("a", 1, "b", "text");
        Map<String, Object> data2 = Map.of("a", 1, "b", "new_text");

        String actual = Differ.generate(data1, data2, "stylish");
        String expected = "{\n    a: 1\n  - b: text\n  + b: new_text\n}";

        assertEquals(expected, actual);
    }

    @Test
    public void testGeneratePlain() {
        Map<String, Object> data1 = Map.of("host", "hexlet.io", "timeout", 50);
        Map<String, Object> data2 = Map.of("host", "hexlet.ru", "timeout", 20, "verbose", true);

        String actual = Differ.generate(data1, data2, "plain");
        String expected = """
                Property 'host' was updated. From 'hexlet.io' to 'hexlet.ru'
                Property 'timeout' was updated. From 50 to 20
                Property 'verbose' was added with value: true""";
        assertEquals(expected, actual);
    }

    @Test
    public void testPlainWithNullAndBoolean() {
        Map<String, Object> data1 = new HashMap<>();
        data1.put("debug", true);
        data1.put("mode", null);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("debug", false);
        data2.put("mode", "full");

        String actual = Differ.generate(data1, data2, "plain");
        String expected = """
                Property 'debug' was updated. From true to false
                Property 'mode' was updated. From null to 'full'""";

        assertEquals(expected, actual);
    }

    @Test
    public void testStylishWithComplexValues() {
        Map<String, Object> data1 = Map.of("items", "[1,2]", "config", "{key=val}");
        Map<String, Object> data2 = Map.of("items", "[3,4]", "config", "{key=newVal}");

        String actual = Differ.generate(data1, data2, "stylish");
        String expected = """
                {
                  - config: {key=val}
                  + config: {key=newVal}
                  - items: [1,2]
                  + items: [3,4]
                }""";

        assertEquals(expected, actual);
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
