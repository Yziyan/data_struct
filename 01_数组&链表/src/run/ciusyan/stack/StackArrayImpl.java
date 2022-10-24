package run.ciusyan.stack;

import run.ciusyan.list.List;
import run.ciusyan.list.impl.ArrayList;

/**
 * 栈 - 用动态数组实现
 */
public class StackArrayImpl<E> implements Stack<E>{

    private final List<E> list = new ArrayList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void push(E element) {
        list.add(element);
    }

    @Override
    public E pop() {
        return list.remove(list.size() - 1);
    }

    @Override
    public E top() {
        return list.get(list.size() - 1);
    }

    @Override
    public void clear() {
        list.clear();
    }
}
