package run.ciusyan.binarysearchtree.impl;

import run.ciusyan.binarysearchtree.BinarySearchTree;
import run.ciusyan.binarytree.impl.BinaryTreeImpl;

import java.util.Comparator;

/**
 * 二叉搜索树
 */
public class BSTImpl<E> extends BinaryTreeImpl<E> implements BinarySearchTree<E> {


    /**
     * 比较器
     */
    private Comparator<E> comparator;

    public BSTImpl() {
        this(null);
    }

    public BSTImpl(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        if (root == null) { // 添加根节点
            root = new Node<>(element, null);
            size++;
            return;
        }
        // 来到这说明不是第一次添加

        // 找到父节点
        Node<E> parent = root;
        Node<E> currentNode = root;
        int compare = 0;

        // 从根节点开始，依次往下寻找
        while (currentNode != null) {
            // 待添加节点与父节点比较大小
            compare = compare(element, currentNode.element);
            // 在比较前，先保存父节点
            parent = currentNode;

            if (compare > 0) { // 待添加的节点 > 当前节点
                currentNode = currentNode.right;
            } else if (compare < 0) { // 待添加的节点 < 当前节点
                currentNode = currentNode.left;
            } else { // 相等的情况
                currentNode.element = element;
                return;
            }
        }

        // 构建待添加的节点
        Node<E> newNode = new Node<>(element, parent);
        // 拿到刚刚的比较结果，决定最后要添加到父节点的左侧还是右侧【相等的情况在上面已经处理过了】
        if (compare > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        size++;
    }

    @Override
    public void remove(E element) {
        remove(node(element));
    }

    /**
     * 删除某一节点
     */
    private void remove(Node<E> node) {
        if (node == null) return;
        size--;
        if (node.hasTowChildren()) { // 度为 2 的节点 —— 情况 ③
            Node<E> predecessorNode = predecessor(node); // 拿到前驱节点
            node.element = predecessorNode.element;
            node = predecessorNode; // 删除前驱节点 【与下面删除度为 0、1的节点一致】
        }
        // 来到这里说明节点的度只能为 0 或 1
        // 看看有没有子节点
        Node<E> child = node.left != null ? node.left : node.right;
        if (child != null) { // 度为 1 的节点 —— 情况 ②
            // 将子节点的父节点指向待删除节点的父节点
            child.parent = node.parent;
            if (node.parent == null) {
                root = child;
            } else if (node == node.parent.left) { // 查看待删除节点是在它父节点的哪一边
                node.parent.left = child;
            } else { // node == node.parent.right
                node.parent.right = child;
            }
        } else if (node.parent == null) { // 度为 0 的节点 —— 情况 ① 只有一个根节点的情况
            root = null;
        } else { // 度为 0 的节点 —— 情况 ①
            // 看看待删除的叶子节点在它父节点的哪一边，在哪一边就删除谁
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }
        }
    }

    @Override
    public boolean contains(E element) {
        return node(element) != null;
    }

    /**
     * 根据元素，获取对应的节点
     * @param element：查找元素
     * @return ：对应的节点
     */
    private Node<E> node(E element) {
        if (element == null) return null;
        Node<E> node = root;
        while (node != null) {
            int compare = compare(element, node.element);
            if (compare == 0) return node;
            if (compare > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    /**
     * 检查元素是否为空
     * @param element：待检查元素
     */
    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("元素不能为 null");
        }
    }

    /**
     * 比较两元素的大小
     * @return ：【返回值 > 0】说明 e1 > e2。【返回值 = 0】说明 e1 = e2。【返回值 < 0】说明 e1 < e2
     */
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>)e1).compareTo(e2);
    }
}
