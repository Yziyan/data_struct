package run.ciusyan.uf;

/**
 * 基于 rank + 路径分裂 的实现
 */
public class UnionFind_QU_R_PS extends UnionFInd_QU_R {

    public UnionFind_QU_R_PS(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) { // 1 3 5 7
        rangeCheck(v);

        while (v != parents[v]) {
            // 先将父节点备份，要不然一会找不到了
            int p = parents[v];

            // 将v嫁接到祖父节点上
            parents[v] = parents[p];

            // 让父节点也进行嫁接操作
            v = p;
        }

        // 最终肯定是 v = parents[v] 也就是说，v就是根节点了
        return v;
    }
}
