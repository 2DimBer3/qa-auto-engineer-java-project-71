package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hexlet.code.DiffEntry;
import hexlet.code.Formatter;

import java.util.List;

public final class JsonFormatter implements Formatter {

    public static final String JSON_FORMAT = "json";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String format(List<DiffEntry> diff) {
        ArrayNode array = MAPPER.createArrayNode();

        for (DiffEntry entry : diff) {
            ObjectNode node = MAPPER.createObjectNode();
            node.put("key", entry.getKey());
            node.put("type", String.valueOf(entry.getType()));
            switch (entry.getType()) {
                case UNCHANGED, ADDED -> node.putPOJO("value", entry.getNewValue());
                case CHANGED -> {
                    node.putPOJO("oldValue", entry.getOldValue());
                    node.putPOJO("newValue", entry.getNewValue());
                }
                case REMOVED -> node.putPOJO("value", entry.getOldValue());
                default -> throw new IllegalArgumentException("Unknown type: " + entry.getType());
            }
            array.add(node);
        }

        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(array);
        } catch (Exception e) {
            throw new RuntimeException("Failed to format JSON", e);
        }
    }
}
