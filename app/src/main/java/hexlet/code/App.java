package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Runnable {

    @CommandLine.Option(names = {"-f", "--format"},
            description = "output format [default: stylish]",
            defaultValue = "stylish")
    private String format;

    @CommandLine.Parameters(paramLabel = "filepath1", description = "path to first file")
    Path filepath1;

    @CommandLine.Parameters(paramLabel = "filepath2", description = "path to second file")
    Path filepath2;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            Map<String, Object> data1 = parseFile(filepath1);
            Map<String, Object> data2 = parseFile(filepath2);

            System.out.println("File 1 data: " + data1);
            System.out.println("File 2 data: " + data2);

        } catch (Exception e) {
            System.err.println("Error: " + e);
            System.exit(1);
        }
    }

    private Map<String, Object> parseFile(Path filePath) throws Exception {
        String content = readFile(filePath);
        return parseJson(content);
    }

    private String readFile(Path filePath) throws Exception {
        return Files.readString(filePath);
    }

    private Map<String, Object> parseJson(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<>() {});
    }
}