package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.diffentry.DiffEntry;

import java.util.List;

public final class JsonFormatter implements Formatter {

    public static final String JSON_FORMAT = "json";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String format(List<DiffEntry> diff) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(diff);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to format JSON", e);
        }
    }

}
