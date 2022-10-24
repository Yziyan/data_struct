package run.ciusyan.queue.impl;

import run.ciusyan.list.List;
import run.ciusyan.queue.Queue;
import run.ciusyan.stack.Stack;
import run.ciusyan.stack.StackArrayImpl;

/**
 * 队列 - 用栈实现
 */
public class QueueStackImpl<E> implements Queue<E> {

    /**
     * 仅用于 入对
     */
    private final Stack<E> inStack = new StackArrayImpl<>();
    /**
     * 用于 出队、获取队头
     */
    private final Stack<E> outStack = new StackArrayImpl<>();

    @Override
    public int size() {
        return inStack.size() + outStack.size();
    }

    @Override
    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    @Override
    public void enQueue(E element) {
        inStack.push(element);
    }

    @Override
    public E deQueue() {
        checkOutStack();
        return outStack.pop();
    }

    @Override
    public E front() {
        checkOutStack();
        return outStack.top();
    }

    @Override
    public void clear() {
        inStack.clear();
        outStack.clear();
    }

    /**
     * 检查是否需要将 inStack中的元素弹到 outStack中
     */
    private void checkOutStack() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
    }
}
