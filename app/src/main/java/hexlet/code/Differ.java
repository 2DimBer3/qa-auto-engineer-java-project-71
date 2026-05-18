package hexlet.code;

import hexlet.code.diffentry.DiffEntry;
import hexlet.code.formatters.Formatter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static hexlet.code.FileUtils.loadFile;
import static hexlet.code.formatters.StylishFormatter.STYLISH_FORMAT;

public class Differ {
    public static String generate(String filepath1, String filepath2, String format) throws IOException {
        Map<String, Object> data1 = loadFile(Path.of(filepath1));
        Map<String, Object> data2 = loadFile(Path.of(filepath2));
        List<DiffEntry> diff = DiffComputer.computeDiff(data1, data2);
        return Formatter.getFormatter(format).format(diff);
    }

    public static String generate(String filepath1, String filepath2) throws IOException {
        return generate(filepath1, filepath2, STYLISH_FORMAT);
    }
}
