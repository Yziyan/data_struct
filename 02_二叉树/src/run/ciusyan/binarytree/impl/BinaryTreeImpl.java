package run.ciusyan.binarytree.impl;

import run.ciusyan.binarytree.BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树
 */
public class BinaryTreeImpl<E> implements BinaryTree<E> {

    protected int size;
    /**
     * 根节点
     */
    protected Node<E> root;

    /**
     * 内部节点类
     */
    protected static class Node<E> {
        /**
         * 元素
         */
        public E element;
        /**
         * 左子节点
         */
        public Node<E> left;
        /**
         * 右子节点
         */
        public Node<E> right;
        /**
         * 父节点
         */
        public Node<E> parent;
        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        /**
         * 判断某一节点是否为叶子节点
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * 判断某一节点的度是否为二
         */
        public boolean hasTowChildren() {
            return left != null && right != null;
        }

        /**
         * 查看节点是否位于父节点的左子树
         */
        public boolean isLeftChild() { return parent != null && this == parent.left; }

        /**
         * 查看节点是否位于父节点的右子树
         */
        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 获取兄弟节点
         * @return ：兄弟节点
         */
        public Node<E> sibling() {
            if (isLeftChild()) return parent.right; // 自己在左边，返回右边
            if (isRightChild()) return parent.left; // 自己在右边，返回左边
            return null; // 没有父节点，那么也没有兄弟节点
        }
    }

    public BinaryTreeImpl() { }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * 用于访问内部某些细节
     */
    public static abstract class Visitor<E> {

        /**
         * 用于记录是否需要停止访问
         */
        boolean stop;

        /**
         * 访问时，执行的操作
         * @param element：被访问元素
         * @return :返回 true 就不继续访问了
         */
        protected abstract boolean visit(E element);
    }

    /**
     * 层序遍历
     */
    @Override
    public void levelOrder(Visitor<E> visitor) {
        if (root == null || visitor == null) return;

        // 准备一个队列，并且将根节点放入此队列
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            // 将队头出队
            Node<E> node = queue.poll();

            // 访问元素【如果返回值是 true 停止遍历】
            if (visitor.visit(node.element)) return;

            // 如果出队节点的左子节点不为空，将其入队
            if (node.left != null) {
                queue.offer(node.left);
            }
            // 如果出队节点的右子节点不为空，将其入队
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    /**
     * 前序遍历
     */
    @Override
    public void preorder(Visitor<E> visitor) {
        if (visitor == null) return;
        preorder(root, visitor);
    }

    /**
     * 前序遍历，递归函数
     */
    private void preorder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;
        // 先访问根节点
        visitor.stop = visitor.visit(node.element);
        // 再使用递归方式，前序遍历左子树
        preorder(node.left, visitor);
        // 最后使用递归方式前序遍历右子树
        preorder(node.right, visitor);
    }

    /**
     * 中序遍历
     */
    @Override
    public void inorder(Visitor<E> visitor) {
        if (visitor == null) return;
        inorder(root, visitor);
    }

    /**
     * 中序遍历，递归函数
     */
    private void inorder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;
        // 先使用递归方式，中序遍历左子树
        inorder(node.left, visitor);
        if (visitor.stop) return;
        // 再访问根节点
        visitor.stop = visitor.visit(node.element);
        // 最后使用递归方式，中序遍历右子树
        inorder(node.right, visitor);
    }
    /**
     * 后序遍历
     */
    @Override
    public void postorder(Visitor<E> visitor) {
        if (visitor == null) return;
        postorder(root, visitor);
    }

    /**
     * 后序遍历，递归函数
     */
    private void postorder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;
        postorder(node.left, visitor);
        postorder(node.right, visitor);
        // 最后访问根节点
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return toString(root, sb, "");
    }

    /**
     * 利用遍历，打印树结构
     * @param node：节点
     * @param prefix：前缀
     */
    private String toString(Node<E> node, StringBuilder sb, String prefix) {
        if (node == null) return null;
        toString(node.right, sb, prefix + "[R] ");
        sb.append(prefix).append(node.element).append("\n");
        toString(node.left, sb, prefix + "[L] ");
        return sb.toString();
    }

    @Override
    public int height() {
        return heightLevelOrder();
    }

    /**
     * 计算树的高度【递归法】
     */
    private int height(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * 计算树的高度【层序遍历法】
     */
    private int heightLevelOrder() {
        if (root == null) return 0;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        // 最终返回的树的高度
        int height = 0;
        // 每一层的元素个数
        int levelSize = 1;

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            // 出队一个元素，每层的元素个数就 -1
            levelSize--;
            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }

            // 当每层元素个数为 0 时，说明要开始遍历下一层了【如果还有的话】，有几层，height就等于多少
            if (levelSize == 0) {
                levelSize = queue.size();
                height++;
            }

        }
        return height;
    }

    @Override
    public boolean isComplete() {
        if (root == null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        // 记录是否该出现叶子节点的情况：②、④
        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();

            // 本该是叶子节点，但不是叶子节点
            if (leaf && !node.isLeaf()) return false;

            if (node.left != null) { // 保证左子节点不为空时，将其放入队列
                queue.offer(node.left);
            } else if (node.right != null) { // 说明是 情况 ③
                return false;
            }

            if (node.right != null) { // 保证右子节点不为空时，将其放入队列
                queue.offer(node.right);
            } else { // 说明是情况 ②、④
                leaf = true;
            }
        }
        return true;
    }

    /**
     * 找到一个节点的前驱节点
     * @param node：待查找节点
     * @return ：前驱结点
     */
    protected Node<E> predecessor(Node<E> node) {
        if (node == null) return null;

        Node<E> predecessorNode = node.left;
        if (predecessorNode != null) { // 来到这说明有左子树，前驱节点一定在左子树上
            while (predecessorNode.right != null) {
                predecessorNode = predecessorNode.right;
            }
            return predecessorNode;
        }

        // 来到这说明没有左子树【只能从父节点开始找】
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        /*
        来到这里说明
        1、node.parent == null
        2、node == node.parent.right
         */
        return node.parent;
    }

    /**
     * 找到一个节点的后继节点
     * @param node：待查找节点
     * @return ：后继结点
     */
    protected Node<E> successor(Node<E> node) {
        if (node == null) return null;

        Node<E> successorNode = node.right;
        if (successorNode != null) { // 来到这里说明有右子树，后继节点一定在右子树上
            while (successorNode.left != null) {
                successorNode = successorNode.left;
            }
            return successorNode;
        }
        // 来到这说明没有右子树，只能从父节点往上寻找

        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }
        /*
        来到这有两种情况
        1、node.parent = null
        2、node = node.parent.left
         */
        return node.parent;
    }


    /*-----------------------方便打印【作者：MJ Lee】-----------------------------*/

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        Node<E> myNode = (Node<E>) node;
        if (myNode.parent == null) {
            return myNode.element + "_p(null)";
        }
        return myNode.element + "_p(" + myNode.parent.element + ")";
    }

}
