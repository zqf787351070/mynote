package JAVA.Example.java.Java8;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class LambdaDemo04 {

    public static void main(String[] args) {

        // 对象::实例方法名
        PrintStream out = System.out;
        Consumer<String> consumer = out::println;
        consumer.accept("hello world！"); // hello world！

        // 类::静态方法名
        Comparator<Integer> comparator = Integer::compare;
        System.out.println(comparator.compare(2, 1)); // 1

        // 类::实例方法名
        BiPredicate<String, String> biPredicate = String::equals;
        System.out.println(biPredicate.test("1", "3")); // false
    }

}
