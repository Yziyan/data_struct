package run.ciusyan;

import run.ciusyan.uf.UnionFInd_QF;
import run.ciusyan.uf.UnionFind;

public class Main {

    static void test01() {
        UnionFind uf = new UnionFInd_QF(12);
        uf.union(0, 1);
        uf.union(0, 3);
        uf.union(0, 4);
        uf.union(2, 3);
        uf.union(2, 5);

        uf.union(6, 7);

        uf.union(8, 10);
        uf.union(9, 10);
        uf.union(9, 11);

        //uf.union(4, 6);

        System.out.println(uf.isSame(2, 7));

    }

    public static void main(String[] args) {
        test01();
    }
}
