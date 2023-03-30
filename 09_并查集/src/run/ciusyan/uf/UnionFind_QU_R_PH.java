package run.ciusyan.uf;

/**
 * 基于 rank + 路径减半 的实现
 */
public class UnionFind_QU_R_PH extends UnionFInd_QU_R {

    public UnionFind_QU_R_PH(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) { // 1 3 5 7
        rangeCheck(v);

        while (v != parents[v]) {
            // 将自己嫁接到祖父节点上
            parents[v] = parents[parents[v]];

            // 让祖父节点也执行此循环
            v = parents[v]; // 这时候的父节点已经变为祖父节点了。
        }

        // 最终肯定是 v = parents[v] 也就是说，v就是根节点了
        return v;
    }
}
