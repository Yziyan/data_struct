package run.ciusyan.balancebinarysearchtree;

import run.ciusyan.binarysearchtree.impl.BSTImpl;

import java.util.Comparator;

/**
 * 自平衡二叉搜索树 - balance binary search tree
 */
public class BBST<E> extends BSTImpl<E> {

    public BBST() {
        this(null);
    }

    public BBST(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 统一旋转逻辑
     */
    protected void rotate(Node<E> R, // 待旋转节点的根节点
                        Node<E> A, Node<E> B, Node<E> C, // 左子树
                        Node<E> D, // 最终的根节点
                        Node<E> E, Node<E> F, Node<E> G // 右子树的
    ) {

        // 将 D 变成根节点
        D.parent = R.parent;
        if (R.isRightChild()) { // 待旋转节点在根节点的右边
            R.parent.right = D;
        } else if (R.isLeftChild()) { // 待旋转节点在根节点的左边
            R.parent.left = D;
        } else { // 待旋转节点就是根节点
            root = D;
        }

        // 构建 D 的左子树
        B.left = A;
        B.right = C;
        if (A != null) {
            A.parent = B;
        }
        if (C != null) {
            C.parent = B;
        }
        B.parent = D;

        // 构建 D 的右子树
        F.left = E;
        F.right = G;
        if (E != null) {
            E.parent = F;
        }
        if (G != null) {
            G.parent = F;
        }
        F.parent = D;

        // 将 B - D - F 连接起来
        D.left = B;
        D.right = F;
    }

    /**
     * 左旋转
     * @param node：待旋转节点
     */
    protected void rotateLeft(Node<E> node) {
        Node<E> child = node.right; // 取出子节点 【左旋，节点肯定在右边】
        Node<E> grandChild = child.left; // 取出孙子节点
        node.right = grandChild; // 将上面的节点往下旋转【将待旋转的子节点指向它的孙子节点】
        child.left = node; // 将下面的节点向上旋转【将子节点的子节点变成待旋转节点】

        // 旋转后的操作
        afterRotate(node, child, grandChild);
    }

    /**
     * 右旋转
     * @param node：待旋转节点
     */
    protected void rotateRight(Node<E> node) {
        Node<E> child = node.left; // 取出子节点【右旋：节点肯定在左边】
        Node<E> grandChild = child.right; // 取出孙子节点
        node.left = grandChild; // 将上面的节点往下旋转【将待旋转的子节点指向它的孙子节点】
        child.right = node; // 将下面的节点向上旋转【将子节点的子节点变成待旋转节点】

        // 旋转后的操作
        afterRotate(node, child, grandChild);
    }

    /**
     * 旋转后所需要维护的内容
     * @param node：原先的待旋转节点
     * @param child：子节点
     * @param grandChild：孙子节点
     */
    protected void afterRotate(Node<E> node, Node<E> child, Node<E> grandChild) {
        // 更新子节点的父节点
        child.parent = node.parent;
        if (node.isLeftChild()) { // 待旋转节点是它父节点的左子树
            node.parent.left = child;
        } else if (node.isRightChild()) { // 待旋转节点是它父节点的右子树
            node.parent.right = child;
        } else { // 没有父节点【待旋转节点就是根节点】
            root = child;
        }

        // 更新孙子节点的父节点
        if (grandChild != null) {
            grandChild.parent = node;
        }

        // 更新原先待旋转节点的父节点
        node.parent = child;
    }
}
