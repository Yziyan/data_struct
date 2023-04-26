package run.ciusyan.DFS;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/permutations-ii/
 */
public class _47_全排列II {

    public List<List<Integer>> permuteUnique(int[] nums) {
        if (nums == null) return null;
        List<List<Integer>> list = new ArrayList<>();
        if (nums.length == 0) return list;
        dfs(0, nums, list);
        return list;
    }

    private void dfs(int level, int[] nums, List<List<Integer>> list) {
        if (level == nums.length) {
            List<Integer> result = new ArrayList<>();
            for (int num : nums) {
                result.add(num);
            }
            list.add(result);
            return;
        }

        for (int i = level; i < nums.length; i++) {
            // 如果和前面有相同的元素，那么直接剪枝，跳过这种情况即可
            if (isRepeat(nums, level, i)) continue;

            // 来到这后面。说明没有这需要被记录
            swap(nums, level, i);

            // 继续往下搜索
            dfs(level + 1, nums, list);

            // 记得回溯
            swap(nums, level, i);
        }

    }

    /**
     * 判断 nums[willSwap] 与 nums 中，这个范围 [level, willSwap)内有无重复的元素
     */
    private boolean isRepeat(int[] nums, int level, int willSwap) {
        for (int i = level; i < willSwap; i++) {
            // 说明有重复的
            if (nums[willSwap] == nums[i]) return true;
        }

        return false;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public List<List<Integer>> permuteUnique1(int[] nums) {
        if (nums == null) return null;

        List<List<Integer>> list = new ArrayList<>();
        if (nums.length == 0) return list;

        // 从第 0 层开始往下查找
        dfs1(0, nums, list);

        return list;
    }

    private void dfs1(int level, int[] nums, List<List<Integer>> list) {
        // 不能往下搜索了，记录结果
        if (level == nums.length) {
            // 利用轨迹、记录答案
            List<Integer> result = new ArrayList<>();
            for (int num : nums) {
                result.add(num);
            }

            // 结果中不存在再加，存在了那就不加了咯
            if (!list.contains(result)) {
                list.add(result);
            }

            // 直接回溯
            return;
        }

        for (int i = level; i < nums.length; i++) {
            // 确定一个 level 位置的元素
            swap(nums, level, i);

            // 往下一层搜索
            dfs1(level + 1, nums, list);

            // 恢复现场
            swap(nums, level, i);
        }
    }
}
