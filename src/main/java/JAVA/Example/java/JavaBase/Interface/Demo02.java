package JAVA.Example.java.JavaBase.Interface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@testAnnotation01(id = 1, username = "zqf", password = "123")
@testAnnotation02
public class Demo02 {
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface testAnnotation01 {
    int id();
    String username();
    String password();
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface testAnnotation02 {
    String color() default "Red";
    String name() default "flower";
}
