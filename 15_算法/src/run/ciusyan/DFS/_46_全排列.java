package run.ciusyan.DFS;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/permutations/
 */
public class _46_全排列 {

    /**
     * 交换剪枝
     */
    public List<List<Integer>> permute(int[] nums) {
        if (nums == null) return null;

        // 结果数组
        List<List<Integer>> list = new ArrayList<>();
        if (nums.length == 0) return list;

        // dfs从第 0 层开始，往下钻
        dfs(0, nums, list);

        return list;
    }

    /**
     *
     * @param level：第几层
     * @param nums：元数据 + 轨迹 + 标识使用
     * @param list：结果列表
     */
    private void dfs(int level, int[] nums, List<List<Integer>> list) {
        if (level == nums.length) {
            // 说明不能往下钻了，利用轨迹，记录一个组合
            List<Integer> result = new ArrayList<>();
            for (int num : nums) {
                result.add(num);
            }

            list.add(result);

            // 直接回溯到上一层
            return;
        }

        // 来到这里，说明这一层还合理，枚举所有可能
        //      是第几层，就从第几号位置开始交换，也就是要确定第几层的元素
        for (int i = level; i < nums.length; i++) {
            swap(nums, level, i);

            // 拿着这一次交换后，确定的第 level 层元素，往下钻
            dfs(level + 1, nums, list);

            // 如果来到这里，说明回溯到 level 层了，
            //      将刚刚交换的位置先复原成最初的样子（上上层的样子）
            swap(nums, level, i);
        }
    }

    /**
     * 交换 nums 中，i j 位置的元素
     */
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 使用 List 作为轨迹 + 标识已使用
     */
    public List<List<Integer>> permute2(int[] nums) {
        if (nums == null) return null;

        // 结果数组
        List<List<Integer>> list = new ArrayList<>();
        if (nums.length == 0) return list;

        // 记录轨迹
        List<Integer> track = new ArrayList<>();

        // dfs 搜索，从 0 层开始
        dfs2(0, nums, track, list);

        return list;
    }

    private void dfs2(int level, int[] nums, List<Integer> track, List<List<Integer>> list) {
        // 不能往下搜索了
        if (level == nums.length) {
            // 利用轨迹，记录一个合理的结果
            // 注意，这里不能直接使用 track，必须要深拷贝一份
            list.add(new ArrayList<>(track));

            // 添加完结果直接回溯
            return;
        }

        // 来到这里，列举出所有的可能
        for (int i = 0; i < nums.length; i++) {
            // 如果轨迹中已经存在了此数字，说明不能选了
            if (track.contains(nums[i])) continue;

            // 来到这里，说明此数字在这一次组合中，还没被选择，记录轨迹
            track.add(nums[i]);
            // 往下一层搜索
            dfs2(level + 1, nums, track, list);
            // 回溯到上一层时，需要还原现场，因为这个值已经用过了
            track.remove(track.size() - 1);
        }
    }

    /**
     * 使用两个数组，记录轨迹 + 标识已使用
     */
    public List<List<Integer>> permute1(int[] nums) {
        if (nums == null) return null;

        // 结果数组
        List<List<Integer>> list = new ArrayList<>();
        if (nums.length == 0) return list;

        // 用于记录轨迹
        int[] track = new int[nums.length];
        // 用于记录每层轨迹中，已经使用过的数字
        boolean[] used = new boolean[nums.length];

        // 进行 dfs 调用，从第 0 层开始
        dfs1(0, nums, track, used, list);

        return list;
    }

    private void dfs1(int level, int[] nums, int[] track, boolean[] used, List<List<Integer>> list) {
        // 不能往下搜索了，利用轨迹记录结果
        if (level == nums.length) {

            List<Integer> results = new ArrayList<>();
            for (int res : track) {
                results.add(res);
            }
            // 记录一次轨迹
            list.add(results);

            // 然后直接回溯到上一层
            return;
        }

        // 来到这里，说明钻到了合法的一层，这一层可以记录轨迹
        //      先列出这一层所有的可能
        for (int i = 0; i < nums.length; i++) {
            // 说明已经使用过这个数字了，换下一个
            if (used[i]) continue;

            // 来到这里，说明还没使用过此数字，现在马上要用了
            used[i] = true;
            // 记录轨迹（注意，这里是要记录每一层的轨迹）
            track[level] = nums[i];
            // 尝试钻到下一层
            dfs1(level + 1, nums, track, used, list);

            // 来到这里，说明从上一层退回来了
            //      需要还原现场，因为此数字使用完了
            used[i] = false;
        }
    }

    public static void main(String[] args) {
        _46_全排列 o = new _46_全排列();

        System.out.println(o.permute(new int[]{1, 2, 3}));
    }

}
