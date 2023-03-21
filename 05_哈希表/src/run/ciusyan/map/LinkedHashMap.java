package run.ciusyan.map;

import java.util.Objects;

public class LinkedHashMap<K, V> extends HashMap<K, V>{

    private LinkedNode<K, V> first;
    private LinkedNode<K, V> last;

    @Override
    public void clear() {
        super.clear();
        first = null;
        last = null;
    }

    @Override
    public boolean containsValue(V value) {
        LinkedNode<K, V> currentNode = first;
        while (currentNode != null) {
            if (Objects.equals(currentNode.value, value)) return true;
            currentNode = currentNode.next;
        }

        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;

        LinkedNode<K, V> currentNode = first;
        while (currentNode != null) {
            if (visitor.visit(currentNode.key, currentNode.value)) return;
            currentNode = currentNode.next;
        }
    }

    /**
     * 用于实现删除后，维护链表引用
     * @param willRemoveNode：想要删除的节点
     * @param removedNode：真正被删除的节点
     */
    @Override
    protected void afterRemove(Node<K, V> willRemoveNode, Node<K, V> removedNode) {
        LinkedNode<K, V> node1 = (LinkedNode<K, V>) willRemoveNode;
        LinkedNode<K, V> node2 = (LinkedNode<K, V>) removedNode;
        if (node1 != node2) { // 说明是删除度为2的情况
            // 1、交换prev
            LinkedNode<K, V> temp = node1.prev;
            node1.prev = node2.prev;
            node2.prev = temp;
            if (node1.prev == null) {
                first = node1;
            } else {
                node1.prev.next = node1;
            }
            if (node2.prev == null) {
                first = node2;
            } else {
                node2.prev.next = node2;
            }

            // 2、交换 next
            temp = node1.next;
            node1.next = node2.next;
            node2.next = temp;
            if (node1.next == null) {
                last = node1;
            } else {
                node1.next.prev = node1;
            }

            if (node2.next == null) {
                last = node2;
            } else {
                node2.next.prev = node2;
            }
        }


        LinkedNode<K, V> prev = node2.prev;
        LinkedNode<K, V> next = node2.next;

        if (prev == null) { // 删除的是头节点的情况
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) { // 删除的是尾节点
            last = prev;
        } else {
            next.prev = prev;
        }
    }

    @Override
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinkedNode<K, V> node = new LinkedNode<>(key, value, parent);

        if (first == null) { // 第一次添加
            first = last = node;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }

        return node;
    }

    private static class LinkedNode<K, V> extends Node<K, V> {
        LinkedNode<K, V> prev;
        LinkedNode<K, V> next;

        LinkedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }
}
