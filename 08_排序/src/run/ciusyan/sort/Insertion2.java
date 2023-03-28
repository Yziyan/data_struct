package run.ciusyan.sort;

/**
 * 插入排序
 */
public class Insertion2<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            int cur = begin;
            E element = array[cur];
            // 与前一个元素比较
            while (cur > 0 && cmp(element, array[cur - 1]) < 0) {
                // 如果前一个元素比我小，将它往后挪动一个位置
                array[cur] = array[--cur];
            }
            // 将其元素放入最后一次挪动的前一个位置
            array[cur] = element;
        }
    }
}
