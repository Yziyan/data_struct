package run.ciusyan.uf;

/**
 * 基于 rank + 路径压缩 的实现
 */
public class UnionFind_QU_R_PC extends UnionFInd_QU_R {

    public UnionFind_QU_R_PC(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);

        if (parents[v] != v) {
            // 将父节点进行递归调用，
            parents[v] = find(parents[v]);
        }

        // 最终开始递归返回的时候，已经将路径上的parents[v]，赋值为 根节点了
        return parents[v];
    }
}
