package hexlet.code;

import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

import java.util.List;

import static hexlet.code.formatters.PlainFormatter.PLAIN_FORMAT;
import static hexlet.code.formatters.StylishFormatter.STYLISH_FORMAT;

public interface Formatter {

    String format(List<DiffEntry> diff);

    static Formatter getFormatter(String formatName) {
        if (STYLISH_FORMAT.equalsIgnoreCase(formatName)) {
            return new StylishFormatter();
        } else if (PLAIN_FORMAT.equalsIgnoreCase(formatName)) {
            return new PlainFormatter();
        }

        throw new IllegalArgumentException("Unknown format: " + formatName);
    }
}
