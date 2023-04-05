package run.ciusyan.greedy;

import java.util.Arrays;

/**
 * 零钱兑换
 */
public class CoinChange {

    public void coinChange() {
        Integer[] faces = {25, 5, 10, 1};
        Arrays.sort(faces);

        // 硬币个数
        int coins = 0;
        // 需要找零
        int money = 41;

        for (int i = faces.length - 1; i >= 0 && money > 0; i--) {
            if (money < faces[i]) continue;

            // 来到这里说明可以找零
            coins++;
            money -= faces[i];
            System.out.println("使用："+ faces[i] + "找零");

            // 但是下一次，说不定还可以用这个找零，所以这里可以简单处理，直接从头开始比较
            i = faces.length;
        }
        System.out.println("硬币个数：" + coins);
    }
    public void coinChange(Integer[] faces) {
        // 直接降序排列
        Arrays.sort(faces, (o1, o2) -> o2 - o1);
        // 硬币个数
        int coins = 0;
        // 需要找零
        int money = 41;

        int index = 0;
        while (index < faces.length && money > 0) {
            if (faces[index] > money) {
                // 说明这一面值太大了，之后用小一点的比较即可
                index++;
                continue;
            }

            // 说明可以找零
            coins++;
            money -= faces[index];
            System.out.println("使用："+ faces[index] + "找零");
        }
        System.out.println("硬币个数：" + coins);
    }
}
