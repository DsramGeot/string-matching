import java.util.ArrayList;
import java.util.HashMap;

public class Horspool {
    public static long runtime = 0;
    public static HashMap<Character, Integer> shiftTable;

    public static void HorspoolSearch(ArrayList<Integer> list, int[] measurements, String pattern, String key,
            int start, int end) {

        int keyLength = key.length();
        if (keyLength > end - start + 1)
            return;

        int searchBorder = end - keyLength + 1;

        long startTime = System.nanoTime();
        for (int i = start; i <= searchBorder;) {
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
}
