package run.ciusyan;

import run.ciusyan.tools.Asserts;
import run.ciusyan.tools.Times;
import run.ciusyan.uf.UnionFInd_QF;
import run.ciusyan.uf.UnionFind;
import run.ciusyan.uf.UnionFind_QU;
import run.ciusyan.uf.UnionFind_QU_S;

public class Main {

    private final static int COUNT = 500000;

    public static void main(String[] args) {
        test01(new UnionFInd_QF(12));
        test01(new UnionFind_QU(12));
        test01(new UnionFind_QU_S(12));

//        test02(new UnionFInd_QF(COUNT));
//        test02(new UnionFind_QU(COUNT));
        test02(new UnionFind_QU_S(COUNT));
    }


    static void test01( UnionFind uf) {
        uf.union(0, 1);
        uf.union(0, 3);
        uf.union(0, 4);
        uf.union(2, 3);
        uf.union(2, 5);

        uf.union(6, 7);

        uf.union(8, 10);
        uf.union(9, 10);
        uf.union(9, 11);

        Asserts.test(!uf.isSame(2, 7));

        uf.union(4, 6);

        Asserts.test(uf.isSame(2, 7));
    }

    static void test02(UnionFind uf) {
        Times.test(uf.getClass().getSimpleName(), () -> {
            for (int i = 0; i < COUNT; i++) {
                uf.union((int)(Math.random() * COUNT), (int)(Math.random() * COUNT));
            }

            for (int i = 0; i < COUNT; i++) {
                uf.isSame((int)(Math.random() * COUNT), (int)(Math.random() * COUNT));
            }
        });
    }
}
