package run.ciusyan.queue.impl;

import run.ciusyan.list.List;
import run.ciusyan.list.impl.DoubleLinkedList;
import run.ciusyan.queue.DeQue;

public class DeQueLinkedImpl<E> implements DeQue<E> {

    private final List<E> list = new DoubleLinkedList<>();

    @Override
    public void enQueueFront(E element) {
        list.add(0, element);
    }

    @Override
    public E deQueueRear() {
        return list.remove(list.size() - 1);
    }

    @Override
    public E rear() {
        return list.get(list.size() - 1);
    }

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
        list.add(list.size(), element);
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

    @Override
    public String toString() {
        return "DeQueueLinkedImpl{" +
            "list=" + list +
            '}';
    }
}
