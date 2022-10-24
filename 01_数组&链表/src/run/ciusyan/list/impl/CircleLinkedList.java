package run.ciusyan.list.impl;

import run.ciusyan.list.AbstractList;

/**
 * 单向循环链表
 */
public class CircleLinkedList<E> extends AbstractList<E> {
    /**
     * 头结点
     */
    private Node<E> first;

    /**
     * 内部的节点类
     */
    private static class Node<E> {
        /**
         * 具体的元素
         */
        E element;
        /**
         * 下一个节点
         */
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(element).append("_").append(next.element);
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
        if (index == 0) {
            Node<E> newFirst = new Node<>(element, first);
            Node<E> last = (size == 0) ? newFirst :node(size - 1);
            first = newFirst;
            last.next = first;
        } else {
            // 拿到索引位置的前一个节点
            Node<E> prev = node(index - 1);
            prev.next = new Node<>(element, prev.next);
        }
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        Node<E> node = first;
        if (index == 0) { // 删除第一个节点
            if (size == 1) { // 只有一个节点的情况
                first = null;
            } else {
                Node<E> last = node(size - 1);
                first = first.next;
                last.next = first;
            }
        } else {
            Node<E> prev = node(index - 1);
            node = prev.next;
            prev.next = node.next;
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
    }

    /**
     * 获取对应索引位置的节点
     * @param index：索引
     * @return ：节点对象
     */
    private Node<E> node(int index) {
        checkIndex(index);
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
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
