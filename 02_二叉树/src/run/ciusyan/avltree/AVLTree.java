package run.ciusyan.avltree;

import run.ciusyan.balancebinarysearchtree.BBST;

import java.util.Comparator;

/**
 * 自平衡二叉搜索树 —— AVL树
 */
public class AVLTree<E> extends BBST<E> {

    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null) { // 从父节点不断的往上寻找失衡的祖先节点
            if (isBalanced(node)) { // 未失衡的逻辑
                // 如果是平衡的节点，那么将其高度更新一下，因为添加了一个新的节点
                updateHeight(node);
            } else { // 失衡后的处理
                // 恢复平衡
                rebalanced(node);
                // 处理完最小的失衡的节点后，所有节点都平衡了，终止循环即可
                break;
            }
        }
        // 退出循环说明解决完失衡了 【也可能没有失衡】
    }

    @Override
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) { // 未失衡
                updateHeight(node);
            } else {
                rebalanced(node); // 恢复平衡后，还需继续遍历
            }
        }
    }

    /**
     * 判断节点是否平衡
     * @param node：待判断的节点
     * @return ：是否平衡
     */
    private boolean isBalanced(Node<E> node) {
        // |平衡因子| ≤ 1
        return Math.abs(((AVLNode<E>) node).balanceFactor()) <= 1;
    }

    /**
     * 更新节点的高度
     * @param node：需要更新高度的节点
     */
    private void updateHeight(Node<E> node) {
        ((AVLNode<E>)node).updateHeight(); // 将强制转换写在外面，美化代码
    }

    /**
     * 恢复平衡
     * @param node：最小的失衡节点
     */
    private void rebalanced2(Node<E> node) {
        Node<E> child = ((AVLNode<E>) node).tallerChild(); // 失衡节点较高的子节点
        Node<E> grandChild = ((AVLNode<E>) child).tallerChild(); // 失衡节点较高的孙子节点
        if (child.isLeftChild()) { // 在失衡节点的左边 【L】
            if (grandChild.isLeftChild()) { // 继续向左【LL】
                rotateRight(node); // 左边太重了【右旋转】
            } else { // 往右走了【LR】
                rotateLeft(child); // 子节点的右边重了，先将它左旋
                rotateRight(node); // 失衡节点的左边太重了，需要右旋
            }
        } else { // 在失衡节点的右边【R】
            if (grandChild.isRightChild()) { // 继续向右【RR】
                rotateLeft(node); // 右边太重了【左旋转】
            } else { // 往左走了【RL】
                rotateRight(child); // 子节点的左边重了，先将它右旋
                rotateLeft(node); // 失衡节点的右边太重了，需要左旋旋
            }
        }
    }

    /**
     * 恢复平衡
     * @param node：最小的失衡节点
     */
    private void rebalanced(Node<E> node) {
        Node<E> child = ((AVLNode<E>) node).tallerChild();
        Node<E> grandChild = ((AVLNode<E>) child).tallerChild();

        if (child.isLeftChild()) {
            if (grandChild.isLeftChild()) { // LL
                rotate(node,
                    grandChild.left, grandChild, grandChild.right,
                    child,
                    child.right, node, node.right);
            } else { // LR
                rotate(node,
                    child.left, child, grandChild.left,
                    grandChild,
                    grandChild.right, node, node.right);
            }
        } else {
            if (grandChild.isRightChild()) { // RR
                rotate(node,
                    node.left, node, child.left,
                    child,
                    grandChild.left, grandChild, grandChild.right);
            } else { // RL
                rotate(node,
                    node.left, node, grandChild.left,
                    grandChild,
                    grandChild.right, child, child.right);
            }
        }

    }

    @Override
    protected void rotate(Node<E> R, Node<E> A, Node<E> B, Node<E> C, Node<E> D, Node<E> E, Node<E> F, Node<E> G) {
        super.rotate(R, A, B, C, D, E, F, G);

        // 更新树的高度【也要先更新矮的】
        updateHeight(B); // 更新左子树B 的高度
        updateHeight(F); // 更新右子树F 的高度
        updateHeight(D); // 更新 D 的高度
    }

    @Override
    protected void afterRotate(Node<E> node, Node<E> child, Node<E> grandChild) {
        super.afterRotate(node, child, grandChild);

        // 更新节点的高度 【先更新矮的、后更新高的】
        updateHeight(node);
        updateHeight(child);
    }

    /**
     * AVL树的节点类
     */
    private static class AVLNode<E> extends Node<E> {
        /**
         * 树的高度【默认高度为 1 （叶子节点）】
         */
        int height = 1;
        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        /**
         * 计算某一节点的平衡因子
         */
        public int balanceFactor() {
            int LH = left != null ? ((AVLNode<E>)left).height : 0; // 左子树的高度
            int RH = right != null ? ((AVLNode<E>)right).height : 0; // 右子树的高度
            return LH - RH; // (右 - 左) 也可以
        }

        /**
         * 更新节点的高度
         */
        public void updateHeight() {
            int LH = left != null ? ((AVLNode<E>)left).height : 0; // 左子树的高度
            int RH = right != null ? ((AVLNode<E>)right).height : 0; // 右子树的高度
            height = 1 + Math.max(LH, RH); // 自己的高度 + 左右子树高度的最大值
        }

        /**
         * 返回节点的子节点
         * @return ：【左右子树中高的那个、如果一样高，返回同方向的】
         */
        public Node<E> tallerChild() {
            int LH = left != null ? ((AVLNode<E>)left).height : 0; // 左子树的高度
            int RH = right != null ? ((AVLNode<E>)right).height : 0; // 右子树的高度
            if (LH > RH) return left; // 左边较高，返回左子树
            if (LH < RH) return right; // 右边较高，返回右子树
            // 左右高度一样，返回与父节点同方向的节点
            return isLeftChild() ? left : right;
        }

        @Override
        public String toString() {
            if (parent == null) {
                return element + "_p(null)_h(" + height + ")";
            }
            return element + "_p(" + parent.element + ")_h(" + height + ")";
        }
    }

}
