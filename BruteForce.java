import java.util.ArrayList;

public class BruteForce {
    public static long runtime = 0;
    public static void bruteForceSearch(ArrayList<Integer> list, int[] measurements, String pattern, String key) {
        int patternLength = pattern.length();
        int keyLength = key.length();
        if (keyLength > patternLength)
            return;
        int searchBorder = patternLength - keyLength;

        long startTime = System.nanoTime();
        for (int i = 0; i <= searchBorder; i++) {
            for (int j = 0; j < keyLength; j++) {
                measurements[1]++;
                if (pattern.charAt(i + j) != key.charAt(j))
                    break;
                if (j == keyLength - 1) {
                    measurements[0]++;
                    list.add(i);
                }
            }
        }
        long endTime = System.nanoTime();
        runtime += endTime - startTime;
        return;
    }
}
