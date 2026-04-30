import java.util.ArrayList;
import java.util.HashMap;

public class Horspool {
    public static long runtime = 0;
    public static HashMap<Character, Integer> shiftTable;

    public static void HorspoolSearch(ArrayList<Integer> list, int[] measurements, String pattern, String key) {

        int keyLength = key.length();
        int patternLength = pattern.length();
        int searchBorder = patternLength - keyLength;

        long startTime = System.nanoTime();
        for (int i = 0; i <= searchBorder;) {
            int j;
            for (j = keyLength - 1; 0 <= j; j--) {
                measurements[1]++;

                if (pattern.charAt(i + j) != key.charAt(j)) {
                    break;
                }
            }
            if (j < 0) {
                measurements[0]++;
                list.add(i);
            }

            if (shiftTable.containsKey(pattern.charAt(i + keyLength - 1))) {
                i += shiftTable.get(pattern.charAt(i + keyLength - 1));
            } else {
                i += keyLength;
            }
        }
        long endTime = System.nanoTime();
        runtime += endTime - startTime;
    }

    public static HashMap<Character, Integer> generateTable(String key) {

        HashMap<Character, Integer> shiftTable = new HashMap<Character, Integer>();
        int keyLength = key.length();
        for (int i = keyLength - 2; 0 <= i; i--) {
            if (shiftTable.containsKey(key.charAt(i)))
                continue;
            else
                shiftTable.put(key.charAt(i), keyLength - i - 1);
        }
        return shiftTable;
    }
}
