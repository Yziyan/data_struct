package run.ciusyan.sort.cmp;

import run.ciusyan.sort.Sort;

/**
 * 插入排序 利用二分搜索优化
 */
public class Insertion3<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            // 找到插入位置
            int insertIndex = search(begin);

            // 插入元素
            insert(begin, insertIndex);
        }
    }

    /**
     * 将元素插入到目标位置
     * @param src：源位置
     * @param dest：目标位置
     */
    private void insert(int src, int dest) {
        // 将插入元素（待排序）备份
        E insertEle = array[src];

        for (int i = src; i > dest; i--) {
            array[i] = array[i - 1];
        }

        array[dest] = insertEle;
    }

    /**
     * 利用二分搜索找到 index 位置元素的待插入位置，
     * 已经排好序的数组范围是 [0, index)
     */
    private int search(int index) {
        int begin = 0;
        int end = index;
        int mid;
        while (begin < end) {
            mid = (begin + end) >> 1;

            if (cmp(array[index], array[mid]) < 0) {
                end = mid;
            } else { // cmp(array[index], array[mid]) >= 0
                begin = mid + 1;
            }
        }

        return begin;
    }
}
