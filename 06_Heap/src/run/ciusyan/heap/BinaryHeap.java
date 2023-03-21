package run.ciusyan.heap;

import run.ciusyan.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * 二叉堆（大顶堆）
 */
@SuppressWarnings("unchecked")
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {

    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap() {
        this(null);
    }

    public BinaryHeap(Comparator<E> comparator) {
        super(comparator);
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element); // 判空
        ensureCapacity(size + 1); // 确保容量足够
        elements[size++] = element; // 将元素添加到数组的最后
        siftUp(size - 1); // 进行上滤操作，从新添加的元素开始
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E replace(E element) {
        return null;
    }

    /**
     * 上滤操做
     * @param index：进行上滤元素的索引
     */
    private void siftUp(int index) {
        E e = elements[index]; // 上滤节点的值
        while (index > 0) {
            int pIndex = (index - 1) >> 1; // 计算父节点的索引
            E p = elements[pIndex];

            if (compare(e, p) <= 0) return; // 如果上滤节点 <= 父节点时，说明可以终止上滤了

            // 来到这里，需要将父节点下移，然后进入下一轮循环
            elements[index] = p;
            index = pIndex; // 上滤节点已经变换位置了，需要改变索引
        }

        // 最后设置上滤节点的真实位置
        elements[index] = e;
    }

    /**
     * 用于确保容量足够，
     * 不够时扩容为原先的 1.5 倍
     * @param capacity：至少所需容量
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];

        // 挨个拷贝元素
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;
    }

    /**
     * 用于检查是否为 空
     */
    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
    }

    /**
     * 用于检查元素是否为 null
     */
    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("元素不能为 null");
        }
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((int) node << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        int index = ((int) node << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        Integer index = (Integer) node;
        return elements[(int)node];
    }
}
