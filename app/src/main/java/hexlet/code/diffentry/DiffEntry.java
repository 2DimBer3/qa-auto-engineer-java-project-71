package hexlet.code.diffentry;

public abstract class DiffEntry {
    private final String key;
    private final DiffEntryType type;

    protected DiffEntry(String key, DiffEntryType type) {
        this.key = key;
        this.type = type;
    }

    public final String getKey() {
        return key;
    }

    public final DiffEntryType getType() {
        return type;
    }
}
