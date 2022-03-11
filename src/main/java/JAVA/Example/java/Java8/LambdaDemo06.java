package JAVA.Example.java.Java8;

import java.util.function.Function;

public class LambdaDemo06 {

    public static void main(String[] args) {
        Function<Integer, String[]> function = String[]::new;
        String[] apply = function.apply(10);
        System.out.println(apply.length); // 10
    }

}
