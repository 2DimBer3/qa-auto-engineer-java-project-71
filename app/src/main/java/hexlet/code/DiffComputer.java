package hexlet.code;

import hexlet.code.diffentry.DiffEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static hexlet.code.diffentry.DiffEntryFields.NEW_VALUE;
import static hexlet.code.diffentry.DiffEntryFields.OLD_VALUE;
import static hexlet.code.diffentry.DiffEntryFields.TYPE;
import static hexlet.code.diffentry.DiffEntryFields.KEY;
import static hexlet.code.diffentry.DiffEntryFields.VALUE;
import static hexlet.code.diffentry.DiffEntryType.UNCHANGED;
import static hexlet.code.diffentry.DiffEntryType.CHANGED;
import static hexlet.code.diffentry.DiffEntryType.REMOVED;
import static hexlet.code.diffentry.DiffEntryType.ADDED;

public class DiffComputer {
    public static List<DiffEntry> computeDiff(Map<String, Object> data1, Map<String, Object> data2) {
        List<DiffEntry> entries = new ArrayList<>();
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());

        for (String key : allKeys) {
            boolean inFile1 = data1.containsKey(key);
            boolean inFile2 = data2.containsKey(key);

            DiffEntry diffEntry = new DiffEntry();
            diffEntry.put(KEY, key);

            if (inFile1 && inFile2) {
                Object value1 = data1.get(key);
                Object value2 = data2.get(key);
                if (isEqual(value1, value2)) {
                    diffEntry.put(TYPE, UNCHANGED);
                    diffEntry.put(VALUE, value1);
                } else {
                    diffEntry.put(TYPE, CHANGED);
                    diffEntry.put(OLD_VALUE, value1);
                    diffEntry.put(NEW_VALUE, value2);
                }
            } else if (inFile1) {
                diffEntry.put(TYPE, REMOVED);
                diffEntry.put(VALUE, data1.get(key));
            } else {
                diffEntry.put(TYPE, ADDED);
                diffEntry.put(VALUE, data2.get(key));
            }
            entries.add(diffEntry);
        }
        return entries;
    }

    private static boolean isEqual(Object value1, Object value2) {
        if (value1 == null && value2 == null) {
            return true;
        }
        if (value1 == null || value2 == null) {
            return false;
        }
        return value1.equals(value2);
    }
}
