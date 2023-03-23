package run.ciusyan.sort;

import run.ciusyan.tools.Asserts;
import run.ciusyan.tools.Integers;

/**
 * 选择排序
 */
public class Selection {

    public static void sort(Integer[] array) {

        for (int end = array.length - 1; end >= 0 ; end--) {

            // 最大位置的索引，每次扫描的时候，默认是0号位置最大
            int maxIndex = 0;
            for (int begin = 1; begin < end; begin++) {
                if (array[maxIndex] <= array[begin]) { // 注意这里的 = ，为了保证稳定性
                    // 有比 maxIndex 位置还大的元素
                    maxIndex = begin;
                }
            }

            // 扫描所有元素，已经找出最大元素的索引了
            Integer temp = array[end];
            array[end] = array[maxIndex];
            array[maxIndex] = temp;
        }
    }

    public static void test() {
        Integer[] array = Integers.random(10, 1, 100);
        Selection.sort(array);
        Asserts.test(Integers.isAscOrder(array));
    }

}
