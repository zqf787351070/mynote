package Example.java.JavaBase.Reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射解决泛型问题
 */
public class Demo03 {

    public static void main(String[] args) throws Exception {
        List<String> list = new ArrayList<>();
        list.add("曹操");
        list.add("刘备");
        // list.add(18); 不符合 String 类型，编译器会报错

        // 利用反射解决上述问题
        Class<?> clazz = list.getClass();
        Method add = clazz.getMethod("add", Object.class);
        add.invoke(list, 18);
        System.out.println(list); // [曹操, 刘备, 18]
    }

}
