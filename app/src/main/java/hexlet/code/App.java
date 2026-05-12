package hexlet.code;

import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<Integer> {

    @CommandLine.Parameters(paramLabel = "filepath1", description = "path to first file")
    @SuppressWarnings("unused")
    private String filepath1;

    @CommandLine.Parameters(paramLabel = "filepath2", description = "path to second file")
    @SuppressWarnings("unused")
    private String filepath2;

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
            String diff = Differ.generate(filepath1, filepath2, format);
            System.out.println(diff);

            return 0;

        } catch (IOException e) {
            System.err.println("Error: " + e);
            return 1;
        }
    }

}
