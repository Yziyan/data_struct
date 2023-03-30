package run.ciusyan.uf;

/**
 * 基于 size 的优化
 */
public class UnionFind_QU_S extends UnionFind_QU {
    private int[] sizes;

    public UnionFind_QU_S(int capacity) {
        super(capacity);

        sizes = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            sizes[i] = 1;
        }
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;

        // 来到这里说明需要将元素少的集合、嫁接到元素多的集合
        if (sizes[p1] < sizes[p2]) {
            parents[p1] = p2; // 少的嫁接到多的
            sizes[p2] += sizes[p1]; // 将少的集合元素数量，合并到多的集合上
        } else {
            parents[p2] = p1;
            sizes[p1] += sizes[p2];
        }
    }
}
