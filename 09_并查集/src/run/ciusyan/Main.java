package run.ciusyan;

import run.ciusyan.tools.Asserts;
import run.ciusyan.tools.Times;
import run.ciusyan.uf.*;

public class Main {

    private final static int COUNT = 500000;

    public static void main(String[] args) {
//        test(new UnionFInd_QF(COUNT));
//        test(new UnionFind_QU(COUNT));
        test(new UnionFind_QU_S(COUNT));
        test(new UnionFInd_QU_R(COUNT));
        test(new UnionFind_QU_R_PC(COUNT));
        test(new UnionFind_QU_R_PS(COUNT));
    }


    static void test(UnionFind uf) {uf.union(0, 1);
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
