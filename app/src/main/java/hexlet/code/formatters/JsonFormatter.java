package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffEntry;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class JsonFormatter implements Formatter {

    public static final String JSON_FORMAT = "json";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String format(List<DiffEntry> diff) {
        return writeValueAsString(diffToPlainList(diff));
    }

    private static List<Map<String, Object>> diffToPlainList(List<DiffEntry> diff) {
        return diff.stream()
                .map(JsonFormatter::diffEntryToMap)
                .toList();
    }

    private static Map<String, Object> diffEntryToMap(DiffEntry entry) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("key", entry.getKey());
        map.put("type", entry.getType().toString());

        switch (entry.getType()) {
            case UNCHANGED, ADDED -> map.put("value", entry.getNewValue());
            case CHANGED -> {
                map.put("oldValue", entry.getOldValue());
                map.put("newValue", entry.getNewValue());
            }
            case REMOVED -> map.put("value", entry.getOldValue());
            default -> throw new IllegalArgumentException("Unknown type: " + entry.getType());
        }
        return map;
    }

    private static String writeValueAsString(Object data) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to format JSON", e);
        }
    }
}
