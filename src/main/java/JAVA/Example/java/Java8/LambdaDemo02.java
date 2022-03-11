package JAVA.Example.java.Java8;

import java.util.Locale;

/**
 * 自定义接口式函数的使用
 */
public class LambdaDemo02 {

    // 定义一个方法将函数式接口作为方法参数
    public static String toLowerString(MyFuncInterf<String> myFuncInterf, String origin) {
        return myFuncInterf.getValue(origin);
    }

    public static void main(String[] args) {
        // 将 Lambda 表达式实现的接口作为参数传递
        String value = toLowerString((str) -> {
            return str.toLowerCase(Locale.ROOT);
        }, "ZQF");
        System.out.println(value); // zqf
    }

}
