
import java.lang.reflect.*;

public class ReflectionDemo {
    public void greet(String name) {
        System.out.println("Hello, " + name);
    }

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("ReflectionDemo");
        Object obj = cls.getDeclaredConstructor().newInstance();

        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Method: " + method.getName());
        }

        Method greetMethod = cls.getDeclaredMethod("greet", String.class);
        greetMethod.invoke(obj, "Java User");
    }
}
