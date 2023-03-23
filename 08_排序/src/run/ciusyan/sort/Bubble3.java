package run.ciusyan.sort;

/**
 * 冒泡排序 - 优化2
 */
public class Bubble3<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            // 用于记录从哪里开始，已经排好序了。默认认为全排序了，然后下一次就不会扫描了
            int sortedIndex = 1;

            for (int begin = 1; begin <= end; begin++) {
                if (cmp(begin, begin - 1) < 0) { // 说明前一个数比后一个数大

                    swap(begin, begin - 1);

                    // 进入了这里，说明有交换，给 sortedIndex 赋值，认为从这里开始，之和都不会交换了
                    sortedIndex = begin;
                }
            }

            // 扫描完成，sortedIndex 给 end，表示之和只需要排 [0, sortedIndex]即可，后面的已经排好序了
            end = sortedIndex;
        }
    }
}
