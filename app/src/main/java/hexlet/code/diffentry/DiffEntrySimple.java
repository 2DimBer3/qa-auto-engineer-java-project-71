package hexlet.code.diffentry;

public final class DiffEntrySimple extends DiffEntry {
    private final Object value;

    public DiffEntrySimple(String key, DiffEntryType type, Object value) {
        super(key, type);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
