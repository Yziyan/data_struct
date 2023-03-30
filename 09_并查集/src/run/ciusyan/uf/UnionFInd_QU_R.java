package run.ciusyan.uf;

/**
 * 基于 rank 的优化
 */
public class UnionFInd_QU_R extends UnionFind_QU {

    private int[] ranks;

    public UnionFInd_QU_R(int capacity) {
        super(capacity);

        ranks = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            ranks[i] = 1;
        }
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;

        // 来到这里说明需要将矮的集合嫁接到高的集合上
        if (ranks[p1] < ranks[p2]) { // 说明 v1 所在集合矮
            // 将 矮的集合 嫁接到 高的集合
            parents[p1] = p2;
        } else if (ranks[p1] > ranks[p2]) { // 说明 v2 所在集合矮
            parents[p2] = p1;
        } else {
            parents[p1] = p2; // 这里 parents[p2] = p1 也行

            // 但是别忘记，需要将对应的树高 + 1
            ranks[p2]++;
        }
    }
}
