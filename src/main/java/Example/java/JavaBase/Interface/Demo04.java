package Example.java.JavaBase.Interface;

public class Demo04 {
    public static void main(String[] args) {
        GreetingService greetingService = message -> System.out.println("Hello " + message);
        greetingService.sayMessage("@FunctionalInterface");
    }
}

@FunctionalInterface
interface GreetingService {
    void sayMessage(String message);
}
