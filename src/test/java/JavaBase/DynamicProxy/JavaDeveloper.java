package JavaBase.DynamicProxy;

import java.io.Serializable;

/**
 * 创建真实角色
 */
public class JavaDeveloper implements Developer, Serializable {

    private String name;

    public JavaDeveloper(String name) {
        this.name = name;
    }

    @Override
    public void code() {
        System.out.println(this.name + " is coding java !");
    }

    @Override
    public void debug() {
        System.out.println(this.name + " is debugging java !");
    }
}
