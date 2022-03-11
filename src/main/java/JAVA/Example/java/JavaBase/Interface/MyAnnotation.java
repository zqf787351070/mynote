package JAVA.Example.java.JavaBase.Interface;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {

    // 定义静态属性
    String name = "ZQF";
    int age = 25;

    // 定义公共的抽象方法
    String code();

}
