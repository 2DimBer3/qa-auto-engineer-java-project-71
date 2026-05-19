package hexlet.code;

import hexlet.code.diffentry.DiffEntry;
import hexlet.code.formatters.Formatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvSource(delimiter = '|', textBlock = """
             JSON                 | stylish | file1.json             | file2.json       | expected_diff.txt
             YAML                 | stylish | file1.yml              | file2.yml        | expected_diff.txt
             SameFiles            | stylish | same_file_1.json       | same_file_2.json | expected_diff_same_file.txt
             First File Is Empty  | stylish | empty.yml              | file2.yml        | expected_diff_format_stylish_first_empty.txt
             Second File Is Empty | json    | file1_test_format.json | empty.json       | expected_diff_format_json_second_empty.txt
            """)
    void testDiffFiles(String description, String format, String file1, String file2, String expectedFile) throws Exception {
        Formatter formatter = Formatter.getFormatter(format);
        Map<String, Object> data1 = FileUtils.loadFile(getFixturePath(file1));
        Map<String, Object> data2 = FileUtils.loadFile(getFixturePath(file2));
        List<DiffEntry> diff = DiffComputer.computeDiff(data1, data2);

        String actual = formatter.format(diff);
        String expected = readFixtureFile(expectedFile);

        assertEquals(normalizeLineEndings(expected), normalizeLineEndings(actual), description);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvSource(delimiter = '|', textBlock = """
            Stylish format | stylish | expected_diff_format_stylish.txt
            Plain format   | plain   | expected_diff_format_plain.txt
            JSON format    | json    | expected_diff_format_json.txt
            """)
    void testGenerateWithFormat(String description, String format, String expectedFile) throws IOException {
        String actual = Differ.generate(getFixturePath("file1_test_format.json").toString(), getFixturePath("file2_test_format.json").toString(), format);

        String expected = readFixtureFile(expectedFile);
        assertEquals(normalizeLineEndings(expected), normalizeLineEndings(actual), description);
    }

    @Test
    void testGenerateDefaultStylish() throws IOException {
        String actual = Differ.generate(getFixturePath("file1_test_format.json").toString(), getFixturePath("file2_test_format.json").toString());
        String expected = readFixtureFile("expected_diff_format_stylish.txt");

        assertEquals(normalizeLineEndings(expected), normalizeLineEndings(actual));
    }

    @Test
    void testEmptyFiles() throws IOException {
        String actual = Differ.generate(getFixturePath("empty.json").toString(), getFixturePath("empty.json").toString(), "stylish");
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
        return Paths.get("src", "test", "resources", "fixtures", fileName).toAbsolutePath().normalize();
    }

    private static String normalizeLineEndings(String s) {
        return s.replace("\r\n", "\n").replace("\r", "\n");
    }
}
