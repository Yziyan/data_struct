package run.ciusyan;

import run.ciusyan.tools.Asserts;
import run.ciusyan.tools.Times;
import run.ciusyan.uf.*;

public class Main {

    private final static int COUNT = 5000000;

    public static void main(String[] args) {
//        test(new UnionFInd_QF(COUNT));
//        test(new UnionFind_QU(COUNT));
//        test(new UnionFind_QU_S(COUNT));
//        test(new UnionFInd_QU_R(COUNT));
//        test(new UnionFind_QU_R_PC(COUNT));
//        test(new UnionFind_QU_R_PS(COUNT));
//        test(new UnionFind_QU_R_PH(COUNT));
//        test2(new GenericUnionFind<>());

        test3();

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

    static void test2(GenericUnionFind<Integer> uf) {
        for (int i = 0; i < COUNT; i++) {
            uf.makeSet(i);
        }

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

        Times.test(uf.getClass().getSimpleName(), () -> {
            for (int i = 0; i < COUNT; i++) {
                uf.union((int)(Math.random() * COUNT),
                    (int)(Math.random() * COUNT));
            }

            for (int i = 0; i < COUNT; i++) {
                uf.isSame((int)(Math.random() * COUNT),
                    (int)(Math.random() * COUNT));
            }
        });
    }

    static void test3() {
        GenericUnionFind<Student> uf = new GenericUnionFind<>();
		Student stu1 = new Student(1, "jack");
		Student stu2 = new Student(2, "rose");
		Student stu3 = new Student(3, "jack");
		Student stu4 = new Student(4, "rose");
		uf.makeSet(stu1);
		uf.makeSet(stu2);
		uf.makeSet(stu3);
		uf.makeSet(stu4);

		uf.union(stu1, stu2);
		uf.union(stu3, stu4);


		Asserts.test(!uf.isSame(stu2, stu3));
		Asserts.test(uf.isSame(stu3, stu4));
		Asserts.test(!uf.isSame(stu1, stu3));

        uf.union(stu1, stu4);

        Asserts.test(uf.isSame(stu2, stu3));
        Asserts.test(uf.isSame(stu3, stu4));
        Asserts.test(uf.isSame(stu1, stu3));
    }
}
