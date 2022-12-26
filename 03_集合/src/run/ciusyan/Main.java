package run.ciusyan;

import run.ciusyan.set.Set;
import run.ciusyan.set.impl.ListSet;
import run.ciusyan.set.impl.RBTreeSet;

import java.util.Random;

public class Main {

    static void test1(Set<Integer> set) {
        for (int i = 1; i <= 30; i++) {
            set.add(i);
        }
        set.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });

        for (int i = 1; i <= 30; i++) {
            set.remove(i);
        }
        System.out.println("------------");
        set.traversal(new Set.Visitor<>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }

    static void test2() {
        test(new ListSet<>()); // 使用链表实现
    }
    static void test3() {
        test(new RBTreeSet<>()); // 使用红黑树实现
    }
    static void test(Set<Integer> set) {
        int size = 10000000;
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            set.add(random.nextInt(10000));
        }
        for (int i = 0; i < size; i++) {
            set.contains(random.nextInt(10000));
        }
        for (int i = 0; i < size; i++) {
            set.remove(random.nextInt(10000));
        }
    }
    public static void main(String[] args) {
        Times.test("内部使用链表", Main::test2);
        Times.test("内部使用红黑树", Main::test3);
    }
}
