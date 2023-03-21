package run.ciusyan.heap;

public interface Heap<E> {
    int size();
    boolean isEmpty();
    void clear();
    void add(E element);
    E get();
    // 删除堆顶元素
    E remove();
    // 替换堆顶元素
    E replace(E element);
}
