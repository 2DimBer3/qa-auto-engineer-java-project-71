package hexlet.code;

import picocli.CommandLine;

import java.io.File;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Runnable {

    @CommandLine.Option(names = {"-f", "--format"},
            description = "output format [default: stylish]",
            defaultValue = "stylish")
    private String format;

    @CommandLine.Parameters(paramLabel = "filepath1", description = "path to first file")
    File filepath1;

    @CommandLine.Parameters(paramLabel = "filepath2", description = "path to second file")
    File filepath2;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Hello, World!");
    }
}