package run.ciusyan.set.impl;

import run.ciusyan.list.List;
import run.ciusyan.list.DoubleLinkedList;
import run.ciusyan.set.Set;

/**
 * 内部使用链表实现
 */
public class ListSet<E> implements Set<E> {

    // 组合双向链表
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
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    @Override
    public void add(E element) {
        int index = list.indexOf(element); // 查找元素所在链表的索引【-1代表不存在】
        if (index != list.ELEMENT_NOT_FOUND) { // 以前存在，覆盖原值
            list.set(index, element);
        } else { // 以前不存在，添加
            list.add(element);
        }
    }

    @Override
    public void remove(E element) {
        list.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        if (visitor == null) return;
        int size = list.size(); // 遍历即可
        for (int i = 0; i < size; i++) {
            // 遍历时，发现外界需要返回时，停止遍历
            if (visitor.visit(list.get(i))) return;
        }
    }
}
