package run.ciusyan;

import run.ciusyan.sort.*;
import run.ciusyan.tools.Asserts;
import run.ciusyan.tools.Integers;

import java.util.Arrays;

public class Main {

    @SafeVarargs
    static void testSort(Integer[] array, Sort<Integer>... sorts) {
        for (Sort<Integer> sort : sorts) {
            Integer[] newArr = Integers.copy(array);
            sort.sort(newArr);
            Asserts.test(Integers.isAscOrder(newArr));
        }

        Arrays.sort(sorts);

        for (Sort<Integer> sort : sorts) {
            System.out.println(sort);
        }
    }

    static void test1() {
        Integer[] array = Integers.random(10000, 1, 20000);
        testSort(array,
            new Insertion1<>(),
            new Insertion2<>(),
            new Bubble3<>(),
            new Selection<>(),
            new Heap<>()
        );
    }

    static void test2() {
        int[] array = {2, 3, 5, 6, 8, 10};
        Asserts.test(BinarySearch.indexOf(array, 3) == 1);
        Asserts.test(BinarySearch.indexOf(array, 6) == 3);
        Asserts.test(BinarySearch.indexOf(array, 2) == 0);
        Asserts.test(BinarySearch.indexOf(array, 10) == 5);
        Asserts.test(BinarySearch.indexOf(array, 20) == -1);

    }

    public static void main(String[] args) {
        test2();
    }
}
