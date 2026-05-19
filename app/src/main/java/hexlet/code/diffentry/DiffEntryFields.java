package hexlet.code.diffentry;

public enum DiffEntryFields {
    TYPE("type"),
    KEY("key"),
    VALUE("value"),
    OLD_VALUE("oldValue"),
    NEW_VALUE("newValue");

    private final String fieldName;

    DiffEntryFields(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return fieldName;
    }
}
