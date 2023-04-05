package run.ciusyan.greedy;

import java.util.Arrays;

/**
 * 最优装载问题（加勒比海盗）
 */
public class Pirate {

    public void pirate() {
        int[] weights = {3, 5, 4, 10, 7, 14, 2, 11};
        // 将其排序
        Arrays.sort(weights);

        // 总载重量
        int capacity = 30;
        // 古董件数
        int count = 0;
        // 已选重量
        int weight = 0;

        // 总重量大于等于 容量时，就没必要去挑古董了
        for (int i = 0; i < weights.length && weight < capacity; i++) {
            int newWeight = weight + weights[i];
            if (newWeight > capacity) continue;

            // 能来到这里，说明可以选择
            count++;
            weight = newWeight;
            System.out.println("重量：" + weights[i]);
        }

        System.out.println("能选古董数：" + count + " 件");
    }
}
