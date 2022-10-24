package run.ciusyan.list.impl;

import run.ciusyan.list.AbstractList;

/**
 * 双向链表
 */
public class DoubleLinkedList<E> extends AbstractList<E> {
    /**
     * 头结点
     */
    private Node<E> first;
    /**
     * 尾结点
     */
    private Node<E> last;

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
            last = new Node<>(oldLast, element, null);

            if (oldLast == null) { // index == size == 0【第一次添加元素】
                first = last;
            } else {
                oldLast.next = last;
            }
        } else {
            Node<E> next = node(index);
            Node<E> prev = next.prev;
            Node<E> node = new Node<>(prev, element, next);
            next.prev = node;

            if (prev == null) {  // index == 0【往最前面添加元素】
                first = node;
            } else {
                prev.next = node;
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        Node<E> node = node(index);
        Node<E> prev = node.prev;
        Node<E> next = node.next;

        if (prev == null) { // index == 0【删除第一个元素】
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) { // index == size - 1【删除最后一个元素】
            last = prev;
        } else {
            next.prev = prev;
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
