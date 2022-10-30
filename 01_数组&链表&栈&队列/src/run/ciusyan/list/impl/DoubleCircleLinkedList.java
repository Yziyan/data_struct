package run.ciusyan.list.impl;

import run.ciusyan.list.AbstractList;

/**
 * 双向循环链表
 */
public class DoubleCircleLinkedList<E> extends AbstractList<E> {
    /**
     * 头结点
     */
    private Node<E> first;
    /**
     * 尾结点
     */
    private Node<E> last;
    /**
     * 当前所指向的节点
     */
    private Node<E> current;

    /**
     * 内部的节点类
     */
    private static class Node<E> {
        /**
         * 具体的元素
         */
        E element;
        /**
         * 前驱节点
         */
        Node<E> prev;
        /**
         * 后继节点
         */
        Node<E> next;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder();

            if (prev != null) {
                sb.append(prev.element);
            } else {
                sb.append("null");
            }

            sb.append("_").append(element).append("_");

            if (next != null) {
                sb.append(next.element);
            } else {
                sb.append("null");
            }
            return sb.toString();
        }
    }

    @Override
    public E get(int index) {
        return node(index).element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        checkIndexForAdd(index);

        if (index == size) { // index == size【往最后面添加元素】
            Node<E> oldLast = last;
            last = new Node<>(oldLast, element, first);

            if (oldLast == null) { // index == size == 0【第一次添加元素】
                first = last;
                last.next = last;
                first.prev = first;
            } else {
                oldLast.next = last;
                first.prev = last;
            }
        } else {
            Node<E> next = node(index);
            Node<E> prev = next.prev;
            Node<E> node = new Node<>(prev, element, next);
            next.prev = node;
            prev.next = node;

            if (index == 0) {  // index == 0【往最前面添加元素】
                first = node;
            }

        }
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        return remove(node(index));
    }

    /**
     * 删除current所指向的节点
     */
    public E remove() {
        if (current == null) return null;
        Node<E> next = current.next;
        E element = remove(current);
        if (size == 0) {
          current = null;
        } else {
            current = next;
        }
        return element;
    }

    /**
     * 重置 current 的指向
     */
    public void reset() {
        current = first;
    }

    /**
     * 让current往后走一步
     * @return ：最新指向的元素
     */
    public E next() {
        if (current == null) return null;
        current = current.next;
        return current.element;
    }

    /**
     * 根据节点删除对应的元素
     * @param node：待删的节点
     * @return ：被删除节点的元素
     */
    private E remove(Node<E> node) {
        if (size == 1) {
            first = null;
            last = null;
        } else {
            Node<E> prev = node.prev;
            Node<E> next = node.next;
            prev.next = next;
            next.prev = prev;

            if (node == first) { // index == 0【删除第一个元素】
                first = next;
            }

            if (node == last) { // index == size - 1【删除最后一个元素】
                last = prev;
            }
        }
        size--;
        return node.element;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = first;
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (node.element == null) return i;
                node = node.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) return i;
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
    }

    /**
     * 获取对应索引位置的节点
     * @param index：索引
     * @return ：节点对象
     */
    private Node<E> node(int index) {
        checkIndex(index);
        Node<E> node;
        if (index < (size >> 1)) {
            // index < (size / 2) 从前往后找
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            // 从后往前找
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LinkedList{size=").append(size).append(", elements=[");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(node);
            node = node.next;
        }

        sb.append("]}");
        return sb.toString();
    }
}
