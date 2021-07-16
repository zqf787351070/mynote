# 1. Stream 概述
Stream 流是 JDK 8 新增的成员，允许以声明性方式处理数据集合，可以把 Stream 流看作是遍历数据集合的一个高级迭代器。

Stream 将要处理的元素集合看作一种流，在流的过程中，借助 Stream API 对流中的元素进行操作，比如：筛选、排序、聚合等……

## 1.1 使用 Stream 的好处？
代码以声明性方式书写，说明想要完成什么，而不是说明如何完成一个操作。

可以把几个基础操作连接起来，来表达复杂的数据处理的流水线，同时保持代码清晰可读。

## 1.2 Stream 流是什么？
从支持数据处理操作的源生成元素序列，数据源可以是集合、数组或 IO 资源。

从操作角度来看，流与集合是不同的：流不存储数据值; 流的目的是处理数据，它是关于算法与计算的。

如果把集合作为流的数据源，创建流时不会导致数据流动; 如果流的终止操作需要值时,流会从集合中获取值; 流只使用一次。

流中心思想是延迟计算，流直到需要时才计算值。

Stream可以由数组或集合创建，对流的操作分为两种：
* 中间操作，每次返回一个新的流，可以有多个。
* 终端操作，每个流只能进行一次终端操作，终端操作结束后流无法再次使用。终端操作会产生一个新的集合或值。

Stream 有几个特性：
* stream 不存储数据，而是按照特定的规则对数据进行计算，一般会输出结果。
* stream 不会改变数据源，通常情况下会产生一个新的集合或一个值。
* stream 具有延迟执行特性，只有调用终端操作时，中间操作才会执行。

# 2. Stream 的创建
```java
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
```
stream 和 parallelStream 的简单区分： 
* stream是顺序流，由主线程按顺序对流执行操作
* parallelStream 是并行流，内部以多线程并行执行的方式对流进行操作，但前提是流中的数据处理没有顺序要求。如果流中的数据量足够大，并行流可以加快处速度。
* 除了直接创建并行流，还可以通过 parallel() 把顺序流转换成并行流：`Optional<Integer> findFirst = list.stream().parallel().filter(x->x>6).findFirst();`

# 3. Stream 常用 API

## 3.1 遍历 and 匹配
API：`forEach()/findFirst()/findAny()/anyMatch()`

Stream 也是支持类似集合的遍历和匹配元素的，只是 Stream 中的元素是以 Optional 类型存在的。

```java
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
```

## 3.2 按条件过滤
API：`filter()`

```java
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
```

## 3.3 聚合
API：`max()/min()/count()`
```java
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
```

## 3.4 map() 与 flatMap()
API：`map()/flatMap()`
```java
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
```

## 3.5 归约 reduce()
API：`reduce()`
```java

```