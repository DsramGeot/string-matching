import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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
            FileWriter writer = new FileWriter(file);
            StringBuilder str = new StringBuilder();
            Random random = new Random();
            int binary = 0;

            str.append("<HTML><BODY>");
            for (int i = 0; i < length; i++) {
                
                if (i % 400 == 0)
                    str.append("\n");
                binary = random.nextInt(2);
                str.append(binary);
            }
            str.append("\n</BODY></HTML>");

            writer.write(str.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("File could not be created!");
            System.exit(1);
        }
    }
}