package hexlet.code;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Differ {

    public static String generate(Map<String, Object> data1, Map<String, Object> data2) {
        StringBuilder result = new StringBuilder();

        // Получаем все уникальные ключи из обоих файлов, отсортированные по алфавиту
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());

        result.append("{\n");

        for (String key : allKeys) {
            boolean inFile1 = data1.containsKey(key);
            boolean inFile2 = data2.containsKey(key);

            if (inFile1 && inFile2) {
                // Ключ есть в обоих файлах
                Object value1 = data1.get(key);
                Object value2 = data2.get(key);

                if (isEqual(value1, value2)) {
                    // Значения совпадают
                    result.append("    ").append(key).append(": ").append(value1).append("\n");
                } else {
                    // Значения различаются
                    result.append("  - ").append(key).append(": ").append(value1).append("\n");
                    result.append("  + ").append(key).append(": ").append(value2).append("\n");
                }
            } else if (inFile1) {
                // Ключ есть только в первом файле
                result.append("  - ").append(key).append(": ").append(data1.get(key)).append("\n");
            } else {
                // Ключ есть только во втором файле
                result.append("  + ").append(key).append(": ").append(data2.get(key)).append("\n");
            }
        }

        result.append("}");

        return result.toString();
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
