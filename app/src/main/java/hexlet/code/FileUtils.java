package hexlet.code;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Map;

public class FileUtils {
    public static Map<String, Object> loadFile(Path userPath) throws IOException {
        String content;

        if (Files.exists(userPath)) {
            // 1. Чтение физического файла
            content = Files.readString(userPath);
        } else {
            // 2. Поиск файла в ресурсах
            String fileName = userPath.getFileName().toString();
            InputStream stream = App.class.getClassLoader().getResourceAsStream(fileName);
            if (stream == null) {
                throw new NoSuchFileException("File not found: " + userPath + " nor in resources as " + fileName);
            }
            content = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }

        return Parser.parseContent(content, getFormat(userPath));
    }

    private static String getFormat(Path filePath) {
        String fileName = filePath.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        throw new IllegalArgumentException("Cannot determine file format for: " + fileName);
    }
}
