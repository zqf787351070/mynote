package Example.java.Java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * 聚合 -- max()/min()/count()
 */
public class StreamDemo04 {

    public static void main(String[] args) {
        // 获取 String 集合中最长的元素
        List<String> list01 = Arrays.asList("zhangsan", "lisi", "wangwu", "sunliu");
        Comparator<? super String> comparator = Comparator.comparing(String::length);
        Optional<String> max = list01.stream().max(comparator);
        System.out.println(max.get()); // zhangsan

        // 获取集合中最大/最小值
        List<Integer> list02 = Arrays.asList(1, 17, 27, 7);
        Optional<Integer> max1 = list02.stream().max(Integer::compareTo);
        System.out.println(max1.get()); // 27
        // 自定义排序
        Optional<Integer> max2 = list02.stream().max((o1, o2) -> o1.compareTo(o2));
        System.out.println(max2.get()); // 27

        // 获取集合中符合条件元素的个数
        long count = list02.stream().filter((x) -> x < 10).count();
        System.out.println(count); // 2
    }

}
