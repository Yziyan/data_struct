package run.ciusyan.queue;

/**
 * 队列的接口设计
 */
public interface Queue<E> {
    /**
     * 获取队列中元素的数量
     * @return ：元素数量
     */
    int size();

    /**
     * 队列是否为空
     * @return ：是否为 null
     */
    boolean isEmpty();

    /**
     * 入队
     */
    void enQueue(E element);

    /**
     * 出队
     */
    E deQueue();

    /**
     * 获取队头元素
     */
    E front();

    /**
     * 清空队列的所有元素
     */
    void clear();
}
