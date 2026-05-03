package hexlet.code;

import picocli.CommandLine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

    @CommandLine.Parameters(paramLabel = "filepath1", description = "path to first file")
    Path filepath1;
    @CommandLine.Parameters(paramLabel = "filepath2", description = "path to second file")
    Path filepath2;
    @CommandLine.Option(names = {"-f", "--format"},
            description = "output format [default: stylish]",
            defaultValue = "stylish")
    private String format;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        try {
            Map<String, Object> data1 = loadFile(filepath1);
            Map<String, Object> data2 = loadFile(filepath2);

            String diff = Differ.generate(data1, data2);
            System.out.println(diff);

            return 0;

        } catch (IOException e) {
            System.err.println("Error: " + e);
            return 1;
        }
    }

    private Map<String, Object> loadFile(Path userPath) throws IOException {
        // Физический файл
        if (Files.exists(userPath)) {
            return Parser.parseFile(userPath);
        }

        // Поиск в ресурсах
        String fileName = userPath.getFileName().toString();
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (stream == null) {
            throw new NoSuchFileException("File not found: " + userPath + " nor in resources as " + fileName);
        }

        String content = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        return Parser.parseContent(content, fileName);
    }

}
