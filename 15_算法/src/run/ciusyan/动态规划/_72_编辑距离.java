package run.ciusyan.动态规划;

/**
 * https://leetcode.cn/problems/edit-distance/
 */
public class _72_编辑距离 {


    /**
     * 一维数组，并且选择短的作为列数组
     */
    public static int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null) return 0;
        char[] chars1 = word1.toCharArray();
        char[] chars2 = word2.toCharArray();
        if (chars1.length == 0) return chars2.length;
        if (chars2.length == 0) return chars1.length;

        // 选长度较短的为列数组
        char[] charsRow = chars1;
        char[] charsCol = chars2;
        if (charsRow.length < charsCol.length) {
            // 选一个更短地作为列
            charsRow = chars2;
            charsCol = chars1;
        }

        // 只需要一行一行的计算即可，最多使用到两行数据
        int[] dp = new int[charsCol.length + 1];

        // 初始化第一行（包含dp(=[0]）
        for (int col = 0; col <= charsCol.length; col++) {
            // 代表将 "" -> 含有col个字母的单词
            dp[col] = col;
        }

        // 外层遍历行数组、内层遍历列数组：一行一行的计算
        for (int row = 1; row <= charsRow.length; row++) {

            // 用于之后记录左上角的值使用
            int cur = dp[0];
            for (int col = 1; col <= charsCol.length; col++) {
                // 先拿到左上角的值：
                int leftTop = cur;
                cur = dp[col];

                // 说明此次的最后一个字符不相等，还需要做一次替换操作
                if (charsRow[row - 1] != charsCol[col - 1]) leftTop++;

                // 选择插入 or 删除操作的最少次数
                int insertOrRemove = Math.min(dp[col - 1], dp[col]) + 1;

                dp[col] = Math.min(leftTop, insertOrRemove);
            }

            // 算完一行后，到下一行之前，要先初始化下一行的第一列
            // 代表将 含有row个字母的单词 -> ""
            dp[0] = row;
        }

        return dp[charsCol.length];
    }

    /**
     * 二维数组
     */
    public static int minDistance1(String word1, String word2) {
        if (word1 == null || word2 == null) return 0;
        char[] chars1 = word1.toCharArray();
        char[] chars2 = word2.toCharArray();

        // 定义 dp的状态
        int[][] dp = new int[chars1.length + 1][chars2.length + 1];

        // 初始化 dp(0, 0)
        dp[0][0] = 0;
        // 初始化第一行 dp(0, col)
        for (int col = 1; col <= chars2.length; col++) {
            // 代表将 "" -> 含有col个字母的单词
            dp[0][col] = col;
        }
        // 初始化第一列 dp(row, 0)
        for (int row = 1; row <= chars1.length; row++) {
            // 代表将 含有row个字母的单词 -> ""
            dp[row][0] = row;
        }

        // 递推出每一个解
        for (int row = 1; row <= chars1.length; row++) {
            for (int col = 1; col <= chars2.length; col++) {
                // 默认最后此次转换的最后一个字符相等，
                dp[row][col] = dp[row - 1][col - 1];

                if (chars1[row - 1] != chars2[col -1]) {
                    // 说明不相等，那么还需要将最后一个单词替换掉
                    //  因为前面已经赋值过了，所以直接 +1 即可
                    dp[row][col]++;
                }

                // 采用，删除、和插入的方式，求出一个最小值
                // dp[row][col - 1] ： 代表将 word1中 row字符结尾的单词 -> word2中 col - 1字符结尾的单词
                // dp[row - 1][col] ：代表将 word1 中 row - 1字符结尾的单词 -> word2中 col 字符结尾的单词
                // + 1代表着，转换之后，还需要插入一个字符 or 转换之前，需要先删除一个字符
                int insertOrRemove = Math.min(dp[row][col - 1], dp[row - 1][col]) + 1;

                // 最终结合三种方式，得出一个最小值，就是 dp(i, j) 的解
                dp[row][col] = Math.min(dp[row][col], insertOrRemove);
            }
        }

        return dp[chars1.length][chars2.length];
    }

    public static void main(String[] args) {
        System.out.println(minDistance("sea", "eat"));
    }
}
