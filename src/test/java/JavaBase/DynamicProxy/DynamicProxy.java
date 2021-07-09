package JavaBase.DynamicProxy;

import java.io.Serializable;
import java.lang.reflect.Proxy;

/**
 * 动态代理的实现
 */
public class DynamicProxy implements Serializable {

    public static void main(String[] args) {
        // 创建实际角色
        JavaDeveloper leo = new JavaDeveloper("Leo");

        // 获取代理对象
        Developer leoProxy = (Developer) Proxy.newProxyInstance(leo.getClass().getClassLoader(), leo.getClass().getInterfaces(), ((proxy, method, args1) -> {
            if (method.getName().equals("code")) {
                System.out.println("编码之前要吃饭！");
                method.invoke(leo, args);
            }
            if (method.getName().equals("debug")) {
                System.out.println("调试之前要祈祷！");
                method.invoke(leo, args);
            }
            return null;
        }));

        // 使用代理对象执行方法查看代理结果
        leoProxy.code();
        leoProxy.debug();
    }

}
