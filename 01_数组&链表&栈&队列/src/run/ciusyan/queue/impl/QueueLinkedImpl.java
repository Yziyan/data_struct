package run.ciusyan.queue.impl;

import run.ciusyan.list.List;
import run.ciusyan.list.impl.DoubleLinkedList;
import run.ciusyan.queue.Queue;

/**
 * 队列 - 用双向链表实现
 */
public class QueueLinkedImpl<E> implements Queue<E> {

    private final List<E> list = new DoubleLinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void enQueue(E element) {
        list.add(element);
    }

    @Override
    public E deQueue() {
        return list.remove(0);
    }

    @Override
    public E front() {
        return list.get(0);
    }

    @Override
    public void clear() {
        list.clear();
    }
}
