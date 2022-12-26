package run.ciusyan.list;

public abstract class AbstractList<E> implements List<E> {

    /**
     * 数组的元素个数
     */
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    @Override
    public void add(E element) {
        add(size, element);
    }

    @Override
    public E remove(E element) {
        final int index = indexOf(element);
        if (index != List.ELEMENT_NOT_FOUND) {
            return remove(index);
        }
        return element;
    }

    /**
     * 判断索引是否越界
     * @param index：索引
     */
    protected void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throwBoundMsg(index);
        }
    }

    /**
     * 判断添加方法是否越界
     * @param index：索引
     */
    protected void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throwBoundMsg(index);
        }
    }

    /**
     * 抛出数组越界的异常
     */
    private void throwBoundMsg(int index) {
        throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
    }

}
