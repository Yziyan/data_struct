package run.ciusyan;

import run.ciusyan.sort.*;
import run.ciusyan.sort.cmp.*;
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
              new Counting()
//            new Insertion3<>(),
//            new Bubble3<>(),
//            new Selection<>(),
//            new Heap<>(),
//            new Merge<>(),
//            new Quick<>(),
//            new Shell<>()
        );
    }

    static void test2() {
        int[] array = {2, 3, 5, 6, 8, 10};
        Asserts.test(BinarySearch.indexOf(array, 3) == 1);
        Asserts.test(BinarySearch.indexOf(array, 6) == 3);
        Asserts.test(BinarySearch.indexOf(array, 2) == 0);
        Asserts.test(BinarySearch.indexOf(array, 10) == 5);
        Asserts.test(BinarySearch.indexOf(array, 20) == -1);

        Asserts.test(BinarySearch.search(array, 3) == 2);
        Asserts.test(BinarySearch.search(array, 6) == 4);
        Asserts.test(BinarySearch.search(array, 2) == 1);
        Asserts.test(BinarySearch.search(array, 10) == 6);
        Asserts.test(BinarySearch.search(array, 20) == 6);
        Asserts.test(BinarySearch.search(array, 1) == 0);
    }

    public static void main(String[] args) {
        test1();
    }
}
