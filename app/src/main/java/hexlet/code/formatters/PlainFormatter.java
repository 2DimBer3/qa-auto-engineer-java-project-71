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

public final class PlainFormatter implements Formatter {

    public static final String PLAIN_FORMAT = "plain";

    @Override
    public String format(List<DiffEntry> diff) {
        StringBuilder sb = new StringBuilder();
        for (DiffEntry entry : diff) {
            switch (entry.get(TYPE)) {
                case UNCHANGED -> {
                }
                case CHANGED -> sb.append("Property '").append(entry.get(KEY)).append("' was updated. From ")
                        .append(stringify(entry.get(OLD_VALUE))).append(" to ")
                        .append(stringify(entry.get(NEW_VALUE))).append("\n");
                case REMOVED -> sb.append("Property '").append(entry.get(KEY)).append("' was removed").append("\n");
                case ADDED -> sb.append("Property '").append(entry.get(KEY)).append("' was added with value: ")
                        .append(stringify(entry.get(VALUE))).append("\n");
                default -> throw new IllegalArgumentException("Unknown type: " + entry.get(TYPE));
            }
        }

        if (!sb.isEmpty() && sb.charAt(sb.length() - 1) == '\n') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    private String stringify(Object value) {
        if (value == null) {
            return "null";
        }

        if (value instanceof String) {
            return "'" + value + "'";
        }

        if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }

        return "[complex value]"; // если массив / объект
    }

}
