package JAVA.Example.java.JavaBase.Generics;

import java.util.ArrayList;
import java.util.List;

public class GenericsTest01 {

    public static void main(String[] args) {
        List list = new ArrayList();
        list.add("zqf");
        list.add("szn");
        list.add(100);
        for (int i = 0; i < list.size(); i ++) {
            String str = (String) list.get(i);
            System.out.println("泛型测试：" + str);
        }
    }

}
