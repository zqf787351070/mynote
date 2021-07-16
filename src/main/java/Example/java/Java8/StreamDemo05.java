package Example.java.Java8;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * map() 和 flatMap()
 */
public class StreamDemo05 {

    public static void main(String[] args) {
        // 字符串全部替换为大写
        List<String> list01 = Arrays.asList("zhangsan", "lisi", "wangwu", "sunliu");
        List<String> collect = list01.stream().map((x) -> x.toUpperCase(Locale.ROOT)).collect(Collectors.toList());
        System.out.println(collect); // [ZHANGSAN, LISI, WANGWU, SUNLIU]

        // 所有数值 + 100
        List<Integer> list02 = Arrays.asList(1, 2, 3, 4);
        List<Integer> collect1 = list02.stream().map((x) -> x + 100).collect(Collectors.toList());
        System.out.println(collect1); // [101, 102, 103, 104]

        // 将两个字符数组合并成一个新的字符数组
        String[] arr = {"z, h, a, n, g", "s, a, n"};
        List<String> list = Arrays.asList(arr);
        List<String> collect2 = list.stream().flatMap(x -> {
            String[] array = x.split(",");
            Stream<String> stream = Arrays.stream(array);
            return stream;
        }).collect(Collectors.toList());
        System.out.println(collect2); // [z,  h,  a,  n,  g, s,  a,  n]
    }

}
