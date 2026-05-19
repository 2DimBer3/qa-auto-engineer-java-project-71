package hexlet.code.diffentry;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.LinkedHashMap;
import java.util.Map;

public final class DiffEntry {
    private final Map<String, Object> data = new LinkedHashMap<>();

    public void put(DiffEntryFields field, Object value) {
        data.put(field.toString(), value);
    }

    public Object get(DiffEntryFields field) {
        return data.get(field.toString());
    }

    @SuppressWarnings("unused")
    @JsonValue
    public Map<String, Object> getData() {
        return data;
    }
}
