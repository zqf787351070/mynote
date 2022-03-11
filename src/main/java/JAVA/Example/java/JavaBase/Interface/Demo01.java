package JAVA.Example.java.JavaBase.Interface;

import java.lang.annotation.*;

@Person(role = "student")
@Person(role = "son")
@Person(role = "communist")
public class Demo01 {
    String name = "Leo Zhu";

    public static void main(String[] args) {
        Annotation[] annotations = Demo01.class.getAnnotations();
        System.out.println(annotations.length);
        Persons persons = (Persons) annotations[0];
        for (Person p: persons.value()) {
            System.out.println(p.role());
        }
    }
}

@Repeatable(Persons.class)
@interface Person {
    String role() default "";
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Persons {
    Person[] value();
}
