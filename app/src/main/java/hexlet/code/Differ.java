package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static hexlet.code.DiffEntryType.UNCHANGED;
import static hexlet.code.DiffEntryType.CHANGED;
import static hexlet.code.DiffEntryType.REMOVED;
import static hexlet.code.DiffEntryType.ADDED;

public class Differ {

    public static List<DiffEntry> computeDiff(Map<String, Object> data1, Map<String, Object> data2) {
        List<DiffEntry> entries = new ArrayList<>();
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());

        for (String key : allKeys) {
            boolean inFile1 = data1.containsKey(key);
            boolean inFile2 = data2.containsKey(key);

            if (inFile1 && inFile2) {
                Object value1 = data1.get(key);
                Object value2 = data2.get(key);
                if (isEqual(value1, value2)) {
                    entries.add(new DiffEntry(key, UNCHANGED, null, value1));
                } else {
                    entries.add(new DiffEntry(key, CHANGED, value1, value2));
                }
            } else if (inFile1) {
                entries.add(new DiffEntry(key, REMOVED, data1.get(key), null));
            } else {
                entries.add(new DiffEntry(key, ADDED, null, data2.get(key)));
            }
        }
        return entries;
    }

    public static String generate(Map<String, Object> data1, Map<String, Object> data2, String format) {
        List<DiffEntry> diff = computeDiff(data1, data2);
        return Formatter.getFormatter(format).format(diff);
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
