package hexlet.code.formatters;

import hexlet.code.diffentry.DiffEntryChanged;
import hexlet.code.diffentry.DiffEntry;
import hexlet.code.diffentry.DiffEntrySimple;

import java.util.List;

public final class PlainFormatter implements Formatter {

    public static final String PLAIN_FORMAT = "plain";

    @Override
    public String format(List<DiffEntry> diff) {
        StringBuilder sb = new StringBuilder();
        for (DiffEntry entry : diff) {
            switch (entry.getType()) {
                case UNCHANGED -> { }
                case CHANGED -> {
                    DiffEntryChanged e = (DiffEntryChanged) entry;
                    sb.append("Property '").append(entry.getKey()).append("' was updated. From ")
                            .append(stringify(e.getOldValue())).append(" to ")
                            .append(stringify(e.getNewValue())).append("\n");
                }
                case REMOVED -> sb.append("Property '").append(entry.getKey()).append("' was removed").append("\n");
                case ADDED -> {
                    DiffEntrySimple e = (DiffEntrySimple) entry;
                    sb.append("Property '").append(entry.getKey()).append("' was added with value: ")
                            .append(stringify(e.getValue())).append("\n");
                }
                default -> throw new IllegalArgumentException("Unknown type: " + entry.getType());
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
