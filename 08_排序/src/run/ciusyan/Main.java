package run.ciusyan;

import run.ciusyan.sort.Bubble;
import run.ciusyan.sort.Selection;
import run.ciusyan.tools.Integers;
import run.ciusyan.tools.Times;

public class Main {

    static void testBubble() {
        Bubble.test1();
        Bubble.test2();
    }

    static void testSelection() {
        Selection.test();
    }

    public static void main(String[] args) {
        // testBubble();
        testSelection();
    }
}
