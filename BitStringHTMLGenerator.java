import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BitStringHTMLGenerator {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Length value is not provided!");
            System.exit(1);
        }

        int length = Integer.parseInt(args[0]);
        htmlGenerator(length);
    }

    public static void htmlGenerator(int length) {

        File file = new File("BitString.html");
        try {
            FileWriter cleanUp = new FileWriter(file, false);
            cleanUp.close();

            FileWriter writer = new FileWriter(file);



            writer.close();
        } catch (IOException e) {
            System.out.println("File could not be created!");
            System.exit(1);
        }

    }
}