package hexlet.code;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

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

    public static Map<String, Object> loadFile(Path userPath) throws IOException {
        String fileName = userPath.getFileName().toString();
        String content;

        if (Files.exists(userPath)) {
            // 1. Чтение физического файла
            content = Files.readString(userPath);
        } else {
            // 2. Поиск файла в ресурсах
            InputStream stream = App.class.getClassLoader().getResourceAsStream(fileName);
            if (stream == null) {
                throw new NoSuchFileException("File not found: " + userPath + " nor in resources as " + fileName);
            }
            content = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }

        return Parser.parseContent(content, fileName);
    }
}
