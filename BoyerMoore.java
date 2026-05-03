import java.util.ArrayList;
import java.util.HashMap;

public class BoyerMoore {
    public static long runtime = 0;
    public static HashMap<Character, Integer> badCharTable;
    public static int[] goodSuffix;

    public static void BoyerMooreSearch(ArrayList<Integer> list, int[] measurements, String pattern, String key,
            int start, int end) {

        int keyLength = key.length();

        if (keyLength == 0 || keyLength > end - start + 1)
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
            if (j >= 0) {
                char textChar = pattern.charAt(i + j);
                if (badCharTable.containsKey(textChar)) {
                    shiftamount = j - badCharTable.get(textChar);
                } else {
                    shiftamount = j + 1;
                }
                shiftamount = Math.max(1, shiftamount);
            }
            shiftamount = Math.max(1, shiftamount);

            int goodSuffixShift = (j < 0) ? goodSuffix[0] : goodSuffix[j];

            i += Math.max(shiftamount, goodSuffixShift);
        }
        long endTime = System.nanoTime();
        runtime += endTime - startTime;
    }
}
