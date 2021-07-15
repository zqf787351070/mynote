package Example.java.JavaBase.Reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 动态的创建对象执行方法
 */
public class Demo01 {

    public static void main(String[] args) throws Exception {
        // 通过反射获取类对象 clazz
        Class<?> clazz = Class.forName("Example.java.JavaBase.Reflect.Programmer");

        // 1. 通过无参构造实例化对象
        Programmer programmer01 = (Programmer) clazz.newInstance();
        System.out.println(programmer01); // Programmer{name='null', age=0}

        // 2. 通过有参构造创建对象
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
        Programmer programmer02 = (Programmer) constructor.newInstance("zqf", 25);
        System.out.println(programmer02); // Programmer{name='zqf', age=25}
        Programmer programmer03 = (Programmer) constructor.newInstance("szn", 3);
        System.out.println(programmer03); // Programmer{name='szn', age=3}

        // 3. 通过反射调用普通方法(方法不可私有)
        Method code = clazz.getMethod("code");
        code.invoke(programmer03); // szn is coding...

        // 4. 通过反射操作属性
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(programmer02, "ZQF");
        System.out.println(programmer02); // Programmer{name='ZQF', age=25}
    }

}
