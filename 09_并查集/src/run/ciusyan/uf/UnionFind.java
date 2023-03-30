package run.ciusyan.uf;

/**
 * 并查集的抽象公共类
 */
public abstract class UnionFind {

    /**
     * 有多少个根节点，就有多少个集合
     */
    protected int[] parents;

    public UnionFind(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("并查集容量必须大于0");
        }

        // 初始化集合
        parents = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            // 先用自己作为一个单独的集合，（根节点）
            parents[i] = i;
        }
    }

    /**
     * 查询 v 所属的集合（根节点）
     */
    public abstract int find(int v);

    /**
     * 合并 v1、v2 所属的集合
     */
    public abstract void union(int v1, int v2);

    /**
     * 检查 v1、v2 是否属于同一个集合
     */
    public boolean isSame(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /**
     * 判断元素是否越界 [因为现在使用数组来实现的]
     */
    protected void rangeCheck(int v) {
        if (v < 0 || v >= parents.length) {
            throw new IllegalArgumentException("传入的元素有问题");
        }
    }
}
