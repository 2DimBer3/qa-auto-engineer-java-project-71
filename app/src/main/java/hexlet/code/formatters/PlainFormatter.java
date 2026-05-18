package hexlet.code.formatters;

import hexlet.code.DiffEntry;

import java.util.List;

public final class PlainFormatter implements Formatter {

    public static final String PLAIN_FORMAT = "plain";

    @Override
    public String format(List<DiffEntry> diff) {
        StringBuilder sb = new StringBuilder();
        for (DiffEntry entry : diff) {
            switch (entry.getType()) {
                case UNCHANGED -> { }
                case CHANGED -> sb.append("Property '").append(entry.getKey()).append("' was updated. From ")
                            .append(stringify(entry.getOldValue())).append(" to ")
                            .append(stringify(entry.getNewValue())).append("\n");
                case REMOVED -> sb.append("Property '").append(entry.getKey()).append("' was removed").append("\n");
                case ADDED -> sb.append("Property '").append(entry.getKey()).append("' was added with value: ")
                            .append(stringify(entry.getNewValue())).append("\n");
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
