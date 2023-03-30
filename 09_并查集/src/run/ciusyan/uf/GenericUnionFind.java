package run.ciusyan.uf;

import java.util.HashMap;
import java.util.Objects;

/**
 * 通用版 并查集
 */
public class GenericUnionFind<V> {

    // 表示所有集合
    private HashMap<V, Node<V>> sets;

    public GenericUnionFind() {
        this.sets = new HashMap<>();
    }

    /**
     * 将元素初始化成个集合
     */
    public void makeSet(V v) {
        if (sets.containsKey(v)) return;

        // 将其独立作为一个集合
        sets.put(v, new Node<>(v));
    }

    /**
     * 查找元素的属于的集合，(根节点)
     * @param v：查找元素
     * @return ：根节点
     */
    public V find(V v) {
        Node<V> node = findNode(v);

        return node == null ? null : node.value;
    }

    /**
     * 将 v1 和 v2 合并到一个集合
     */
    public void union(V v1, V v2) {
        Node<V> p1 = findNode(v1);
        Node<V> p2 = findNode(v2);
        if (p1 == null || p2 == null) return;
        if (Objects.equals(p1.value, p2.value)) return;

        // 来到这里说明需要进行嫁接操作，根据 rank 比较
        if (p1.rank < p2.rank) { // 说明 p1 集合矮
            p1.parent = p2; // 将 矮的集合嫁接到 高的集合上
        } else if (p1.rank > p2.rank) { // 说明 p2 集合矮
            p2.parent = p1;
        } else { // 说明集合一样高
            // 嫁接谁都行
            p1.parent = p2;
            // 但是增加集合高度得对应
            p2.rank++;
        }
    }

    /**
     * 查看 v1 和 v2 是否属于一个集合
     */
    public boolean isSame(V v1, V v2) {
        return Objects.equals(find(v1), find(v2));
    }

    /**
     * 根据元素查找对应的 根节点
     */
    private Node<V> findNode(V v) {
        Node<V> node = sets.get(v);
        if (node == null) return null;

        // 一直往上找父节点，直至与 父节点是自己
        while (!Objects.equals(node.value, node.parent.value)) {
            // 将自己嫁接到祖父节点
            node.parent = node.parent.parent;
            // 让嫁接后的祖父节点也执行此操作
            node = node.parent;
        }

        return node;
    }

    /**
     * 内部节点类
     */
    private static class Node<V> {
        // 节点存储的值
        V value;
        // 父节点（默认指向自己，独立作为一个集合）
        Node<V> parent = this;
        // 节点的高度（默认为 1）
        int rank = 1;

        public Node(V value) {
            this.value = value;
        }
    }
}
