package run.ciusyan.Top;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/spiral-matrix/
 */
public class _54_螺旋矩阵 {
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return null;

        List<Integer> list = new ArrayList<>();

        // 定义四个指针，分别指向：上下左右四个方向
        int top = 0, bottom = matrix.length - 1, left = 0, right = matrix[0].length - 1;

        // 一圈一圈的遍历，上下不能颠倒、左右也不能颠倒
        while (top <= bottom && left <= right) {
            // 左上 -> 右上
            for (int i = left; i <= right; i++) {
                list.add(matrix[top][i]);
            }
            // 向下移动一行
            top++;

            // 如果是奇数行，偶数列，那么这里
            // 会重复打印一个 left <= right 的元素
            // top > bottom：说明已经遍历完成了
            if (top > bottom) return list;

            // 右上 -> 右下
            for (int i = top; i <= bottom; i++) {
                list.add(matrix[i][right]);
            }
            // 向左移动一行
            right--;

            // 如果是奇数行，奇数列，并且不是正方矩阵，那么这里
            // 会重复打印一个 top <= button 的元素
            // left > right：说明已经遍历完成了
            if (left > right) return list;

            // 右下 -> 左下
            for (int i = right; i >= left ; i--) {
                list.add(matrix[bottom][i]);
            }
            // 向上移动一行
            bottom--;

            // 左下 -> 左上
            for (int i = bottom; i >= top ; i--) {
                list.add(matrix[i][left]);
            }
            // 向右移动一行
            left++;
        }

        return list;
    }

    public static void main(String[] args) {
        _54_螺旋矩阵 o = new _54_螺旋矩阵();
        int[][] matrix = {
            {1,2,3},
            {4,5,6},
            {7,8,9},
            {1,2,3},
            {1,2,3},
        };
        System.out.println(o.spiralOrder(matrix));
    }

}
