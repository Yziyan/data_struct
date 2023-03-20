package run.ciusyan.map;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * 哈希表实现的映射
 */
@SuppressWarnings("ALL")
public class HashMap<K, V> implements Map<K, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private static final int DEFAULT_CAPACITY = 1 << 4; // 数组长度需要是 2^n

    private int size;
    private Node<K, V>[] table; // 创建一个红黑树节点数组

    public HashMap() {
        table = new Node[DEFAULT_CAPACITY];
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
        if (size == 0) return;
        size = 0;
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
    }

    @Override
    public V put(K key, V value) {
        int index = index(key); // 拿到key的索引
        Node<K, V> root = table[index]; // 取出索引位置的元素

        if (root == null) { // 此桶第一次放置元素
            root = new Node<>(key, value, null); // 构建红黑树根节点
            table[index] = root; //
            afterPut(root); // 维护红黑树性质
            size++;
            return null;
        }

        // 来到这里说明已经有根节点了，可能会出现哈希冲突

        Node<K, V> parent; // 用于构建红黑树节点
        Node<K, V> currentNode = root; // 用于遍历节点的引用
        Node<K, V> foundRes = null; // 用于接受查找节点的返回值
        int compare; // 用于接收比较的大小
        boolean found = false; // 用于记录此棵红黑树是否扫描过了
        K k1 = key; // 方便查看 k1 和 k2
        int h1 = k1 == null ? 0 : k1.hashCode(); // 计算出 k1 的哈希值
        do {
            K k2 = currentNode.key;
            int h2 = currentNode.hash;

            // 先比较哈希值
            if (h1 > h2) {
                compare = 1;
            } else if (h1 < h2) {
                compare = -1;
            } else if (Objects.equals(k1, k2)) { // 哈希值相等，并且 equals
                compare = 0;
            } else if (k1 != null && k2 != null &&
                k1.getClass() == k2.getClass() && // k1 和 k2 是同一种类型
                k1 instanceof Comparable && // 此类型的 key 具备可比较性
                (compare = ((Comparable) k1).compareTo(k2)) != 0 // k1 - k2 != 0
            ) {

                // 行至此地说明：此 key 具可比较性，并且 k1 != k2
                // 什么也不做即可，因为 compare = 1 or -1；下面会继续右or左遍历

            } else if (found) { // 说明之前已经扫描过此红黑树了，并且未发现节点
                compare = 1; // 也先暂且直接往右走
            } else {
                // 查找左子树 or 右子树，查看 k1 是否已存在
                boolean isFind = (currentNode.left != null && (foundRes = node(currentNode.left, k1)) != null)
                    || (currentNode.right != null && (foundRes = node(currentNode.right, k1)) != null);
                if (isFind) { // 说明 key 已经存在了
                    compare = 0;
                    // 查找到了对应的节点，将其赋值
                    currentNode = foundRes;
                } else {
                    found = true; // 记录已经扫描过了，之后就不用扫描了
                    compare = 1; // 说明 k1 不存在，暂且往右边走
                }
            }

            // 根据比较结果，选择左、右还是覆盖
            parent = currentNode; // 赋值前先保存父节点
            if (compare > 0) {
                currentNode = currentNode.right;
            } else if (compare < 0) {
                currentNode = currentNode.left;
            } else { // 相等，说明没有哈希冲突
                V oldValue = currentNode.value;
                currentNode.key = key;
                currentNode.value = value;
                return oldValue;
            }
        } while (currentNode != null);

        // 来到这里说明出现了哈希冲突，将此节点插入红黑树上
        Node<K, V> node = new Node<>(key, value, parent);

        if (compare > 0) {
            parent.right = node;
        } else {
            parent.left = node;
        }

        afterPut(node); // 维护红黑树性质

        size++;
        return null;
    }

    @Override
    public V get(K key) {

        Node<K, V> node = node(key); // 查找节点
        return node == null ? null : node.value; // 返回对应的 value
    }

    @Override
    public V remove(K key) {
        return remove(node(key)); // 删除对应节点
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null; // 能查找到不为空的节点，说明此key存在
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) return false;

        // 遍历每一棵红黑树
        Queue<Node<K, V>> queue = new LinkedList<>(); // 一会用于层序遍历
        for (Node<K, V> root : table) {
            if (root == null) continue; // 说明此索引位置没有红黑树

            // 说明 i 位置存在红黑树，遍历此树【我这就使用层序遍历了】
            queue.offer(root);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll(); // 弹出栈顶元素
                if (Objects.equals(value, node.value)) return true; // 说明找到相等的了

                if (node.left != null) { // 左子树入队
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right); // 右子树入队
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor == null) return;

        Queue<Node<K, V>> queue = new LinkedList<>();
        for (Node<K, V> root : table) {
            if (root == null) continue; // 说明此索引位置没有红黑树

            // 说明 i 位置存在红黑树，遍历此树【我这就使用层序遍历了】
            queue.offer(root);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll(); // 弹出栈顶元素

                if (visitor.visit(node.key, node.value)) return; // 访问此节点

                if (node.left != null) { // 左子树入队
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right); // 右子树入队
                }
            }
        }
    }

    /**
     * 红黑树节点
     *
     * @param <K>：键 key
     * @param <V>：值 value
     */
    private static class Node<K, V> {
        boolean color = RED; // 节点的颜色，默认为红色
        int hash; // Key的哈希值
        K key;
        V value;
        Node<K, V> parent;
        Node<K, V> left;
        Node<K, V> right;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.hash = key == null ? 0 : key.hashCode();
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

    /**
     * 计算 key 的索引
     *
     * @param key：键
     * @return ：在数组中的索引
     */
    private int index(K key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        return (hash ^ (hash >>> 16)) & (table.length - 1);
    }

    /**
     * 根据 node 计算索引
     *
     * @param node：节点
     * @return ：索引
     */
    private int index(Node<K, V> node) {
        return (node.hash ^ (node.hash >>> 16)) & (table.length - 1);
    }


    /*--------------------------↓ ↓ ↓红黑树的辅助函数---------------------------------*/

    /**
     * 染色
     *
     * @param node：待染色节点
     * @param color：颜色
     * @return ：染色后的节点
     */
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return null;
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
            table[index(node)] = child;
        }
        // 如果有孙子节点，更新孙子节点的父节点
        if (grandchild != null) {
            grandchild.parent = node;
        }
        node.parent = child; // 更新原先待旋转节点的父节点
    }

    /**
     * 根据 key 获取节点对象
     */
    private Node<K, V> node(K key) {
        Node<K, V> root = table[index(key)]; // 取出key位置的节点，因为可以存储 null，不需要进行非空判断
        return root == null ? null : node(root, key);
    }

    private Node<K, V> node(Node<K, V> node, K k1) {

        int h1 = k1 == null ? 0 : k1.hashCode();
        Node<K, V> foundRes; // 用于接收查找结果的引用
        int compare = 0;
        while (node != null) {
            K k2 = node.key;
            int h2 = node.hash;

            // 先比较哈希值
            if (h1 > h2) {
                node = node.right;
            } else if (h1 < h2) {
                node = node.left;
            } else if (Objects.equals(k1, k2)) { // 哈希值相等，并且 equals
                return node;
            } else if (k1 != null && k2 != null &&
                k1.getClass() == k2.getClass() && // k1 和 k2 是同一种类型
                k1 instanceof Comparable && // 此类型的 key 具备可比较性
                (compare = ((Comparable) k1).compareTo(k2)) != 0 // k1 - k2 != 0
            ) {
                // 行至此地说明：key具可比较性，并且 k1 != k2
                node = compare > 0 ? node.right : node.left; // 根据比较结果，决定继续往右还是往左
            } else if (node.left != null && (foundRes = node(node.left, k1)) != null) { // 查找是否存在于左子树
                return foundRes;
            } else {
                // 相当于：（node.right != null && (foundRes = node(node.right, k1)) != null）
                node = node.right;
            }
        }
        return null; // 遍历结束还未查找到对应的节点
    }

    /**
     * 删除对应的节点
     *
     * @param node：待删除节点
     * @return ：删除节点的 value
     */
    private V remove(Node<K, V> node) {
        if (node == null) return null;
        size--;

        V oldValue = node.value; // 取出待删除节点的 Value

        if (node.hasTowChildren()) { // 删除的是度为 2 的节点
            Node<K, V> predecessor = predecessor(node); // 找到对应的前驱节点
            // 替换对应的 K,V,Hash
            node.key = predecessor.key;
            node.value = predecessor.value;
            node.hash = predecessor.hash;
            node = predecessor; // 将前驱节点变成待删除的节点，下面统一处理
        }

        // 来到这里说明度为 0 or 1
        // 找到替代它的子节点
        Node<K, V> child = node.left != null ? node.left : node.right;

        if (node.parent == null) { // 删除的是根节点
            table[index(node)] = child;
        } else if (node == node.parent.left) { // 删除节点在左子树
            node.parent.left = child;
        } else { // 删除节点在右子树
            node.parent.right = child;
        }

        if (child != null) { // 删除节点的度为 1
            child.parent = node.parent; // 维护子节点的父节点
            afterRemove(child); // 修复红黑树性质【这里需要传入子节点】
        } else { // 删除节点的度为 0
            afterRemove(node); // 修复红黑树性质
        }

        return oldValue;
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



    /*--------------------------↑ ↑ ↑二叉搜索树的辅助函数-------------------------------*/


}
