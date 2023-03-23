package run.ciusyan.sort;

public class Heap extends Sort {
    private int heapSize;

    @Override
    protected void sort() {
        // 原地建堆
        heapSize = array.length;
        // 自下而上的下滤（从最后一个非叶子节点开始）
        for (int i = (heapSize >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }

        // 直至堆里只有一个元素
        while (heapSize > 1) {
            // 交换堆顶元素和堆尾元素，并且堆的索引减一
            swap(0, --heapSize);

            // 下滤操作
            siftDown(0);
        }
    }

    /**
     * 下滤操作
     */
    private void siftDown(int index) {
        int half = heapSize >> 1; // 非叶子节点的数量

        Integer element = array[half]; // 下滤节点的值
        while (index < half) { // 说明之后是叶子节点了，没必要下滤了
            int childIndex = (index << 1) + 1; // 算出左子节点的索引
            Integer child = array[childIndex]; // 默认取出左子节点

            int rightIndex = childIndex + 1;
            if (rightIndex < heapSize && cmpEle(array[rightIndex], child) > 0) {
                // 说明有右子节点，并且值比左边的大
                childIndex = rightIndex;
                child = array[rightIndex];
            }

            // 说明找到位置了
            if (cmpEle(element, child) > 0) break;

            // 来到这里，需要将子节点上移
            array[index] = child;
            index = childIndex;
        }

        // 需要设置下滤节点的值
        array[index] = element;
    }
}
