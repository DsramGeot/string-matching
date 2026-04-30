import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class StringMatching {
    static int numberOfOccurances = 0;
    static int numberOfComparisons = 0;
    static long runtime = 0; // in nanoseconds

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("HTML file path and key is not provided!");
            System.exit(1);
        }

        try {
            File htmlFile = new File(args[0]);

            Scanner input = new Scanner(htmlFile);
            String inputText = input.nextLine();
            String key = args[1];

            int inputLength = inputText.length();
            String refinedInput = inputText.substring(12, inputLength - 14);

            highlighter(findOccurances(refinedInput, key), refinedInput, key);

            System.out.println("Number of occurances: " + numberOfOccurances);
            System.out.println("Number of comparisons: " + numberOfComparisons);

            long milliseconds = runtime / 1000000;
            System.out.println("Runtime: " + milliseconds + "ms");
            input.close();

        } catch (FileNotFoundException e) {
            System.out.println("HTML file path is wrong!");
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> findOccurances(String pattern, String key) {
        ArrayList<Integer> list = new ArrayList<>();
        int patternLength = pattern.length();
        int keyLength = key.length();
        if (keyLength > patternLength)
            return null;
        int searchBorder = patternLength - keyLength;

        long startTime = System.nanoTime();
        for (int i = 0; i <= searchBorder; i++) {
            for (int j = 0; j < keyLength; j++) {
                numberOfComparisons++;
                if (pattern.charAt(i + j) != key.charAt(j))
                    break;
                if (j == keyLength - 1) {
                    numberOfOccurances++;
                    list.add(i);
                }
            }
        }
        long endTime = System.nanoTime();
        runtime = endTime - startTime;
        return list;
    }

    public static void highlighter(ArrayList<Integer> list, String pattern, String key) {

        File updated = new File("highlighted_file.html");
        int shift = 0;
        int keyLength = key.length();

        StringBuilder strBuild = new StringBuilder(pattern);
        for (int i = 0; i < list.size(); i++) {
            int index = list.get(i);

            strBuild.insert(index + shift, "<mark>");
            shift += 6;

            strBuild.insert(index + shift + keyLength, "</mark>");
            shift += 7;
        }

        try {
            FileWriter writer = new FileWriter(updated);
            writer.write("<HTML><BODY>" + strBuild.toString() + "</BODY></HTML>");
            writer.close();
        } catch (IOException e) {
            System.out.println("File creation failed!");
            e.printStackTrace();
        }
    }
}
