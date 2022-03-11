package JAVA.Example.java.Java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 四大函数式接口的使用示例
 */
public class LambdaDemo03 {

    /**
     * 消费型接口 Consumer
     */
    public static void makeMoney(int num, Consumer<Integer> consumer) {
        consumer.accept(num);
    }

    /**
     * 供给型接口 Supplier：随机产生整数加入集合中
     */
    public static List<Integer> addNumInList(int size, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i ++) {
            list.add(supplier.get());
        }
        return list;
    }

    /**
     * 函数型接口 Function
     */
    public static String handleStr(String str, Function<String, String> function) {
        return function.apply(str);
    }

    /**
     * 断言型接口 Predicate：自定义条件过滤字符串集合
     */
    public static List<String> filterStr(List<String> strList, Predicate<String> predicate) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < strList.size(); i ++) {
            if (predicate.test(strList.get(i))) {
                result.add(strList.get(i));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // 消费型接口 Consumer
        makeMoney(200, (num) -> System.out.println("今天捡了" + num + "元！"));

        // 供给型接口 Supplier
        List<Integer> list = addNumInList(5, () -> (int) (Math.random() * 100));
        list.forEach((x) -> System.out.println(x));

        // 函数型接口 Function
        String str = handleStr("ABCDEF", (s) -> s.toLowerCase(Locale.ROOT));
        System.out.println(str);

        // 断言型接口 Predicate
        List<String> strings = Arrays.asList("啊啊啊", "2333", "666", "?????????");
        List<String> resList = filterStr(strings, (s) -> s.length() > 3);
        resList.forEach((x) -> System.out.println(x));
    }

    /**
     * 运行结果：
     * 今天捡了200元！
     * 50
     * 25
     * 52
     * 71
     * 47
     * abcdef
     * 2333
     * ?????????
     */

}
