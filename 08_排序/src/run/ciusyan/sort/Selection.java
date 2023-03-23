package run.ciusyan.sort;

import run.ciusyan.tools.Asserts;
import run.ciusyan.tools.Integers;

/**
 * 选择排序
 */
public class Selection<E extends Comparable<E>> extends Sort<E> {

    @Override
    public void sort() {

        for (int end = array.length - 1; end >= 0 ; end--) {

            // 最大位置的索引，每次扫描的时候，默认是0号位置最大
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                if (cmp(maxIndex, begin) <= 0) { // 注意这里的 = ，为了保证稳定性
                    // 有比 maxIndex 位置还大的元素
                    maxIndex = begin;
                }
            }

            // 扫描所有元素，已经找出最大元素的索引了，交换它们的值
            swap(end, maxIndex);
        }
    }
}
