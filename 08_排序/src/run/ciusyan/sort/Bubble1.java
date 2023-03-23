package run.ciusyan.sort;

/**
 * 冒泡排序 - 基础版本
 */
public class Bubble1 extends Sort {

    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            for (int begin = 1; begin <= end; begin++) {
                if (cmp(begin, begin - 1) < 0) { // 说明前一个数比后一个数大

                    swap(begin, begin - 1);
                }
            }
        }
    }

}
