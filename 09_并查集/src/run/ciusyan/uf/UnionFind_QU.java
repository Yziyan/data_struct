package run.ciusyan.uf;

public class UnionFind_QU extends UnionFind {

    public UnionFind_QU(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);

        while (v != parents[v]) {
            // 将父节点赋值给 v
            v = parents[v];
        }

        // 来到这里，说明 v = parents[v]，它就是根节点了
        return v;
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;

        // 来到这里，说明要将 v1 的根节点，换为 v2 的根节点

        parents[p1] = p2;
    }
}
