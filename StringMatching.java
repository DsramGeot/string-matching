import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

// args[2] -> 0 - BruteForce, 1 - Horspool, 2 - Boyer Moore

public class StringMatching {
    public static void main(String[] args) {

        Runtime runtime = Runtime.getRuntime();
        System.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        if (args.length != 3) {
            System.out.println("HTML file path, key and algorithm choice is not provided!");
            System.exit(1);
        }

        try {
            File htmlFile = new File(args[0]);
            String key = args[1];
            int choice = Integer.parseInt(args[2]);

            ArrayList<Integer> list = new ArrayList<>();
            String inputText;
            int[] measurements = { 0, 0 }; // 0 for numberOfOccurances, 1 for numberOfComparisons
            Scanner input = new Scanner(htmlFile);

            File updated = new File("highlighted_file.html");

            FileWriter cleanUp = new FileWriter(updated, false);
            cleanUp.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(updated, true));
            long runtimeInMilliseconds = 0;

            int start = 0;
            int end = 0;

            if (choice == 0) { // Brute force
                while (input.hasNextLine()) {
                    inputText = input.nextLine();

                    start = 0;
                    end = inputText.length() - 1;

                    if (inputText.indexOf('<') != -1) {
                        start = patternStart(inputText);
                        end = patternEnd(inputText);
                    }

                    if (start <= end)
                        BruteForce.bruteForceSearch(list, measurements, inputText, key, start, end);

                    highlighter(writer, list, inputText, key);
                    list.clear();
                }
                runtimeInMilliseconds = BruteForce.runtime / 1000000;
            } else if (choice == 1) { // Horspool
                Horspool.shiftTable = getBadSymbolTable(key);
                tablePrinter(Horspool.shiftTable, key);
                while (input.hasNextLine()) {
                    inputText = input.nextLine();

                    start = 0;
                    end = inputText.length() - 1;

                    if (inputText.indexOf('<') != -1) {
                        start = patternStart(inputText);
                        end = patternEnd(inputText);
                    }

                    if (start <= end)
                        Horspool.HorspoolSearch(list, measurements, inputText, key, start, end);

                    highlighter(writer, list, inputText, key);
                    list.clear();
                }
                runtimeInMilliseconds = Horspool.runtime / 1000000;
            } else if (choice == 2) { // Boyer-Moore
                BoyerMoore.badCharTable = StringMatching.getBadSymbolTable(key);
                BoyerMoore.goodSuffix = StringMatching.getGoodSuffixTable(key);
                tablePrinter(BoyerMoore.badCharTable, key);
                printGoodSuffixTable(BoyerMoore.goodSuffix, key);

                while (input.hasNextLine()) {
                    inputText = input.nextLine();

                    start = 0;
                    end = inputText.length() - 1;

                    if (inputText.indexOf('<') != -1) {
                        start = patternStart(inputText);
                        end = patternEnd(inputText);
                    }

                    if (start <= end)
                        BoyerMoore.BoyerMooreSearch(list, measurements, inputText, key, start, end);

                    highlighter(writer, list, inputText, key);
                    list.clear();
                }
                runtimeInMilliseconds = BoyerMoore.runtime / 1000000;
            } else {
                System.out.println("Invalid algorithm choice!");
                System.exit(1);
            }

            long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
            long memoryUsedBytes = memoryAfter - memoryBefore;
            double memoryUsedMB = memoryUsedBytes / (1024.0 * 1024.0);

            writer.close();
            input.close();

            System.out.printf("Number of occurances: %,d%n", measurements[0]);
            System.out.printf("Number of comparisons: %,d%n", measurements[1]);
            System.out.println("Runtime: " + runtimeInMilliseconds + "ms");
            System.out.printf("Memory used: %.2f MB %n", memoryUsedMB);

            try (PrintWriter outputWriter = new PrintWriter(new FileWriter("output.txt", true))) {

                outputWriter.println(args[2] + "    #BruteForce, 1 - Horspool, 2 - Boyer Moore");
                outputWriter.println(key + "    #Key searched");
                outputWriter.println(args[0] + "    #File searched");
                outputWriter.println(measurements[0] + "    #Occurance number");
                outputWriter.println(measurements[1] + "    #Comparison number");
                outputWriter.println(runtimeInMilliseconds + "    #Runtime in ms");
                outputWriter.printf("%.2f    #Memory in MB%n", memoryUsedMB);

                outputWriter.println();

            } catch (IOException e) {
                System.err.println("An error occurred while writing the output");
            }

        } catch (FileNotFoundException e) {
            System.out.println("HTML file path is wrong!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Output file could not be created!");
            e.printStackTrace();
        }
        /*
         * String pattern = "ABCAABBACA";
         * int[] gs = getGoodSuffixTable(pattern);
         * 
         * for (int i = 0; i < gs.length; i++) {
         * System.out.println("gs[" + i + "] = " + gs[i]);
         */
    }

    public static void highlighter(BufferedWriter writer, ArrayList<Integer> list, String pattern, String key) {

        int shift = 0;
        int keyLength = key.length();
        int listSize = list.size();

        StringBuilder strBuild = new StringBuilder(pattern);
        for (int i = 0; i < listSize; i++) {
            int index = list.get(i);

            if (i == 0 || !(list.get(i - 1) == index - 1)) {
                strBuild.insert(index + shift, "<mark>");
                shift += 6;
            }

            if (i == (listSize - 1) || !(list.get(i + 1) == index + 1)) {
                strBuild.insert(index + shift + keyLength, "</mark>");
                shift += 7;
            }
        }

        try {
            writer.write(strBuild.toString() + "\n");
        } catch (IOException e) {
            System.out.println("File could not be edited!");
            e.printStackTrace();
        }
    }

    public static HashMap<Character, Integer> getBadSymbolTable(String key) {
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

    public static int[] getGoodSuffixTable(String key) {
        int m = key.length();
        int[] goodSuffix = new int[m + 1];
        int[] suffix = new int[m];

        buildSuffixArray(key, suffix);

        for (int i = 0; i <= m; i++) {
            goodSuffix[i] = m;
        }

        for (int i = m - 1; i >= 0; i--) {
            if (suffix[i] == i + 1) {

                int shift = m - 1 - i;

                for (int j = 0; j < shift; j++) {
                    if (goodSuffix[j] == m) {
                        goodSuffix[j] = shift;
                    }
                }
            }
        }

        // suffix doesnt match entirely so we check if a part of it matches with prefix
        for (int i = 0; i <= m - 2; i++) {
            goodSuffix[m - 1 - suffix[i]] = m - 1 - i;
        }

        return goodSuffix;

    }

    public static void buildSuffixArray(String key, int[] suffix) {
        int m = key.length();
        suffix[m - 1] = m;

        for (int i = m - 2; i >= 0; i--) {
            int j = i;
            // one goes from j to 0 other goes from the end to the left
            while (j >= 0 && key.charAt(j) == key.charAt(m - 1 - (i - j))) {
                j--;
            }
            suffix[i] = i - j;
        }
    }

    public static void tablePrinter(HashMap<Character, Integer> badCharTable, String key) {

        System.out.println("--Bad Character Last Occurrence Table-- ");
        System.out.println(" Char | Position ");
        System.out.println("------|----------");

        for (Character entry : badCharTable.keySet()) {
            System.out.println("  " + entry + "   |    " + badCharTable.get(entry));

        }
        System.out.println(" N/A  |    -1");
    }

    public static void printGoodSuffixTable(int[] goodSuffix, String key) {
        System.out.println("--Good Suffix Shift Table-- ");
        System.out.println(" Substring | Shift ");
        System.out.println("-----------|-------");

        int m = key.length();
        for (int i = 0; i < m; i++) {
            String substring = key.substring(m - 1 - i);
            System.out.printf("  %-8s |   %2d%n", substring, goodSuffix[i]);
        }
    }

    public static int patternStart(String pattern) { // starting of the visible element
        boolean lookForMatch = false;
        int length = pattern.length();
        for (int i = 0; i < length; i++) {
            char ch = pattern.charAt(i);
            if (ch == '<')
                lookForMatch = true;
            else if (ch == '>')
                lookForMatch = false;
            else if (!lookForMatch && !Character.isWhitespace(ch))
                return i;
        }
        return pattern.length();
    }

    public static int patternEnd(String pattern) { // ending of the visible element (inclusive)
        boolean lookForMatch = false;
        int length = pattern.length();
        for (int i = length - 1; i >= 0; i--) {
            char ch = pattern.charAt(i);
            if (ch == '>')
                lookForMatch = true;
            else if (ch == '<')
                lookForMatch = false;
            else if (!lookForMatch && !Character.isWhitespace(ch))
                return i;
        }
        return -1;
    }
}
