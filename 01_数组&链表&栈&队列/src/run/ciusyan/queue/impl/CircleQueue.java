package run.ciusyan.queue.impl;

import run.ciusyan.queue.Queue;

import java.util.Arrays;

/**
 * 循环队列
 */
public class CircleQueue<E> implements Queue<E> {

    /**
     * 指向队头的索引
     */
    private int front;
    /**
     * 内部维护的数组
     */
    private E[] elements;
    /**
     * 元素个数
     */
    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    public CircleQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void enQueue(E element) {
        ensureCapacity(size + 1);
        elements[index(size)] = element;
        size++;
    }

    @Override
    public E deQueue() {
        E oldElement = elements[front];
        elements[front] = null;
        front = index(1);
        size--;
        return oldElement;
    }

    @Override
    public E front() {
        return elements[front];
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[index(i)] = null;
        }
        front = 0;
        size = 0;
    }

    /**
     * 扩容
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[index(i)];
        }
        elements = newElements;
        front = 0;
    }

    /**
     * 映射物理索引为真实的索引
     * @param index：物理索引
     */
    private int index(int index) {
        index += front;
        int capacity = elements.length;
        // 优化取模运算，仅适用于【capacity ∈ (0, 2 * index]】
        return (index < capacity) ? index : index - capacity;
    }

    @Override
    public String toString() {
        return "CircleQueue{" +
            "front=" + front +
            ", size=" + size +
            ", capacity=" + elements.length +
            ", elements=" + Arrays.toString(elements) +
            '}';
    }
}
