package run.ciusyan;

import run.ciusyan.recursion.ClimbStairs;
import run.ciusyan.recursion.Fib;
import run.ciusyan.recursion.Hanoi;
import run.ciusyan.tools.Times;

public class Main {
    public static void main(String[] args) {
        testHanoi();
    }

    static void testHanoi() {
        Hanoi hanoi = new Hanoi();
        hanoi.hanoi(3, "A", "B", "C");
    }

    static void testClimbStairs() {
        int n = 44;
        ClimbStairs climbStairs = new ClimbStairs();

        Times.test("climbStairs1", () -> {
            System.out.println(climbStairs.climbStairs1(n));
        });

        Times.test("fib5", () -> {
            System.out.println(climbStairs.climbStairs2(n));
        });
    }

    static void testFib() {
        int n = 44;
        Fib fib = new Fib();
//        Times.test("fib0", () -> {
//            System.out.println(fib.fib0(n));
//        });

        Times.test("fib1", () -> {
            System.out.println(fib.fib1(n));
        });

        Times.test("fib2", () -> {
            System.out.println(fib.fib2(n));
        });

        Times.test("fib3", () -> {
            System.out.println(fib.fib3(n));
        });

        Times.test("fib4", () -> {
            System.out.println(fib.fib4(n));
        });

        Times.test("fib5", () -> {
            System.out.println(fib.fib4(n));
        });
    }
}
