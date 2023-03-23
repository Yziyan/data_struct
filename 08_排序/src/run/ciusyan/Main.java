package run.ciusyan;

import run.ciusyan.sort.*;
import run.ciusyan.tools.Asserts;
import run.ciusyan.tools.Integers;

import java.util.Arrays;

public class Main {

    static void testSort(Integer[] array, Sort... sorts) {
        for (Sort sort : sorts) {
            Integer[] newArr = Integers.copy(array);
            sort.sort(newArr);
            Asserts.test(Integers.isAscOrder(newArr));
        }

        Arrays.sort(sorts);

        for (Sort sort : sorts) {
            System.out.println(sort);
        }
    }

    public static void main(String[] args) {
        Integer[] array = Integers.random(10000, 1, 20000);
        testSort(array,
            new Bubble1(),
            new Bubble2(),
            new Bubble3(),
            new Selection(),
            new Heap()
        );
    }
}
