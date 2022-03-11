package JAVA.Example.java.JavaBase.Generics;

import java.util.ArrayList;
import java.util.List;

public class GenericsTest02 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("11111");
        list.add("22222");
//        list.add(100);
        for (int i = 0; i < list.size(); i ++) {
            String str = (String) list.get(i);
            System.out.println("泛型测试：" + str);
        }
    }

}
