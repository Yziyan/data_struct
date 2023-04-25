package run.ciusyan.binarysearchtree.impl;


/**
 * 二叉线索树（morris遍历）
 */
public class MorrisTree extends BSTImpl<Integer> {

    public void inorder() {
        if (root == null) return;

        Node<Integer> node = root;

        while (node != null) {

            if (node.left != null) {
                // 找到 node 的前驱节点
                Node<Integer> prec = node.left;

                // 一路找到最右边的节点，就是自己的前驱节点
                // 但是注意：prec.right != node，
                //  因为有可能已经串起来了，那么右边如果就是自己，那么不能往右走了
                while (prec.right != null && prec.right != node) {
                    prec = prec.right;
                }

                // 在这里，看看前驱节点的右边，是否串起自己了
                if (prec.right == null) {
                    // 串起来，访问完前驱后，方便立马访问自己
                    prec.right = node;
                    node = node.left;
                } else { // 来到这里，说明 prec.right == node，
                    // 说明已经往回走了，已经访问过前驱节点了，下一个就要访问自己
                    System.out.print(node.element + " ");

                    // 但是要清空串起来的线
                    prec.right = null;

                    // 然后尝试往右走
                    node = node.right;
                }

            } else { // node.left == null
                // 需要访问 node 了，
                System.out.print(node.element + " ");

                // 访问完后，往右走
                node = node.right;
            }
        }
    }
}
