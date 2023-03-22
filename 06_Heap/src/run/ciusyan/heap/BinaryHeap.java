package run.ciusyan.heap;

import run.ciusyan.Main;
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
        this(null, null);
    }

    public BinaryHeap(Comparator<E> comparator) {
        this(null, comparator);
    }

    public BinaryHeap(E[] elements) {
        this(elements, null);
    }

    public BinaryHeap(E[] elements, Comparator<E> comparator) {
        super(comparator);

        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            size = elements.length;
            int capacity = Math.max(size, DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            // 做深拷贝，小心外界乱用
            for (int i = 0; i < size; i++) {
                this.elements[i] = elements[i];
            }

            // 批量建堆
            heapify();
        }

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
        emptyCheck(); // 检查堆是不是空的
        return elements[0];
    }

    @Override
    public E remove() {
        emptyCheck(); // 检查堆是不是空的

        E root = elements[0]; // 需要返回被删除元素的值
        int lastIndex = --size;
        elements[0] = elements[lastIndex]; // 将最后一个元素赋值给首元素
        elements[lastIndex] = null; // 删除最后一个元素
        siftDown(0); // 下滤操作

        return root;
    }

    @Override
    public E replace(E element) {
        elementNotNullCheck(element);

        E root = elements[0]; // 用于返回
        elements[0] = element;
        if (size == 0) {
            // 说明加了第一个元素
            size++;
        } else {
            // 如果有元素，才下滤
            siftDown(0);
        }

        return root;
    }

    /**
     * 批量建堆
     */
    private void heapify() {

//        // 方法一：自上而下的上滤
//        for (int i = 1; i < size; i++) {
//            siftUp(i);
//        }

        // 方案二：自下而上的下滤
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * 下滤操做
     * @param index：进行下滤元素的索引
     */
    private void siftDown(int index) {
        int half = size >> 1; // 非叶子节点的数量 flower(size / 2)
        // 第一个叶子节点的索引 == 非叶子节点的数量

        E element = elements[index];

        // 如果有叶子节点，才进行下滤操作，否则退出循环
        while (index < half) {

            // 能来到这里，index位置节点只会有2种情况
            //      1、只有左子节点
            //      2、左右子节点都有

            // 默认取出左子节点的索引
            int childIndex = (index << 1) + 1;
            E child = elements[childIndex];

            int rightIndex = childIndex + 1;
            // 取出比较大的子节点做下滤的比较 [右子节点索引] = childIndex + 1
            if (rightIndex < size && compare(elements[rightIndex], child) > 0) {
                // 来到这里说明右子节点比较大
                childIndex = rightIndex;
                child = elements[childIndex];
            }

            // 进行比较操作，用下滤节点，于较大的子节点做比较
            if (compare(element, child) > 0) break;

            // 能来到这里，说明需要将子节点上移
            elements[index] = child;
            index = childIndex;
        }

        elements[index] = element;
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

            if (compare(e, p) <= 0) break; // 如果上滤节点 <= 父节点时，说明可以终止上滤了

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
