package hexlet.code.formatters;

import hexlet.code.diffentry.DiffEntryChanged;
import hexlet.code.diffentry.DiffEntry;
import hexlet.code.diffentry.DiffEntrySimple;

import java.util.List;

public final class StylishFormatter implements Formatter {

    public static final String STYLISH_FORMAT = "stylish";

    @Override
    public String format(List<DiffEntry> diff) {
        StringBuilder sb = new StringBuilder("{\n");
        for (DiffEntry entry : diff) {
            switch (entry.getType()) {
                case UNCHANGED -> {
                    DiffEntrySimple e = (DiffEntrySimple) entry;
                    sb.append("    ").append(e.getKey()).append(": ").append(stringify(e.getValue())).append("\n");
                }
                case CHANGED -> {
                    DiffEntryChanged e = (DiffEntryChanged) entry;
                    sb.append("  - ").append(e.getKey()).append(": ").append(stringify(e.getOldValue())).append("\n");
                    sb.append("  + ").append(e.getKey()).append(": ").append(stringify(e.getNewValue())).append("\n");
                }
                case REMOVED -> {
                    DiffEntrySimple e = (DiffEntrySimple) entry;
                    sb.append("  - ").append(e.getKey()).append(": ").append(stringify(e.getValue())).append("\n");
                }
                case ADDED -> {
                    DiffEntrySimple e = (DiffEntrySimple) entry;
                    sb.append("  + ").append(e.getKey()).append(": ").append(stringify(e.getValue())).append("\n");
                }
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
