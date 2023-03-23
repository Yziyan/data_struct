package run.ciusyan.sort;

import run.ciusyan.tools.Integers;
import run.ciusyan.tools.Times;

/**
 * 冒泡排序
 */
public class Bubble {

    /**
     * 基础版本
     */
    public static void sort1(Integer[] array) {
        for (int end = array.length; end > 0; end--) {
            for (int begin = 1; begin < end; begin++) {
                if (array[begin] < array[begin - 1]) { // 说明前一个数比后一个数大
                    int temp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = temp;
                }
            }
        }
    }

    /**
     * 优化1
     */
    public static void sort2(Integer[] array) {
        for (int end = array.length; end > 0; end--) {
            // 每次扫描，都默认它已经是排好序的
            boolean sorted = true;
            for (int begin = 1; begin < end; begin++) {
                if (array[begin] < array[begin - 1]) { // 说明前一个数比后一个数大
                    int temp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = temp;

                    // 进入了这里，说明有交换，还不能证明已经排序好了
                    sorted = false;
                }
            }

            // 扫描完成，都还是排好序的，那么可以提前终止排序
            if (sorted) break;
        }
    }

    /**
     * 优化2
     */
    public static void sort3(Integer[] array) {
        for (int end = array.length; end > 0; end--) {
            // 用于记录从哪里开始，已经排好序了。默认认为全排序了，然后下一次就不会扫描了
            int sortedIndex = 1;

            for (int begin = 1; begin < end; begin++) {
                if (array[begin] < array[begin - 1]) { // 说明前一个数比后一个数大
                    int temp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = temp;

                    // 进入了这里，说明有交换，给 sortedIndex 赋值，认为从这里开始，之和都不会交换了
                    sortedIndex = begin;
                }
            }

            // 扫描完成，sortedIndex 给 end，表示之和只需要排 [0, sortedIndex]即可，后面的已经排好序了
            end = sortedIndex;
        }
    }

    public static void test1() {
        Integer[] array1 = Integers.random(10000, 1,100000);
        // Integer[] array1 = Integers.ascOrder(1, 10000);
        Integer[] array2 = Integers.copy(array1);

        Times.test("test1：bubble sort 1", () -> Bubble.sort1(array1));
        Times.test("test1：bubble sort 2", () -> Bubble.sort2(array2));
    }

    public static void test2() {
        Integer[] array1 = Integers.random(10000, 1,100000);
        // Integer[] array1 = Integers.ascOrder(1, 10000);
        Integer[] array2 = Integers.copy(array1);
        Integer[] array3 = Integers.copy(array1);

        Times.test("test2：bubble sort 1", () -> Bubble.sort1(array1));
        Times.test("test2：bubble sort 2", () -> Bubble.sort2(array2));
        Times.test("test2：bubble sort 3", () -> Bubble.sort3(array3));
    }
}
