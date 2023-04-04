package run.ciusyan.recursion;

/**
 * 汉诺塔
 */
public class Hanoi {

    /**
     * 将 n 个盘子，从 src 柱子 挪动到 dist 柱子
     * @param n：盘子数量
     * @param src：源柱子
     * @param central：中心柱子
     * @param dist：目标柱子
     */
    public void hanoi(int n, String src, String central, String dist) {
        if (n == 1) {
            // 如果只有一个盘子，直接挪动
            move(n, src, dist);
            return;
        }

        // 将 n - 1 个盘子，从 src 挪到 central
        hanoi(n - 1, src, dist, central);

        // 将第 n 个盘子，挪动到目标柱子
        move(n, src, dist);

        // 将 n - 1个盘子，从 central 挪动到 dist
        hanoi(n - 1, central, src, dist);
    }

    /**
     * 将  + n +  号从  + src + 柱 挪动到 + dist + 柱子
     */
    public void move(int n, String src, String dist) {
        System.out.println("将 " + n + " 号从" + src + "柱 挪动到" + dist + "柱子");
    }
}
