package run.ciusyan.sort;

/**
 * 冒泡排序 - 优化1
 */
public class Bubble2 extends Sort {

    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            // 每次扫描，都默认它已经是排好序的
            boolean sorted = true;
            for (int begin = 1; begin <= end; begin++) {
                if (cmp(begin, begin - 1) < 0) { // 说明前一个数比后一个数大

                    swap(begin, begin - 1);

                    // 进入了这里，说明有交换，还不能证明已经排序好了
                    sorted = false;
                }
            }

            // 扫描完成，都还是排好序的，那么可以提前终止排序
            if (sorted) break;
        }
    }
}
