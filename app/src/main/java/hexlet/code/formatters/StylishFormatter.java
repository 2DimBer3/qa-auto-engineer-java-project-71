package hexlet.code.formatters;

import hexlet.code.diffentry.DiffEntry;

import java.util.List;

import static hexlet.code.diffentry.DiffEntryFields.KEY;
import static hexlet.code.diffentry.DiffEntryFields.NEW_VALUE;
import static hexlet.code.diffentry.DiffEntryFields.OLD_VALUE;
import static hexlet.code.diffentry.DiffEntryFields.TYPE;
import static hexlet.code.diffentry.DiffEntryFields.VALUE;
import static hexlet.code.diffentry.DiffEntryType.ADDED;
import static hexlet.code.diffentry.DiffEntryType.CHANGED;
import static hexlet.code.diffentry.DiffEntryType.REMOVED;
import static hexlet.code.diffentry.DiffEntryType.UNCHANGED;

public final class StylishFormatter implements Formatter {

    public static final String STYLISH_FORMAT = "stylish";

    @Override
    public String format(List<DiffEntry> diff) {
        StringBuilder sb = new StringBuilder("{\n");
        for (DiffEntry entry : diff) {
            switch (entry.get(TYPE)) {
                case UNCHANGED ->
                        sb.append("    ")
                                .append(entry.get(KEY))
                                .append(": ")
                                .append(stringify(entry.get(VALUE)))
                                .append("\n");
                case CHANGED -> {
                    sb.append("  - ")
                            .append(entry.get(KEY))
                            .append(": ")
                            .append(stringify(entry.get(OLD_VALUE)))
                            .append("\n");
                    sb.append("  + ")
                            .append(entry.get(KEY))
                            .append(": ")
                            .append(stringify(entry.get(NEW_VALUE)))
                            .append("\n");
                }
                case REMOVED ->
                        sb.append("  - ")
                                .append(entry.get(KEY))
                                .append(": ")
                                .append(stringify(entry.get(VALUE)))
                                .append("\n");
                case ADDED ->
                        sb.append("  + ")
                                .append(entry.get(KEY))
                                .append(": ")
                                .append(stringify(entry.get(VALUE)))
                                .append("\n");
                default -> throw new IllegalArgumentException("Unknown type: " + entry.get(TYPE));
            }
        }
        sb.append("}");

        return sb.toString();
    }

    private String stringify(Object value) {
        if (value == null) {
            return "null";
        }

        if (value instanceof String) {
            return (String) value;
        }

        return value.toString();
    }

}
