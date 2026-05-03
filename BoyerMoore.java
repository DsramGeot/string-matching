import java.util.ArrayList;
import java.util.HashMap;

public class BoyerMoore {
    public static long runtime = 0;
    public static HashMap<Character, Integer> shiftTable;
    public static int[] goodSuffix;

    public static void BoyerMooreSearch(ArrayList<Integer> list, int[] measurements, String pattern, String key,
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

            int shiftamount = 0;
            if (shiftTable.containsKey(pattern.charAt(i + keyLength - 1))) {
                shiftamount = shiftTable.get(pattern.charAt(i + keyLength - 1));
            } else {
                shiftamount = keyLength;
            }

            i += Math.max(shiftamount, goodSuffix[j + 1]);
        }
        long endTime = System.nanoTime();
        runtime += endTime - startTime;
    }
}
