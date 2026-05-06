package hexlet.code;

public class DiffEntry {
    private final String key;
    private final DiffEntryType type;
    private final Object oldValue;
    private final Object newValue;

    public DiffEntry(String key, DiffEntryType type, Object oldValue, Object newValue) {
        this.key = key;
        this.type = type;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getKey() {
        return key;
    }

    public DiffEntryType getType() {
        return type;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

}
