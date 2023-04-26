package run.ciusyan.Top;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/two-sum/
 */
public class _1_两数之和 {
    public int[] twoSum(int[] nums, int target) {
        if (nums == null) return null;
        if (nums.length <= 1) return new int[]{};

        // 使用 map 记录已经使用过的值 <值, 索引>
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            // nums[i] + nums[frontIdx] = target，
            Integer frontIdx = map.get(target - nums[i]);
            if (frontIdx != null) return new int[]{frontIdx, i};
            // 来到这里，说明当前的值，前面没有元素可以得到 target，
            //  存储当前的值
            map.put(nums[i], i);
        }

        return new int[]{};
    }
}
