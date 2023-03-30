package run.ciusyan.uf;

/**
 * 使用 Quick Find 的方式 实现
 */
public class UnionFInd_QF extends UnionFind {
    public UnionFInd_QF(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        // 判断是否越界
        rangeCheck(v);

        // 直接返回 parents[v] 即可，因为它的值就是 该元素的根节点
        return parents[v];
    }

    @Override
    public void union(int v1, int v2) {
        // 找出 v1 和 v2 的父节点
        int p1 = find(v1);
        int p2 = find(v2);

        if (p1 == p2) return;

        // 能来到这里，说明不在一个集合，
        // 将其与v1有相同父节点的元素的父节点设置为 v2

        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == p1) {
                parents[i] = p2;
            }
        }
    }
}
