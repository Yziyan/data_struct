package run.ciusyan.DFS;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/permutations/
 */
public class _46_全排列 {


    /**
     * 使用 List 作为轨迹 + 标识已使用
     */
    public List<List<Integer>> permute(int[] nums) {
        if (nums == null) return null;

        // 结果数组
        List<List<Integer>> list = new ArrayList<>();
        if (nums.length == 0) return list;

        // 记录轨迹
        List<Integer> track = new ArrayList<>();

        // dfs 搜索，从 0 层开始
        dfs(0, nums, track, list);

        return list;
    }

    private void dfs(int level, int[] nums, List<Integer> track, List<List<Integer>> list) {
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
            dfs(level + 1, nums, track, list);
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
