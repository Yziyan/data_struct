package run.ciusyan.list.impl;

import run.ciusyan.list.AbstractList;

/**
 * 虚拟头结点的链表
 */
public class LinkedListVNode<E> extends AbstractList<E> {
    /**
     * 头结点
     */
    private Node<E> first;

    /**
     * 增加一个虚拟头结点
     */
    public LinkedListVNode() {
        first = new Node<>(null, null);
    }

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
        // 拿到索引位置的前一个节点
        Node<E> prev = (index == 0) ? first : node(index - 1);
        prev.next = new Node<>(element, prev.next);
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        Node<E> prev = (index == 0) ? first : node(index - 1);
        Node<E> node = prev.next;
        prev.next = node.next;
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
        Node<E> node = first.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LinkedList{size=").append(size).append(", elements=[");
        Node<E> node = first.next;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(node.element);
            node = node.next;
        }

        sb.append("]}");
        return sb.toString();
    }
}
