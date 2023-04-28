package run.ciusyan.Top;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.cn/problems/lru-cache/
 */
public class LRUCache {

    /**
     * 用于记录最多能够缓存的容量
     */
    private int capacity;

    /**
     * 用于存储所有的节点
     */
    private Map<Integer, Node> cache;

    /**
     * 链表的虚拟头结点
     */
    private Node first;

    /**
     * 链表的虚拟尾结点
     */
    private Node last;

    public LRUCache(int capacity) {
        cache = new HashMap<>(capacity);
        this.capacity = capacity;

        // 虚拟头尾，方便编写代码。减少判断
        first = new Node();
        last = new Node();

        first.next = last;
        last.prev = first;
    }

    public int get(int key) {
        // 尝试获取缓存
        Node node = cache.get(key);
        if (node == null) return -1;

        // 来到这里，说明有这个 key，
        //  那么我们需要将这个key，放置到最新最近使用的地方，也就是 first 的后面

        // 先将这个节点删除
        remove(node);
        // 然后将这个节点添加到 fist 的后面
        addAfterFirst(node);

        return node.value;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);
        if (node != null) {
            // 说明这是一个更新操作
            node.value = value;

            // 先将此节点删除，之后再添加到最前面
            remove(node);
        } else {
            // 说明这是一个插入操作
            // 需要先判断容量满了吗
            if (cache.size() == capacity) {
                // 说明已经满了，需要淘汰最不活跃的节点
                //  最后面的是最优先被淘汰的 last.prev

                // 注意这里，不要忘记将 cache 中对应的 key 给删除掉
                // Node removedNode = cache.remove(last.prev.key);
                // remove(removedNode);

                remove(cache.remove(last.prev.key));
            }

            // 来到这里，肯定可以存放了，但是要将此节点放置到最前面
            node = new Node(key, value);
            cache.put(key, node);
        }

        // 然后将此节点放置到最新最活跃的地方
        addAfterFirst(node);
    }

    /**
     * 将 node 添加到 fist节点的后面
     *      说明这个节点是最近被使用过的
     */
    private void addAfterFirst(Node node) {
        // 相当于在first的后面新增 node 节点

        // 将 node 与 first 后面的节点连线
        node.next = first.next;
        first.next.prev = node;

        // 将node与 first 连线
        node.prev = first;
        first.next = node;
    }

    /**
     * 从链表中删除 node 节点
     */
    private void remove(Node node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    /**
     * 内部节点类
     */
    private static class Node {
        int key;
        int value;
        // 前驱节点
        Node prev;
        // 后继节点
        Node next;
        public Node() {}
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
