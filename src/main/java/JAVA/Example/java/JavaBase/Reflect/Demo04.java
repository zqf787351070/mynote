package JAVA.Example.java.JavaBase.Reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 通过反射获得注解信息
 */
public class Demo04 {

    public static void main(String[] args) throws Exception {
        // 获取类对象
        Class<?> clazz = Class.forName("JAVA.Example.java.JavaBase.Reflect.Programmer");

        // 1. 通过反射获取类注解
        ClassAnno classAnno = clazz.getAnnotation(ClassAnno.class);
        System.out.println(classAnno);
        System.out.println(classAnno.classDesc());

        // 2. 通过反射获取属性注解
        Field name = clazz.getField("name");
        FieldAnno fieldAnno = name.getAnnotation(FieldAnno.class);
        System.out.println(fieldAnno);
        System.out.println(fieldAnno.fieldDesc());

        // 3. 通过反射获取方法注解
        Method debug = clazz.getMethod("debug");
        MethodAnno methodAnno = debug.getAnnotation(MethodAnno.class);
        System.out.println(methodAnno);
        System.out.println(methodAnno.methodDesc());

    }

}
