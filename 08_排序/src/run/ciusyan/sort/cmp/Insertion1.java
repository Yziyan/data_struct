package run.ciusyan.sort.cmp;

import run.ciusyan.sort.Sort;

/**
 * 插入排序
 */
public class Insertion1<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            int cur = begin;

            // 1 2 2
            while (cur > 0 && cmp(cur, cur - 1) < 0) {
                swap(cur, cur - 1);
                cur--;
            }
        }
    }
}
