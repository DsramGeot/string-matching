import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class StringMatching {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("HTML file path and key is not provided!");
            System.exit(1);
        }

        try {
            File htmlFile = new File(args[0]);
            String key = args[1];
            ArrayList<Integer> list = new ArrayList<>();
            String inputText;
            int[] measurements = { 0, 0 }; // 0 for numberOfOccurances, 1 for numberOfComparisons
            Scanner input = new Scanner(htmlFile);

            File updated = new File("highlighted_file.html");
            
            FileWriter cleanUp = new FileWriter(updated, false);
            cleanUp.close();

            FileWriter writer = new FileWriter(updated, true);

            while (input.hasNextLine()) {
                inputText = input.nextLine();
                BruteForce.bruteForceSearch(list, measurements, inputText, key);
                highlighter(writer, list, inputText, key);
                list.clear();
            }
            writer.close();
            input.close();

            long runtimeInMilliseconds = BruteForce.runtime / 1000000;
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

    public static void highlighter(FileWriter writer, ArrayList<Integer> list, String pattern, String key) {

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
            writer.write(strBuild.toString() + "\n");
        } catch (IOException e) {
            System.out.println("File could not be edited!");
            e.printStackTrace();
        }
    }
}
