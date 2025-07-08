import java.util.*;

record Person(String name, int age) {}

public class RecordExample {
    public static void main(String[] args) {
        List<Person> people = List.of(new Person("Alice", 30), new Person("Bob", 17));
        people.stream().filter(p -> p.age() > 18).forEach(System.out::println);
    }
}