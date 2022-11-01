package run.ciusyan.binarysearchtree;

import run.ciusyan.binarytree.BinaryTree;

/**
 * 二叉搜索树 接口设计
 */
public interface BinarySearchTree<E> extends BinaryTree<E> {

    /**
     * 添加元素
     * @param element：待添加元素
     */
    void add(E element);

    /**
     * 删除节点
     * @param element：待删除节点
     */
    void remove(E element);

    /**
     * 搜索某一个元素是否存在
     * @param element：带查找元素
     * @return ：是否存在
     */
    boolean contains(E element);
}
