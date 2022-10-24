package run.ciusyan.queue;

/**
 * 双端队列接口设计
 */
public interface DeQue<E> extends Queue<E> {

    /**
     * 从队头入队
     * @param element：待入队的元素
     */
    void enQueueFront(E element);

    /**
     * 从队尾出队
     * @return ：出队的元素
     */
    E deQueueRear();

    /**
     * 从队尾获取元素
     */
    E rear();

}
