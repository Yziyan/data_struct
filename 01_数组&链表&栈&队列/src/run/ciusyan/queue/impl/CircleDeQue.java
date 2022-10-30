package run.ciusyan.queue.impl;

import run.ciusyan.queue.DeQue;

import java.util.Arrays;

/**
 * 循环双端队列
 */
public class CircleDeQue<E> implements DeQue<E> {

    private int front;
    private int size;
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public CircleDeQue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void enQueueFront(E element) {
        ensureCapacity(size + 1);
        front = index(-1);
        elements[front] = element;
        size++;
    }

    @Override
    public E deQueueRear() {
        int rearIndex = index(size - 1);
        E oldElement = elements[rearIndex];
        elements[rearIndex] = null;
        size--;
        return oldElement;
    }

    @Override
    public E rear() {
        return elements[index(size - 1)];
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
        size = 0;
        front = 0;
    }

    /**
     * 真实的索引
     * @param index：物理索引
     */
    private int index(int index) {
        index += front;
        int capacity = elements.length;

        // return (index < 0) ? index + capacity : index % capacity;

        if (index < 0) {
            return index + capacity;
        }
        // 优化取模运算，仅适用于【capacity ∈ (0, 2 * index]】
        return (index < capacity) ? index : index - capacity;
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
