package Example.java.Java8;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 遍历 and 匹配 -- forEach()/findFirst()/findAny()/anyMatch()
 */
public class StreamDemo02 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 3, 5, 7, 6, 11, 18, 9);

        // 遍历输出符合条件的元素
        list.stream().filter((x) -> x > 7).forEach(System.out::println); // 11 18 9

        // 匹配第一个符合条件的元素
        Optional<Integer> first = list.stream().filter((x) -> x <= 5).findFirst();
        System.out.println(first.get()); // 1

        // 匹配任意一个符合条件的元素(适用于并行流)
        Optional<Integer> any = list.parallelStream().filter((x) -> x <= 5).findAny();
        System.out.println(any.get()); // 5

        // 是否包含符合条件的元素
        boolean anyMatch = list.stream().anyMatch((x) -> x > 15);
        System.out.println(anyMatch); // true

    }

}
