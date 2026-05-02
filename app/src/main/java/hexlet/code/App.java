package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

    @CommandLine.Option(names = {"-f", "--format"},
            description = "output format [default: stylish]",
            defaultValue = "stylish")
    private String format;

    @CommandLine.Parameters(paramLabel = "filepath1", description = "path to first file")
    String filepath1;

    @CommandLine.Parameters(paramLabel = "filepath2", description = "path to second file")
    String filepath2;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        try {
            Map<String, Object> data1 = parseFile(filepath1);
            Map<String, Object> data2 = parseFile(filepath2);

            String diff = Differ.generate(data1, data2);
            System.out.println(diff);

            return 0;

        } catch (IOException e) {
            System.err.println("Error: " + e);
            return 1;
        }
    }

    private Map<String, Object> parseFile(String filePath) throws IOException {
        String content = readFile(filePath);
        return parseJson(content);
    }

    private String readFile(String filePath) throws IOException {
        String fileName = Paths.get(filePath).getFileName().toString();
        Path resourcePath = Paths.get("src", "main", "resources", fileName)
                .toAbsolutePath()
                .normalize();

        if (!Files.exists(resourcePath)) {
            throw new IOException("File not found: " + resourcePath);
        }

        return Files.readString(resourcePath);
    }

    private Map<String, Object> parseJson(String content) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<>() { });
    }
}
