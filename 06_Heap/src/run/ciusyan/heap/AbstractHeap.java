package run.ciusyan.heap;

import java.util.Comparator;

/**
 * 堆的父类
 */
public abstract class AbstractHeap<E> implements Heap<E> {

    protected int size;

    protected Comparator<E> comparator;

    public AbstractHeap(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 用于比较两个元素的大小
     */
    protected int compare(E e1, E e2) {
        return comparator != null ? comparator.compare(e1, e2) : ((Comparable)e1).compareTo(e2);
    }
}
