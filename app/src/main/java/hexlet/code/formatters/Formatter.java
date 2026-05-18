package hexlet.code.formatters;

import hexlet.code.DiffEntry;

import java.util.List;

import static hexlet.code.formatters.JsonFormatter.JSON_FORMAT;
import static hexlet.code.formatters.PlainFormatter.PLAIN_FORMAT;
import static hexlet.code.formatters.StylishFormatter.STYLISH_FORMAT;

public interface Formatter {

    String format(List<DiffEntry> diff);

    static Formatter getFormatter(String formatName) {
        switch (formatName) {
            case STYLISH_FORMAT -> {
                return new StylishFormatter();
            }
            case PLAIN_FORMAT -> {
                return new PlainFormatter();
            }
            case JSON_FORMAT -> {
                return new JsonFormatter();
            }
            default -> throw new IllegalArgumentException("Unknown format: " + formatName);
        }
    }
}
