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

        return parents[v];
    }
}
