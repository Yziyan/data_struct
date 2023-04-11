package run.ciusyan;

import run.ciusyan.match.BruteForce;
import run.ciusyan.match.KMP;
import run.ciusyan.tools.Asserts;

public class Main {
    public static void main(String[] args) {
        String text = "Hello world";
        Asserts.test(BruteForce.indexOf(text, "H") == 0);
        Asserts.test(BruteForce.indexOf(text, "or") == 7);
        Asserts.test(BruteForce.indexOf(text, "d") == 10);
        Asserts.test(BruteForce.indexOf(text, "yan") == -1);


        Asserts.test(KMP.indexOf(text, "H") == 0);
        Asserts.test(KMP.indexOf(text, "or") == 7);
        Asserts.test(KMP.indexOf(text, "d") == 10);
        Asserts.test(KMP.indexOf(text, "yan") == -1);
    }

    static void test1() {

    }

}
