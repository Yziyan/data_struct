package run.ciusyan;

import run.ciusyan.map.Map;
import run.ciusyan.map.Student;
import run.ciusyan.map.TreeMap;;import java.util.Scanner;

public class Main {

    static void test1() {
        Map<Integer, String> map = new TreeMap<>();
        map.put(1, "zhiyan");
        map.put(10, "是是是");
        map.put(3, "哈哈哈");
        map.put(2, "嘿嘿嘿");
        map.put(6, "test test");
        map.put(1, "Ciusyan");

        map.traversal(new Map.Visitor<>() {
            @Override
            public boolean visit(Integer key, String value) {
                System.out.println(key + "：" + value);
                return false;
            }
        });

        System.out.println(map.remove(1));
        System.out.println(map.remove(2));
        System.out.println(map.remove(22));
        System.out.println(map.containsValue("哈哈哈"));
        map.clear();
        System.out.println(map.containsKey(6));
        System.out.println(map.size());
    }


    final int b;
    public Main() {
        b = 10;
    }
    public static void main(String[] args) {
        final int a; // 这种叫使用时常量。在使用时必须赋初始值
        a = 10;
        System.out.println(a); // 比如说这里使用了 a ，就必须赋值后才能使用
    }

}
