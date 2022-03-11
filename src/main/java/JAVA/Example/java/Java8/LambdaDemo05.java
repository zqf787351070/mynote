package JAVA.Example.java.Java8;

import JAVA.Example.java.JavaBase.Reflect.Programmer;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class LambdaDemo05 {

    public static void main(String[] args) {
        // 引用无参构造
        Supplier<Programmer> supplier = Programmer::new;
        System.out.println(supplier.get()); // Programmer{name='null', age=0}
        // 引用有参构造
        BiFunction<String, Integer, Programmer> biFunction = Programmer::new;
        System.out.println(biFunction.apply("zqf", 25)); // Programmer{name='zqf', age=25}
    }

}
