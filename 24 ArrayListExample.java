import java.util.ArrayList;
import java.util.Scanner;

public class ArrayListExample {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> students = new ArrayList<>();
        System.out.println("Enter names (type 'end' to finish):");
        while (true) {
            String name = sc.nextLine();
            if (name.equalsIgnoreCase("end")) break;
            students.add(name);
        }
        System.out.println("Student names:");
        for (String s : students) {
            System.out.println(s);
        }
        sc.close();
    }
}