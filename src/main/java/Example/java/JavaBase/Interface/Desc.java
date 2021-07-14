package Example.java.JavaBase.Interface;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Desc {
    enum Color {
        WHITE, GRAYISH, YELLOW
    }
    // 默认颜色为白色
    Color color() default Color.WHITE;
}
