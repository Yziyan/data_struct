package run.ciusyan.list;

public interface List<E> {

    /**
     * 找不到元素的返回值
     */
    int ELEMENT_NOT_FOUND = -1;

    /**
     * 获取数组元素的数量
     * @return ：元素数量
     */
    int size();

    /**
     * 数组是否为空
     * @return ：是否为 null
     */
    boolean isEmpty();

    /**
     * 查看某元素是否在数组中
     * @param element：查看元素
     * @return ：是否存在
     */
    boolean contains(E element);

    /**
     * 在数组的末尾添加元素
     * @param element：添加的元素
     */
    void add(E element);

    /**
     * 根据索引获取数组元素
     * @param index：索引
     * @return ：对应元素
     */
    E get(int index);

    /**
     * 修改特定的位置的元素
     * @param index：待替换的索引
     * @param element：待替换的元素
     * @return ：以前的元素
     */
    E set(int index, E element);

    /**
     * 在特定位置添加新元素
     * @param index：待添加位置
     * @param element：待添加的元素
     */
    void add(int index, E element);

    /**
     * 删除某一位置的元素
     * @param index：带删除元素位置
     * @return ：被删除的元素
     */
    E remove(int index);

    /**
     * 删除某一元素
     * @param element：待删除的元素
     * @return ：被删除的元素
     */
    E remove(E element);

    /**
     * 查看某元素的所在的所索引
     * @param element：查找的元素
     * @return ：对应索引
     */
    int indexOf(E element);

    /**
     * 清空数组元素
     */
    void clear();

}
