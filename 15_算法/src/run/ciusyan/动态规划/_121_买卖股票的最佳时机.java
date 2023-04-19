package run.ciusyan.动态规划;

/**
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/
 */
public class _121_买卖股票的最佳时机 {

    /**
     * 动态规划
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) return 0;

        // 代表第一天的利润 dp(0) = 第一天和第零天的利润
        int dp = 0;
        // 最大利润（默认是dp(0) ）
        int maxProfit = dp;

        for (int i = 1; i < prices.length; i++) {
            int perDayProfit = prices[i] - prices[i - 1];

            if (dp <= 0) {
                // 说明前面每一天的利润是不赚的，没必要加到后一天
                dp = perDayProfit;
            } else {
                dp += perDayProfit;

                // 来到这里，可能对最大利润有影响，因为加上了新的利润，有可能赚的更多
                maxProfit = Math.max(maxProfit, dp);
            }
        }

        return maxProfit;
    }

    /**
     * 迭代
     * @param prices
     * @return
     */
    public int maxProfit1(int[] prices) {
        if (prices == null || prices.length <= 1) return 0;

        // 前面扫描过的最小价格
        int minPrice = prices[0];
        // 前面扫描过的最大利润
        int maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            if (minPrice > prices[i]) {
                // 说明还有比买入时更便宜的价格
                minPrice = prices[i];
            } else {
                // 说明没有比目前更便宜的价格了，看看是否超过最大利润
                maxProfit = Math.max(maxProfit, prices[i] - minPrice);
            }
        }

        return maxProfit;
    }
}
