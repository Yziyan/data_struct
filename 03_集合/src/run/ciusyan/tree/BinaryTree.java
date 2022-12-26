package run.ciusyan.tree;


/**
 * 二叉树 接口设计
 */
public interface BinaryTree<E> {

    /**
     * 节点数量
     */
    int size();

    /**
     * 是否为空树
     */
    boolean isEmpty();

    /**
     * 清空所有节点
     */
    void clear();

    /**
     * 前序遍历
     */
    void preorder(BinaryTreeImpl.Visitor<E> visitor);

    /**
     * 中序遍历
     */
    void inorder(BinaryTreeImpl.Visitor<E> visitor);

    /**
     * 后序遍历
     */
    void postorder(BinaryTreeImpl.Visitor<E> visitor);

    /**
     * 层序遍历
     */
    void levelOrder(BinaryTreeImpl.Visitor<E> visitor);

    /**
     * 获取树的高度
     */
    int height();

    /**
     * 判断二叉树是否为完全二叉树
     */
    boolean isComplete();
}
