package run.ciusyan.queue;

import run.ciusyan.heap.BinaryHeap;
import run.ciusyan.heap.Heap;

import java.util.Comparator;

/**
 *  优先级队列
 *  用二叉堆实现
 */
public class PriorityQueue<E> {

    private Heap<E> heap;

    public PriorityQueue() {
        this(null);
    }

    public PriorityQueue(Comparator<E> comparator) {
        this.heap = new BinaryHeap<>(comparator);
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void enQueue(E element) {
        heap.add(element);
    }

    public E deQueue() {
        return heap.remove();
    }

    public E front() {
        return heap.get();
    }

    public void clear() {
        heap.clear();
    }
}
