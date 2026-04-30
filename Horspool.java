import java.util.ArrayList;
import java.util.HashMap;

public class Horspool {

    public static void HorspoolSearch(ArrayList<Integer> list, int[] measurements, String pattern, String key) {

        HashMap<Character, Integer> shiftTable = new HashMap<Character, Integer>();
        int keyLength = key.length();

        for (int i = keyLength - 2; i <= 0; i--) {
            if (shiftTable.containsKey(key.charAt(i))) {
                break;
            } else {
                shiftTable.put(key.charAt(i), keyLength - i - 1);
            }
        }

    }

}
