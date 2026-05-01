import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

// args[2] -> 0 - BruteForce, 1 - Horspool, 2 - Boyer Moore

public class StringMatching {
    public static void main(String[] args) {
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
            if (choice == 0) { // Brute force
                while (input.hasNextLine()) {
                    inputText = input.nextLine();
                    BruteForce.bruteForceSearch(list, measurements, inputText, key);
                    highlighter(writer, list, inputText, key);
                    list.clear();
                }
                runtimeInMilliseconds = BruteForce.runtime / 1000000;
            } else if (choice == 1) { // Horspool
                Horspool.shiftTable = getBadSymbolTable(key);
                tablePrinter(Horspool.shiftTable, key);
                while (input.hasNextLine()) {
                    inputText = input.nextLine();
                    Horspool.HorspoolSearch(list, measurements, inputText, key);
                    highlighter(writer, list, inputText, key);
                    // Horspool.showShiftTable();
                    list.clear();
                }
                runtimeInMilliseconds = Horspool.runtime / 1000000;
            } else if (choice == 2) { // Boyer-Moore
                while (input.hasNextLine()) {
                    inputText = input.nextLine();
                    // BoyerMoore.BoyerMooreSearch(list, measurements, inputText, key);
                    highlighter(writer, list, inputText, key);
                    // BoyerMoore.showTables();
                    list.clear();
                }
                // runtimeInMilliseconds = BoyerMoore.runtime / 1000000;
            } else {
                System.out.println("Invalid algorithm choice!");
                System.exit(1);
            }

            writer.close();
            input.close();

            System.out.println("Number of occurances: " + measurements[0]);
            System.out.println("Number of comparisons: " + measurements[1]);
            System.out.println("Runtime: " + runtimeInMilliseconds + "ms");

        } catch (FileNotFoundException e) {
            System.out.println("HTML file path is wrong!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Output file could not be created!");
            e.printStackTrace();
        }
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

    public static HashMap<Integer, Integer> getGoodSuffixTable(String key) {
        HashMap<Integer, Integer> goodSuffix = new HashMap<Integer, Integer>();

        // Implement good suffix table here


        return goodSuffix;
    }
    public static void tablePrinter(HashMap<Character, Integer> shiftTable, String key){
        
        System.out.println("--Bad Symbol Shift Table-- ");
        System.out.println(" Char | Shift ");
        System.out.println("------|-------");
        
        
        for (Character entry : shiftTable.keySet()) {
            System.out.println("  "+entry+"   |   "+shiftTable.get(entry));
            
        }
        System.out.println(" N/A  |   "+key.length() );
    }
}
