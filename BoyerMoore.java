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

                i += goodSuffix[0];
                continue;
            }

            int k = keyLength - j - 1;
            char ch = pattern.charAt(i + j);
            int t1;

            if (badCharTable.containsKey(ch))
                t1 = badCharTable.get(ch);
            else
                t1 = keyLength;

            int badSymbolShift = Math.max(t1 - k, 1);
            int goodSuffixShift = goodSuffix[j];

            i += Math.max(badSymbolShift, goodSuffixShift);
        }
        long endTime = System.nanoTime();
        runtime += endTime - startTime;
    }
}
