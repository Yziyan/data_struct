package run.ciusyan.map;

import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("ALL")
public class TreeMap<K, V> implements Map<K, V> {

    private int size;
    private Node<K, V> root;
    private Comparator<K> comparator;
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public TreeMap() { this(null); }
    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

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
        size = 0;
        root = null;
    }

    @Override
    public V put(K key, V value) {
        keyNotNullCheck(key);

        if (root == null) { // 添加的是根节点
            root = new Node<>(key, value, null);
            size++;
            afterPut(root);
            return null;
        }

        // 来到这里说明不是根节点

        // 记录大小、父节点
        int compare = 0;
        Node<K, V> parent = root;
        Node<K, V> currentNode = root;

        while (currentNode != null) {

            compare = compare(key, currentNode.key); // 比较大小
            parent = currentNode; // 给父节点赋值，之后需要用父节点构建新的节点

            if (compare > 0) { // 插入的值在右边
                currentNode = currentNode.right;
            } else if (compare < 0) { // 插入的值在左边
                currentNode = currentNode.left;
            } else { // 相等的情况
                V oldValue = currentNode.value;
                // 将 Key Value 都覆盖
                currentNode.key = key;
                currentNode.value = value;
                return oldValue;
            }
        }

        // 构建新节点
        Node<K, V> node = new Node<>(key, value, parent);

        if (compare > 0) { // 应该插入右边
            parent.right = node;
        } else { // 应该插入左边
            parent.left = node;
        }
        size++;
        // 插入后的操作
        afterPut(node);
        return null;
    }

    @Override
    public V get(K key) {
        final Node<K, V> node = node(key);
        return node == null ? null : node.value;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (root == null) return false;

        // 准备开始层序遍历
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // 队头出队
            final Node<K, V> node = queue.poll();
            if (node.value.equals(value)) return true;

            if (node.left != null) { // 左子树不为空放入队列
                queue.offer(node.left);
            }

            if (node.right != null) { // 右子树不为空放入队列
                queue.offer(node.right);
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        inorder(root, visitor);
    }

    /**
     * 内部节点
     * @param <K>：键 key
     * @param <V>：值 value
     */
    private static class Node<K, V> {
        boolean color = RED; // 节点的颜色，默认为红色
        K key;
        V value;
        Node<K, V> parent;
        Node<K, V> left;
        Node<K, V> right;
        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /*--------------------------↓ ↓ ↓节点的辅助函数---------------------------------*/

        /**
         * 是否是左子节点
         */
        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        /**
         * 是否是右子节点
         */
        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 是否是叶子节点
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * 是否是度为2的节点
         */
        public boolean hasTowChildren() {
            return left != null && right != null;
        }

        /**
         * 获取兄弟节点
         */
        public Node<K, V> sibling() {
            if (isLeftChild()) { // 自己在左边，返回右边
                return parent.right;
            }
            if (isRightChild()) { // 自己在右边，返回左边
                return parent.left;
            }
            return null; // 没有父节点，那么也没有兄弟节点
        }

        /*--------------------------↑ ↑ ↑节点的辅助函数-------------------------------*/

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "RED_";
            }
            return str + "【K：" + key.toString() + "】【V：" + value.toString() + "】【P：" + parent + "】";
        }
    }

    /*--------------------------↓ ↓ ↓红黑树的辅助函数---------------------------------*/

    /**
     * 染色
     * @param node：待染色节点
     * @param color：颜色
     * @return ：染色后的节点
     */
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;
        return node;
    }

    /**
     * 将节点染成红色
     */
    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    /**
     * 将节点染成黑色
     */
    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    /**
     * 查看节点的颜色
     * @param node：待查询节点
     * @return ：节点的颜色
     */
    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    /**
     * 查看节点是否是红色
     */
    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    /**
     * 查看节点是否是黑色
     */
    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * 添加后的处理
     * @param node：新添加的节点
     */
    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        if (parent == null) { // 添加的是根节点
            black(node); // 将其染色即可
            return;
        }

        // 1、父节点是黑色。不需要处理
        if (isBlack(parent)) return;

        // 来到这里说明父节点都是红色的情况了

        Node<K, V> uncle = parent.sibling(); // 取出叔父节点
        Node<K, V> grandparent = red(parent.parent); // 将祖父节点染成红色

        if (isRed(uncle)) { // 2、叔父节点是红色，会产生上溢现象
            // 将父节点和叔父节点染黑
            black(parent);
            black(uncle);
            // 将它当做新添加的节点，递归解决
            afterPut(grandparent);
            return;
        }

        // 来到这里说明，叔父节点不是红色、添加后通过【旋转 + 染色】解决
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                // 将祖父节点染成红色、父节点染成黑色
                black(parent);
            } else { // LR
                // 将祖父节点染成红色、自己染成黑色
                black(node);
                rotateLeft(parent); // 父节点左旋
            }

            rotateRight(grandparent); // 祖父节点右旋
        } else { // R
            if (node.isRightChild()) { // RR
                // 将祖父节点染成红色、父节点染成黑色
                black(parent);
            } else { // RL
                // 将祖父节点染成红色、自己染成黑色
                black(node);
                rotateRight(parent); // 父节点右旋
            }
            rotateLeft(grandparent); // 祖父节点左旋
        }
    }

    /**
     * 删除后的处理
     * @param node：被删除的节点 or 用于取代的子节点
     */
    private void afterRemove(Node<K, V> node) {

        /*
         1、删除的是红色的节点
         2、删除的是有一个红色子节点的黑色节点【删除的是BST中度为 1 的节点】
         */
        if (isRed(node)) {
            black(node); // 1、都被删除了，染色也没关系 2、将其取代的子节点染成黑色即可
            return;
        }

        // 来到这里，被删除的节点都是度为0的黑色节点

        Node<K, V> parent = node.parent; // 取出父节点
        if (parent == null) return; // 说明删除的是根节点

        /*
         查看被删除的节点是否位于左子树
         1、parent.left == null【传入前左边被删除了，所以是左边】
         2、node.isLeftChild()【下面解决下溢时，可能会递归调用此方法，node是被当做删除节点传入】
         */
        boolean isLeft = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = isLeft ? parent.right : parent.left; // 取出兄弟节点

        if (isLeft) { // 被删除节点位于左子树【与右子树操作对称】
            if (isRed(sibling)) { // 兄弟节点是红色
                // 将兄弟节点染黑、父节点染红
                black(sibling);
                red(parent);
                rotateLeft(parent); // 父节点左旋
                sibling = parent.right; // 旋转后兄弟变了，将侄子变成兄弟
            }

            // 来到这里，兄弟节点肯定是黑色的情况了

            if (isRed(sibling.left) || isRed(sibling.right)) { // 兄弟节点至少有一个红色的子节点，可以借用

                if (isRed(sibling.left)) { // RL 的情况
                    rotateRight(sibling); // 将兄弟节点右旋
                    sibling = parent.right; // 旋转后兄弟节点变换了
                }

                // 来到这里，说明都能看成是 RR的情况了

                rotateLeft(parent); // 将父节点左旋
                color(sibling, colorOf(parent)); // 新父节点【中心节点】继承旧父节点的颜色
                // 将新父节点的子节点都染成黑色
                black(sibling.right);
                black(sibling.left);
            } else { // 兄弟节点一个红色的子节点也没有，需要向下合并
                boolean parentBlack = isBlack(parent); // 记录原先父节点的颜色
                black(parent); // 父节点染成黑色
                red(sibling); // 兄弟节点染成红色
                if (parentBlack) { // 如果以前就是黑色，父节点向下合并后，也会参数下溢
                    afterRemove(parent); // 将父节点当做被删除的节点，递归调用此函数
                }
            }
        } else { // 被删除节点位于右子树【与左子树操作对称】
            if (isRed(sibling)) { // 兄弟节点是红色
                // 将兄弟节点染黑、父节点染红
                black(sibling);
                red(parent);
                rotateRight(parent); // 父节点右旋
                sibling = parent.left; // 旋转后兄弟变了，将侄子变成兄弟
            }

            // 来到这里，兄弟节点肯定是黑色的情况了

            if (isRed(sibling.left) || isRed(sibling.right)) { // 兄弟节点至少有一个红色的子节点，可以借用

                if (isRed(sibling.right)) { // LR 的情况
                    rotateLeft(sibling); // 将兄弟节点左旋
                    sibling = parent.left; // 旋转后兄弟节点变换了
                }

                // 来到这里，说明都能看成是 LL的情况了

                rotateRight(parent); // 将父节点右旋
                color(sibling, colorOf(parent)); // 新父节点【中心节点】继承旧父节点的颜色
                // 将新父节点的子节点都染成黑色
                black(sibling.right);
                black(sibling.left);
            } else { // 兄弟节点一个红色的子节点也没有，需要向下合并
                boolean parentBlack = isBlack(parent); // 记录原先父节点的颜色
                black(parent); // 父节点染成黑色
                red(sibling); // 兄弟节点染成红色
                if (parentBlack) { // 如果以前就是黑色，父节点向下合并后，也会参数下溢
                    afterRemove(parent); // 将父节点当做被删除的节点，递归调用此函数
                }
            }
        }
    }

    /*--------------------------↑ ↑ ↑红黑树的辅助函数-------------------------------*/


    /*--------------------------↓ ↓ ↓二叉搜索树的辅助函数---------------------------------*/

    /**
     * 左旋
     * @param node：待旋转节点
     */
    private void rotateLeft(Node<K, V> node) {
        final Node<K, V> child = node.right; // 取出子节点【左旋、在右边】
        final Node<K, V> grandchild = child.left; // 取出孙子节点
        node.right = grandchild; // 自己的右子节点指向孙子节点
        child.left = node; // 将自己旋转到下方

        afterRotate(node, child, grandchild); // 旋转后的操作
    }

    /**
     * 右旋
     * @param node：待旋转节点
     */
    private void rotateRight(Node<K, V> node) {
        final Node<K, V> child = node.left; // 取出子节点【右旋、在左边】
        final Node<K, V> grandchild = child.right; // 取出孙子节点
        node.left = grandchild; // 自己的左子节点指向孙子节点
        child.right = node; // 将自己旋转到下方

        afterRotate(node, child, grandchild); // 旋转后的操作
    }

    /**
     * 旋转后的操作
     * @param node：原先待旋转节点
     * @param child：子节点
     * @param grandchild：孙子节点
     */
    private void afterRotate(Node<K, V> node, Node<K, V> child, Node<K, V> grandchild) {
        child.parent = node.parent; // 更新子节点的父节点
        // 将子节点旋转到上方
        if (node.isLeftChild()) { // 待旋转节点在父节点的左边
            node.parent.left = child;
        } else if (node.isRightChild()) { // 待旋转节点在父节点的右边
            node.parent.right = child;
        } else { // 没有父节点
            root = child;
        }
        // 如果有孙子节点，更新孙子节点的父节点
        if (grandchild != null) {
            grandchild.parent = node;
        }
        node.parent = child; // 更新原先待旋转节点的父节点
    }

    /**
     * key判空
     */
    private void keyNotNullCheck(K key) {
        if (key == null) {
            throw new InvalidParameterException("Key 不能为空");
        }
    }

    /**
     * 根据 key 获取节点对象
     */
    private Node<K, V> node(K key) {
        if (key == null) return null;
        Node<K, V> node = root;
        while (node != null) {
            int compare = compare(key, node.key); // 比较大小
            if (compare == 0) return node;
            if (compare > 0) { // 传入值大
                node = node.right; // 可能在右子树
            } else { // 传入值小
                node = node.left; // 可能在左子树
            }
        }
        return null;
    }

    /**
     * 比较 key 的大小
     * @return 1：【k1 > k2】 0：【k1 = k2】 -1：【k1 < k2】
     */
    private int compare(K k1, K k2) {
        if (comparator != null) { // 有传比较器就使用比较器
            return comparator.compare(k1, k2);
        }
        return ((Comparable<K>)k1).compareTo(k2); // 没有比较器，就默认当做是可比较的
    }

    /**
     * 查找前驱节点
     */
    private Node<K, V> predecessor(Node<K, V> node) {
        if (node == null) return node;

        Node<K, V> predecessor = node.left;
        if (predecessor != null) { // 说明前驱节点在左子树
            while (predecessor.right != null) { // 在左子树的最右边
                predecessor = predecessor.right;
            }
            return predecessor;
        }

        // 来到这里说明不在左子树 1、是某一祖先节点 2、没有前驱节点
        while (node.parent != null && node == node.parent.left) { // 向父节点上找
            node = node.parent;
        }

        // 来到这里，1、说明要么 parent == null 2、要么 node == node.parent.right
        return node.parent; // 1、【没有前驱节点】 2、【前驱节点是node.parent】
    }

    /**
     * 中序遍历
     * @param node：节点
     * @param visitor：访问器
     */
    private void inorder(Node<K, V> node, Visitor<K, V> visitor) {
        if (node == null || visitor.stop) return;
        inorder(node.left, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.key, node.value);
        inorder(node.right, visitor);
    }

    /**
     * 删除节点
     * @param node：待删除节点
     * @return ：旧的值
     */
    private V remove(Node<K, V> node) {
        if (node == null) return null;
        size--;

        // 1、将度为 2 的节点，转换为删除度为 0 或 1 的节点
        if (node.hasTowChildren()) {
            // 查找前驱or后继节点
            final Node<K, V> predecessor = predecessor(node);
            // 交换它们的 K，V【度为 2 必然有前驱、后继节点】
            node.key = predecessor.key;
            node.value = predecessor.value;
            node = predecessor; // 将其转换为度为 0 or 1 的节点
        }

        // 来到这里说明度肯定为 0 or 1
        Node<K, V> child = node.left != null ? node.left : node.right; // 取出替代它的子节点（如果有）

        if (child != null) { // 2、说明待删除节点的度为 1
            child.parent = node.parent; // 需要改变子节点的父节点
            if (node.parent == null) { // 删除的是根节点
                root = child;
            } else if (node == node.parent.left) { // 待删除节点在左子树
                node.parent.left = child;
            } else { // node == node.parent.right
                node.parent.right = child;
            }
            afterRemove(child); // 将用于取代的子节点传入，删除后的逻辑
        } else { // 3、度为 0
            if (node.parent == null) { // 删除根节点
                root = null;
            } else if (node == node.parent.left) { // 待删除节点在左子树
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }
            afterRemove(node); // 将被删的节点传入删除后的逻辑
        }
        return node.value;
    }

    /*--------------------------↑ ↑ ↑二叉搜索树的辅助函数-------------------------------*/
}
