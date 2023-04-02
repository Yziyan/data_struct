package run.ciusyan.heap;

import java.util.Collection;
import java.util.Comparator;

/**
 * 最小堆
 */
public class MinHeap<E> {
    /**
     * 元素数量
     */
    private int size;

    /**
     * 底层使用数组实现
     */
    private E[] elements;

    /**
     * 可让外界传入一个比较器
     */
    private Comparator<E> comparator;

    /**
     * 默认容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    public MinHeap() {
        this(null);
    }

    public MinHeap(Comparator<E> comparator) {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        this.comparator = comparator;
    }

    public MinHeap(Collection<E> collection, Comparator<E> comparator) {
        this.comparator = comparator;

        size = collection == null ? 0 : collection.size();
        if (size == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            int capacity = Math.max(size, DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            int i = 0;
            for (E e : collection) {
                elements[i++] = e;
            }

            heapify();
        }
    }

    /**
     * 批量建堆 + 传入比较器
     * @param elements：需要批量建堆的元素
     * @param comparator：元素的比较器
     */
    public MinHeap(E[] elements, Comparator<E> comparator) {
        this.comparator = comparator;

        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            size = elements.length;
            int capacity = Math.max(size, DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            for (int i = 0; i < elements.length; i++) {
                this.elements[i] = elements[i];
            }

            // 拷贝完元素后。开始批量建堆
            heapify();
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * 获取堆顶元素 （获取最小值）
     */
    public E get() {
        emptyCheck();
        return elements[0];
    }

    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size + 1);
        // 将元素添加到最后一个位置
        elements[size] = element;
        // 执行上滤操作，执行完记得将 size + 1（因为添加了元素）
        siftUp(size++);
    }

    /**
     * 批量添加
     */
    public void addAll(Collection<E> collection) {
        if (collection == null) return;

        collection.forEach(this::add);
    }

    /**
     * 删除堆顶元素
     * @return ：返回被删除的元素
     */
    public E remove() {
        emptyCheck();
        E root = elements[0];
        // 将最后一个元素与放入堆顶
        elements[0] = elements[--size];
        elements[size] = null; // 清理最后一个无用元素
        // 然后从堆顶，执行下滤操作
        siftDown(0);
        return root;
    }

    /**
     * 替换堆顶元素
     * @return ：被取代的元素
     */
    public E replace(E element) {
        elementNotNullCheck(element);

        E root = elements[0];
        elements[0] = element;
        if (size == 0) {
            // 说明以前没有元素，相当添加了第一个元素
            size++;
        } else {
            // 否则进行下滤操作
            siftDown(0);
        }

        return root;
    }

    /**
     * 批量建堆的方式
     *  这里使用 自下而上的下滤
     */
    private void heapify() {
        // 从第一个非叶子节点开始，到第一个元素执行完毕
        for (int i = (size >> 1) -1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * 从 index 位置进行下滤操作
     */
    private void siftDown(int index) {
        E element = elements[index]; // 取出下滤元素
        int half = size >> 1; // 非叶子节点的数量

        // 第一个叶子节点的位置 = 非叶子节点的数量 = 向下取整 (size / 2)

        // 如果下滤位置有子节点（相对它有叶子节点），才执行下滤逻辑
        while (index < half) {
            // 默认取左孩子 （ 2 * index + 1）
            int childIndex = (index << 1) + 1;
            E child = elements[childIndex];

            // 如果有右子节点，并且右子节点比左子节点还小，那么将子节点信息换掉
            int rightIndex = childIndex + 1;
            if (rightIndex < size && compare(elements[rightIndex], child) < 0) {
                childIndex = rightIndex;
                child = elements[rightIndex];
            }

            // 来到这里，用最小的子节点和 下滤元素比较。如果子节点大，那就退出
            if (compare(element, child) < 0) break;

            // 来到这里说明子节点小，先换子节点
            elements[index] = child;
            index = childIndex;
        }

        // 退出循环说明找到了 下滤元素存放的位置
        elements[index] = element;
    }

    /**
     * 从 index 位置进行上滤操作
     */
    private void siftUp(int index) {
        // 获取上滤元素
        E element = elements[index];

        // 不断的与它的父节点比较，看看是否需要交换
        while (index > 0) {
            int parentIndex = (index - 1) >> 1; // 父节点的索引
            E parent = elements[parentIndex]; // 父节点的元素

            // 和父节点比较，如果自己大于等于父节点，就退出比较
            if (compare(element, parent) >= 0) break;

            // 能来到这里，说明只会需要交换，先换父节点的值
            elements[index] = parent;

            // 再将自己变成父节点，进行下一轮比较
            index = parentIndex;
        }

        // 能来到这里，index 位置肯定是 新加元素的位置了
        elements[index] = element;
    }

    /**
     * 扩容方法
     * @param capacity：至少需要的容量
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

        // 来到这里说明需要扩容 扩容为 原先的 1.5 倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;
    }

    /**
     * 比较方法
     */
    private int compare(E e1, E e2) {
        return comparator != null ? comparator.compare(e1, e2) : ((Comparable)e1).compareTo(e2);
    }

    /**
     * 判断元素是否为 null
     */
    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("元素不能为 null");
        }
    }

    /**
     * 判断堆是否为空
     */
    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
    }
}
