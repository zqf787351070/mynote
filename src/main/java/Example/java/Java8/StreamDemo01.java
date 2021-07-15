package Example.java.Java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream 的创建
 */
public class StreamDemo01 {

    public static void main(String[] args) {
        // 通过 java.util.Collection.stream() 方法用集合创建流
        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> stream01 = list.stream(); // 创建一个顺序流
        Stream<String> parallelStream = list.parallelStream(); // 创建一个并行流

        // 使用 java.util.Arrays.stream(T[] array) 方法用数组创建流
        int[] array = {1, 5, 6, 7, 87};
        IntStream stream02 = Arrays.stream(array);

        // 使用 Stream 的静态方法：of()、iterate()、generate()
        Stream<Integer> stream03 = Stream.of(1,2,4,5,6,8);
        Stream<Integer> stream04 = Stream.iterate(0, (x) -> x + 3).limit(4);
        stream04.forEach(System.out::println);
        Stream<Double> stream05 = Stream.generate(Math::random).limit(3);
        stream05.forEach(System.out::println);
    }

}
