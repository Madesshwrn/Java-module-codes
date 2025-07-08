import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileWriting {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter a string to write: ");
            String input = sc.nextLine();
            FileWriter writer = new FileWriter("output.txt");
            writer.write(input);
            writer.close();
            System.out.println("Data written to output.txt");
            sc.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
}