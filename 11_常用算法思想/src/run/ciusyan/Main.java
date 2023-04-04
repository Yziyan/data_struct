package run.ciusyan;

import run.ciusyan.recursion.Fib;
import run.ciusyan.tools.Times;

public class Main {
    public static void main(String[] args) {
        testFib();
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
