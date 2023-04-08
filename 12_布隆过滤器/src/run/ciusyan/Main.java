package run.ciusyan;

import run.ciusyan.bf.BloomFilter;

public class Main {
    public static void main(String[] args) {
        test1();
    }
    static void test0() {
        BloomFilter<Integer> bf = new BloomFilter<>(1_00_0000, 0.01);
        for (int i = 1; i <= 50; i++) {
            bf.put(i);
        }
        for (int i = 1; i < 50; i++) {
            Asserts.test(bf.contains(i));
        }
    }

    static void test1() {
        BloomFilter<Integer> bf = new BloomFilter<>(1_00_0000, 0.01);
        for (int i = 1; i <= 1_00_0000; i++) {
            bf.put(i);
        }

        int count = 0;
        for (int i = 1_00_0001; i <= 2_00_0000; i++) {
            if (bf.contains(i)) {
                count++;
            }
        }
        System.out.println(count);
    }
}
