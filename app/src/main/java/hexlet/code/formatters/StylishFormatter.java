package hexlet.code.formatters;

import hexlet.code.DiffEntry;

import java.util.List;

public final class StylishFormatter implements Formatter {

    public static final String STYLISH_FORMAT = "stylish";

    @Override
    public String format(List<DiffEntry> diff) {
        StringBuilder sb = new StringBuilder("{\n");
        for (DiffEntry entry : diff) {
            switch (entry.getType()) {
                case UNCHANGED -> sb.append("    ").append(entry.getKey()).append(": ")
                            .append(stringify(entry.getNewValue())).append("\n");
                case CHANGED -> {
                    sb.append("  - ").append(entry.getKey()).append(": ")
                            .append(stringify(entry.getOldValue())).append("\n");
                    sb.append("  + ").append(entry.getKey()).append(": ")
                            .append(stringify(entry.getNewValue())).append("\n");
                }
                case REMOVED -> sb.append("  - ").append(entry.getKey()).append(": ")
                            .append(stringify(entry.getOldValue())).append("\n");
                case ADDED -> sb.append("  + ").append(entry.getKey()).append(": ")
                            .append(stringify(entry.getNewValue())).append("\n");
                default -> throw new IllegalArgumentException("Unknown type: " + entry.getType());
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
