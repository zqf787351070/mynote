package Example.java.JavaBase.Reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 通过反射获取泛型信息
 */
public class Demo02 {

    public void test01(Map<String, Programmer> map, List<Programmer> list) {
        System.out.println("方法: test01()");
    }

    public Map<String, Programmer> test02() {
        System.out.println("方法：test02()");
        return null;
    }

    public static void main(String[] args) throws Exception {
        Method test01 = Demo02.class.getMethod("test01", Map.class, List.class);
        Type[] genericParameterTypes = test01.getGenericParameterTypes();
        for (Type genericParameterType: genericParameterTypes) {
            System.out.println(genericParameterType);
            // java.util.Map<java.lang.String, Example.java.JavaBase.Reflect.Programmer>
            // java.util.List<Example.java.JavaBase.Reflect.Programmer>
        }

        Method test02 = Demo02.class.getMethod("test02");
        Type genericReturnType = test02.getGenericReturnType();
        System.out.println(genericReturnType);
        // java.util.Map<java.lang.String, Example.java.JavaBase.Reflect.Programmer>
    }

}
