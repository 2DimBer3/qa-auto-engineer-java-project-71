package hexlet.code;

import hexlet.code.formatters.JsonFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static hexlet.code.Differ.loadFile;
import static hexlet.code.formatters.JsonFormatter.JSON_FORMAT;
import static hexlet.code.formatters.PlainFormatter.PLAIN_FORMAT;
import static hexlet.code.formatters.StylishFormatter.STYLISH_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {

    @ParameterizedTest(name = "{0})")
    @MethodSource("testDiffGenerationWithFormatterTestData")
    void testDiffGenerationWithFormatter(String description, String file1, String file2,
                                         String expectedFile, Formatter formatter) throws IOException {
        Map<String, Object> data1 = loadFile(getFixturePath(file1));
        Map<String, Object> data2 = loadFile(getFixturePath(file2));

        List<DiffEntry> diff = DiffComputer.computeDiff(data1, data2);
        String actual = formatter.format(diff);
        String expected = readFixtureFile(expectedFile);

        assertEquals(normalizeLineEndings(expected), normalizeLineEndings(actual), description);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("testGenerateWithFormatTestData")
    void testGenerateWithFormat(String description, String format,
                                String expectedFile) throws IOException {
        String actual = Differ.generate(
                getFixturePath("file1_test_format.json").toString(),
                getFixturePath("file2_test_format.json").toString(),
                format);
        String expected = readFixtureFile(expectedFile);

        assertEquals(normalizeLineEndings(expected), normalizeLineEndings(actual), description);
    }

    @Test
    void testGenerateDefaultStylish() throws IOException {
        String actual = Differ.generate(getFixturePath("file1_test_format.json").toString(),
                getFixturePath("file2_test_format.json").toString());
        String expected = readFixtureFile("expected_diff_format_stylish.txt");

        assertEquals(normalizeLineEndings(expected), normalizeLineEndings(actual));
    }

    @Test
    void testEmptyFiles() {
        Map<String, Object> data1 = Map.of();
        Map<String, Object> data2 = Map.of();

        List<DiffEntry> diff = DiffComputer.computeDiff(data1, data2);
        String actual = new StylishFormatter().format(diff);
        String expected = "{\n}";

        assertEquals(expected, actual);
    }

    @Test
    void testPlainWithNullAndBoolean() {
        Map<String, Object> data1 = new HashMap<>();
        data1.put("debug", true);
        data1.put("mode", null);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("debug", false);
        data2.put("mode", "full");

        List<DiffEntry> diff = DiffComputer.computeDiff(data1, data2);
        String actual = new PlainFormatter().format(diff);
        String expected = """
                Property 'debug' was updated. From true to false
                Property 'mode' was updated. From null to 'full'""";

        assertEquals(expected, actual);
    }

    @Test
    void testStylishWithComplexValues() {
        Map<String, Object> data1 = Map.of("items", "[1,2]", "config", "{key=val}");
        Map<String, Object> data2 = Map.of("items", "[3,4]", "config", "{key=newVal}");

        List<DiffEntry> diff = DiffComputer.computeDiff(data1, data2);
        String actual = new StylishFormatter().format(diff);
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

    private static Stream<Arguments> testDiffGenerationWithFormatterTestData() {
        return Stream.of(
                Arguments.of("JSON", "file1.json", "file2.json", "expected_diff.txt", new StylishFormatter()),
                Arguments.of("YAML", "file1.yml", "file2.yml", "expected_diff.txt", new StylishFormatter()),
                Arguments.of("SameFiles", "same_file_1.json", "same_file_2.json",
                        "expected_diff_same_file.txt", new StylishFormatter()),
                Arguments.of("StylishFormat", "file1_test_format.json", "file2_test_format.json",
                        "expected_diff_format_stylish.txt", new StylishFormatter()),
                Arguments.of("PlainFormat", "file1_test_format.json", "file2_test_format.json",
                        "expected_diff_format_plain.txt", new PlainFormatter()),
                Arguments.of("JsonFormat", "file1_test_format.json", "file2_test_format.json",
                        "expected_diff_format_json.txt", new JsonFormatter()),
                Arguments.of("StylishFormat: First File Is Empty", "empty.yml", "file2.yml",
                        "expected_diff_format_stylish_first_empty.txt", new StylishFormatter()),
                Arguments.of("JsonFormat: Second File Is Empty", "file1_test_format.json", "empty.json",
                        "expected_diff_format_json_second_empty.txt", new JsonFormatter())
        );
    }

    private static Stream<Arguments> testGenerateWithFormatTestData() {
        return Stream.of(
                Arguments.of("Stylish format", STYLISH_FORMAT, "expected_diff_format_stylish.txt"),
                Arguments.of("Plain format", PLAIN_FORMAT, "expected_diff_format_plain.txt"),
                Arguments.of("JSON format", JSON_FORMAT, "expected_diff_format_json.txt")
        );
    }
}
