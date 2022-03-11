package JAVA.Example.java.Java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 按条件过滤 -- filter()
 */
public class StreamDemo03 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(88, 58, 94, 63, 74, 25, 96, 100);
        List<Integer> collect = list.stream().filter((x) -> x < 60).collect(Collectors.toList());
        collect.forEach(System.out::println); // 58 25
    }

}
