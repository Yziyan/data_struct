package run.ciusyan;

import run.ciusyan.baktraking.NQueens;
import run.ciusyan.baktraking.NQueens2;
import run.ciusyan.baktraking.Queens3;
import run.ciusyan.divideconquer.MaxSubarray;
import run.ciusyan.dp.*;
import run.ciusyan.greedy.Article;
import run.ciusyan.greedy.CoinChange;
import run.ciusyan.greedy.Pirate;
import run.ciusyan.recursion.ClimbStairs;
import run.ciusyan.recursion.Fib;
import run.ciusyan.recursion.Hanoi;
import run.ciusyan.tools.Times;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        testDpLIS();
    }

    static void testDpKnapsack() {
        Knapsack knapsack = new Knapsack();
        int[] values =  {6, 3, 5, 4, 6};
        int[] weights = {2, 2, 6, 5, 4};
        int capacity = 10;

        System.out.println(knapsack.select(values, weights, capacity));
        System.out.println(knapsack.selectExactly(values, weights, capacity));
    }

    static void testDpLCSubstring() {
        LCSubstring substring = new LCSubstring();
        String str1 = "ABCDA";
        String str2 = "BCDA";
        String str3 = "SADCA";

        System.out.println(substring.lcs(str1, str2));
        System.out.println(substring.lcs(str1, str3));
    }


    static void testDpLCS() {
        LCS lcs = new LCS();

        int[] nums1 = {1, 3, 5, 9, 10};
        int[] nums2 = {1, 4, 9, 10};
        int[] nums3 = {1, 3, 9, 10};

        System.out.println(lcs.lcs(nums1, nums2));
        System.out.println(lcs.lcs(nums1, nums3));
    }

    static void testDpLIS() {
        LIS lis = new LIS();
        System.out.println(lis.lengthOfLIS(new int[]{10, 2, 2, 5, 1, 7, 101, 18}));
    }

    static void testDpSubArray() {
        int[] nums = {-1, 1, -3, 4, -1, 2, 1, -5, 4};
        MaxSubArray subArray = new MaxSubArray();
        System.out.println(subArray.maxSubArray(nums));
    }

    static void testDpCoins() {
        Coins coins = new Coins();
        int[] faces = {1};
        System.out.println(coins.coinsChange(0, faces));
        System.out.println(coins.coinsChange(19, faces));
    }

    static void testMaxSubarray() {
        int[] nums = {-1, 1, -3, 4, -1, 2, 1, -5, 4};
        MaxSubarray subarray = new MaxSubarray();
        System.out.println(subarray.maxSubArray(nums));
    }

    static void testKnapsack() {
        Article article = new Article();
        article.backKnapsack("价值主导", ((o1, o2) -> o2.getValue() - o1.getValue()));
        article.backKnapsack("重量主导", ((o1, o2) -> o1.getWeight() - o2.getWeight()));
        article.backKnapsack("性价比主导", ((o1, o2) ->
            Double.compare(o2.getValueDensity(), o1.getValueDensity())));
    }

    static void testCoins() {
        CoinChange coinChange = new CoinChange();
        coinChange.coinChange(new Integer[]{25, 5, 20, 1});
    }

    static void testPirate() {
        Pirate pirate = new Pirate();
        pirate.pirate();
    }

    static void testNQueens() {
        Queens3 queens = new Queens3(4);

        System.out.println(queens.placeQueues());
        List<List<String>> result = queens.getResult();
        result.forEach(System.out::println);
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
