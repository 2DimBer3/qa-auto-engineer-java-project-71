package hexlet.code.formatters;

import hexlet.code.DiffEntry;
import hexlet.code.Formatter;

import java.util.List;

public class StylishFormatter implements Formatter {

    public static final String STYLISH_FORMAT = "stylish";

    @Override
    public String format(List<DiffEntry> diff) {
        StringBuilder sb = new StringBuilder("{\n");
        for (DiffEntry entry : diff) {
            switch (entry.getType()) {
                case UNCHANGED:
                    sb.append("    ").append(entry.getKey()).append(": ")
                            .append(stringify(entry.getNewValue())).append("\n");
                    break;
                case CHANGED:
                    sb.append("  - ").append(entry.getKey()).append(": ")
                            .append(stringify(entry.getOldValue())).append("\n");
                    sb.append("  + ").append(entry.getKey()).append(": ")
                            .append(stringify(entry.getNewValue())).append("\n");
                    break;
                case REMOVED:
                    sb.append("  - ").append(entry.getKey()).append(": ")
                            .append(stringify(entry.getOldValue())).append("\n");
                    break;
                case ADDED:
                    sb.append("  + ").append(entry.getKey()).append(": ")
                            .append(stringify(entry.getNewValue())).append("\n");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type: " + entry.getType());
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
