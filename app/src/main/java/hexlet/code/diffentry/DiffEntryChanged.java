package hexlet.code.diffentry;

public final class DiffEntryChanged extends DiffEntry {
    private final Object oldValue;
    private final Object newValue;

    public DiffEntryChanged(String key, DiffEntryType type, Object oldValue, Object newValue) {
        super(key, type);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }
}
