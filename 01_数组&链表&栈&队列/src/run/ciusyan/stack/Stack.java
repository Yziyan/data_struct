package run.ciusyan.stack;

/**
 * 栈的接口设计
 */
public interface Stack<E> {
    /**
     * 获取栈中元素的数量
     * @return ：元素数量
     */
    int size();

    /**
     * 栈是否为空
     * @return ：是否为 null
     */
    boolean isEmpty();

    /**
     * 入栈
     */
    void push(E element);

    /**
     * 出栈
     */
    E pop();

    /**
     * 获取栈顶元素
     */
    E top();

    /**
     * 清空栈的所有元素
     */
    void clear();
}
