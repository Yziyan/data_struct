package run.ciusyan;

import run.ciusyan.heap.BinaryHeap;
import run.ciusyan.heap.Heap;
import run.ciusyan.printer.BinaryTrees;

public class Main {

    static void test1() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(68);
        heap.add(72);
        heap.add(43);
        heap.add(25);
        heap.add(50);
        heap.add(38);
        heap.add(90);
        heap.add(20);
        BinaryTrees.println(heap);
        System.out.println(heap.replace(50));
        BinaryTrees.println(heap);
    }

    static void test2() {
        Integer[] eles = {93, 29, 76, 71, 4, 43, 15, 45, 32, 86, 64, 31, 56};
        BinaryHeap<Integer> heap = new BinaryHeap<>(eles);
        BinaryTrees.println(heap);
        eles[0] = 20;
        eles[1] = 21;
        BinaryTrees.println(heap);
    }

    static void test3() {
        Integer[] eles = {93, 29, 76, 71, 4, 43, 15, 45, 32, 86, 64, 31, 56};
        // 传入一个比较器，修改比较策略即可
        BinaryHeap<Integer> heap = new BinaryHeap<>(eles, (o1, o2) -> o2 - o1);
        BinaryTrees.println(heap);
    }

    static void test4() {
        Integer[] eles = {29, 76, 71, 4, 43, 15, 45, 32, 86, 64, 31, 56,
            35, 98, 54, 68, 37, 15, 91, 100, 84, 85, 63, 42, 58, 62, 48,
            16, 4, 93, 86, 14, 29, 59, 94
        };

        // 找出最大的 k 个元素
        int k = 5;

        // 新建一个小顶堆
        BinaryHeap<Integer> heap = new BinaryHeap<>((o1, o2) -> o2 - o1);

        // 扫描数据
        for (Integer ele : eles) {
            if (heap.size() < k) {
                // 将k个元素入堆
                heap.add(ele);
            } else if (heap.get() < ele) {
                heap.replace(ele);
            }
        }

        BinaryTrees.println(heap);
    }

    public static void main(String[] args) {
        test4();
    }

}
