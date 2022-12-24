# TreeMap的实现

## 一、初识Map映射

* 在复习红黑树之前，我们先来看看映射`Map`

* 相信大家大大小小都使用过这本书：

![image-20221123124026595](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/1b1c059bfdfd441d80c64c4701023405~tplv-k3u1fbpfcp-zoom-1.image)



* 没错，就是《新华字典》，而我们接下来要介绍的`Map映射`，其实就和字典差不多，查询到的每一个汉字，都有一个与它对应的详细解释

* 因此，`Map`在有些编程语言中也叫做字典（`dictionary`）
* 就和字典一样，在编程里的体现就是形如`<Key, Value>`这样的键值对，通过一个`Key`，就有与它对应的`Value`

![image-20221124090828000](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/09a454be15fc4508ac8cee244361ff64~tplv-k3u1fbpfcp-zoom-1.image)

* 而实现这种映射的关系，有很多的实现方案，我们下面先用红黑树来实现



## 二、TreeMap的实现

### （1）接口定义 & 基本构造
* 直接上代码，先来切切菜~
* 就不解释这些方法了。因为要从零实现一个`Map`，所以还是将这些接口贴出来了

#### ① 接口定义

```java
public interface Map<K, V> {
    int size();
    boolean isEmpty();
    void clear();
    V put(K key, V value);
    V get(K key);
    V remove(K key);

    /**
     * 查看 Key 存不存在
     */
    boolean containsKey(K key);

    /**
     * 查看 Value 存不存在
     */
    boolean containsValue(V value);

    /**
     * 遍历
     * @param visitor：访问器
     */
    void traversal(Visitor<K, V> visitor);

    /**
     * 访问器抽象类
     */
    abstract class Visitor<K, V> {
        boolean stop;
        public abstract boolean visit(K key, V value);
    }
}
```



#### ② 基本构造

* 之前实现的红黑树只有一个泛型，而我们的Map，有<K, V>两个泛型参数，我们应该如何处理呢？
* 当然，聪明的你肯定能想到这个办法，再提供一个类，形如这样的：

```java
private static class KV<K, V> {
    K key;
    V value;
    // ....
}
```

* 到时候，红黑树就可以用此类作为泛型的参数了：` RBTree<KV> rbTree`
* 这样确实可以做到，接收两个泛型的参数。虽然外界使用没有什么影响，可是内部在使用的时候，还需要中转一层，显得很麻烦，而且也要增加额外的内存
* 况且，如果这样实现的话，那就是继续组合以前写的红黑树了，并不是从零实现一棵红黑树~
* 那下面跟我一起来改造一下，从零利用红黑树来实现`Map`



##### 改造节点

* 因为之前从零分析并且实现过一遍了，所以很多基础性的代码，就直接贴出来了，代码中也配有注释，若有描述不清楚的地方，欢迎讨论，我努力改正~

```java
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
        public String toString() { // 方便调试
            String str = "";
            if (color == RED) {
                str = "RED_";
            }
            return str + "【K：" + key.toString() + "】【V：" + value.toString() + "】【P：" + parent + "】";
        }
    }
```

* 既然觉得用以前的节点不方便，那我们重新写一个节点就行了呗~
* 并且提供一些之后会用到的辅助函数



##### 红黑树的辅助函数

```java
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

    /*--------------------------↑ ↑ ↑红黑树的辅助函数-------------------------------*/
```

* 用于方便维护红黑树的性质，提供的几个辅助函数



##### 简单方法的实现

```java
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

}
```

* 这就是TreeMap内部的基本构造，因为要用红黑树来实现，所以做了很多铺垫
* 有了这些铺垫之后，我们来看看其它方法是如何实现的


### （2）添加相关方法
* 先来添加两个元素进入`Map`，看看该如何添加
```java
map.put("Ciusyan", 666);
map.put("Zhiyan", 999);

```


#### ① `put(K key, V value)`方法

* 因为底层使用红黑树来实现Map，红黑树的前提是二叉搜索树，所以**要先构建二叉搜索树，再维护红黑树的性质**
* 而构建二叉搜索树的前提就是：**元素必须是可比较的**

* 外界给我们Key和Value，我们选用`Key`来构建，所以，在执行此方法前，必须要进行对Key的非空判断
* 当然，Value可以存储空值，不需要判断

```java
    private void keyNotNullCheck(K key) {
        if (key == null) {
            throw new InvalidParameterException("Key 不能为空");
        }
    }
```

* 非空判断后，我们就可以开始构建二叉搜索树了：
    * 如果是第一次添加，那么直接构建根节点即可
    * 如果不是第一次添加，那么先比较节点的大小，找出待添加节点的父节点与该放置它的位置
    * 再根据父节点构建出新的节点，最后将其放在父节点的左边或右边即可
* 将其思路转换为代码：

```java
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
```

* 当然，我们为此方法增加了一个返回值：旧节点的Value，也可以不用此返回值
* 而且这个返回值，只有在节点相等的时候，才可能不会返回`null`
* 而我们里面如果遇到节点相等的情况，会先将`新Key和新Value`覆盖掉`旧Key和旧Value`，至于为什么要覆盖，在前面的文章已经详细解释过了
* 至此，节点就已经添加好了，但是在我们添加节点之后，还需要维护红黑树的性质



#### ② `afterPut(Node<K, V> node)`方法

* 在维护性质前，先来复习一下红黑树的性质：

![image-20221124143729309](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/c378f2f956b6405b8bd479786850a529~tplv-k3u1fbpfcp-zoom-1.image)

* 看完这些性质，我们再来复习一下关于红黑树的添加的分析：

![image-20221109201014803](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0019dd9b53234f2b889774cebf865752~tplv-k3u1fbpfcp-zoom-1.image)

* 上面是将其红黑树转化为了它等价的4阶B树，来看看思路：
    * 如果**父节点是黑色**，那么不需要做额外的处理
    * 如果**父节点是红色**，需要查看它**叔父节点的颜色**
        * 如果叔父节点是红色，那么在添加后会出现上溢的现象。**需要将父节点和叔父节点染成黑色，祖父节点染成红色，再将祖父节点当做新添加的节点，递归调用`afterPut()`方法**
        * 如果叔父节点是黑色，添加后不会出现上溢现象。**利用染色 + 旋转即可维护性质**

* 关于叔父节点是黑色的情况，我单拎出来解释，因为他们需要对树进行**旋转**：
    * 需要双旋才能解决的两种情况（`LR、RL`），染色是：**将自己染成黑色**，祖父节点染成红色
    * 需要单旋即可解决的两种情况（`LL、RR`），染色是：**将父节点染成黑色**，祖父节点染成红色

* 将其思路转换为代码：

```java
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
```

* 配合着思路来看代码，是不是感觉红黑树的添加也不是很难~
* 我们再将其中的旋转操作拎出来复习复习



#### ③ 树的旋转

##### 左旋：`rotateLeft(Node<K, V> node)`

![image-20221103105903379](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/914244dbcf18482a8fb66c04afcfa3d5~tplv-k3u1fbpfcp-zoom-1.image)

```java
    private void rotateLeft(Node<K, V> node) {
        final Node<K, V> child = node.right; // 取出子节点【左旋、在右边】
        final Node<K, V> grandchild = child.left; // 取出孙子节点
        node.right = grandchild; // 自己的右子节点指向孙子节点
        child.left = node; // 将自己旋转到下方

        afterRotate(node, child, grandchild); // 旋转后的操作
    }
```



##### 右旋：`rotateRight(Node<K, V> node)`

![image-20221101142158926](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/06b27d694b8941e489e05905d1819691~tplv-k3u1fbpfcp-zoom-1.image)

```java
    private void rotateRight(Node<K, V> node) {
        final Node<K, V> child = node.left; // 取出子节点【右旋、在左边】
        final Node<K, V> grandchild = child.right; // 取出孙子节点
        node.left = grandchild; // 自己的左子节点指向孙子节点
        child.right = node; // 将自己旋转到下方

        afterRotate(node, child, grandchild); // 旋转后的操作
    }
```



##### 公共代码：`afterRotate(Node<K, V> node, Node<K, V> child, Node<K, V> grandchild)`

```java
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
```

* 配合着图，看看树的旋转，其实也不是很难，就是将某些节点上移、某些节点下移。**交换它们的子节点，维护它们的父节点**





### （3）查询相关方法
* 写完了添加相关的方法，并且成功添加了几个元素进去，我们该如何查询它们呢？

```java
   map.get("Ciusyan");
   map.containsKey("Zhiyan");
   map.containsValue(666);
```

#### ① `get(K key)`

* 根据`Key`，获取对应的`Value`，外部想要获取值，内部得先去获取节点，再通过节点取出`Value`
* 那我们还得提供一个根据`Key`，查找`Node`节点的方法：

```java
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
```

* 思路也不是很难，就是从根节点开始查找。因为红黑树也是一棵二叉搜索树，所以可以比较节点`Key`的大小，来二分查找
* 所以，我们还需要提供一个，比较节点`Key`大小的方法：

```java
private int compare(K k1, K k2) {
    if (comparator != null) { // 有传比较器就使用比较器
        return comparator.compare(k1, k2);
    }
    return ((Comparable<K>)k1).compareTo(k2); // 没有比较器，就默认当做是可比较的
}
```

* 如果外界在使用`TreeMap`的时候，有传入比较器，优先使用`Comparator`的方法
* 如果外界没有传入比较器，那我们就默认它是可以比较的，使用`Comparable`的方法

* 至此，`node(K key)`方法写完了，我们就可实现`get(K key)`方法了

```java
    public V get(K key) {
        final Node<K, V> node = node(key); // 根据 key 获取节点
        return node == null ? null : node.value;
    }
```



#### ② `containsKey(K key)`

* 这个方法是用于查看`Key`，是否存在与容器中
* 那我们完全可以套用刚刚写的`node(K key)`方法来实现

```java
public boolean containsKey(K key) {
    return node(key) != null;
}
```



#### ③ `containsValue(V value)`

* 这个方法是用于查看`Value`，是否存在与容器中
* 因为构建红黑树是利用`Map`的`Key`来构建的，所以不能套用上面的node方法了
* 只能自己遍历，**确保每一个节点都被访问**

```java
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
```

* 之前学习的四种遍历方式，其实都可以。我这里就选择使用层序遍历的方式了
* 这一段代码，应该都能倒背如流了吧！！！



### （4）删除相关方法
* 查询也完成了，来看看如何删除？

```java
    map.remove("Zhiyan");
```

* 外界是根据key来删除对应的value
* 而内部则是根据key，来删除node
* 所以内部是使用：`remove(node(key))`来删除对应的节点

#### ① `remove(Node<K, V> node)`

* 删除二叉搜索树中的节点，应该也不陌生了，我们来看看思路：
    * 查看节点的度是否为`2`，若为2，先将其转换为删除度为 `0 或 1`的节点
        * 找到前驱或后继节点，这里以前驱节点为例，将前驱节点的key和value赋值给待删除节点。然后将前驱节点变成待删除节点
    * 经过上面的逻辑，来到这里说明待删除节点的度要么为`0`，要么为`1`
        * 先取出用于替代它的子节点，如果不为`null`，说明度为1。如果为`null`，说明度为0
        * 再找出待删除节点在它父节点的哪一边，就将那一边用取代它的子节点赋值
* 将其思路转换为代码：

```java
	private V remove(Node<K, V> node) {
        if (node == null) return null;
        size--;

        // 取出被删除节点的值
        final V oldValue = node.value;

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
        return oldValue;
    }
```

* 看上面的代码，二叉搜索树的删除应该不是很难理解。最主要的是其中在删除节点后，对红黑树性质的维护
* 那我们先将上面查找前驱节点的逻辑复习之后，重点来看看`afterRemove()`



#### ② `predecessor(Node<K, V> node)`

* 查找某一节点的前驱节点（如果你查找的是后继节点，思路于此方法相反，可自行实现）
    * 先查看该节点是否拥有左子树：
        * 若有左子树，前驱节点必然在左子树的最右边。那么找到左子树后，一直向右遍历，直到为`null`
        * 若无左子树，前驱节点可能是某一祖先节点，也可能没有前驱节点。那么拿到该节点的父节点向上遍历，直至父节点为空或者该节点位于父节点的右子树。返回此时该节点的父节点即可
* 将思路转换为代码：

```java
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
```

* 感兴趣的朋友，可以自己实现一下如何查找后继节点~
* 至此，节点就已经被删除了，但是在我们删除节点之后，还需要维护红黑树的性质



#### ③ `afterRemove(Node<K, V> node)`

* 之前实现删除，可是费了九牛二虎之力啊，相信现在会轻松许多，我们先来看看被删除节点可能出现的情况：

![image-20221111181854679](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/7c59ff559fdc40389ec04918144c0d0a~tplv-k3u1fbpfcp-zoom-1.image)

* 如图所示，我们来看看思路：
* 如果删除的节点是红色的和删除的黑色节点有两个红色的子节点。**那么不需要做任何处理，直接返回即可**
* 如果删除的节点是黑色的，并且它有一个红色的子节点：**那么将用于取代它的子节点，也就是那个唯一的子节点染成黑色即可**
* 如果删除的节点是黑色，并且一个红色的子节点都没有，删除后会出现下溢现象：**取出兄弟节点**
    * 若为黑色：**查看兄弟节点有没有红色的子节点**
        * 若有：通过旋转 + 染色，向兄弟借一个元素
        * 若没有：将父节点向下合并，如果父节点原先就是黑色。那么还要将它当做是被删除的节点，递归执行`afterRemove()`方法
    * 若为红色：**将其转换为兄弟节点为黑色的情况。通过旋转 + 染色，将侄子变成兄弟。然后执行兄弟节点为黑色的逻辑**
* 将其思路转换为代码：

```java
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
```

* 一定要结合思路和注释，自己分析分析代码~ 相信红黑树对你来说，也不会特别难



### （5）遍历方法

#### ① `traversal(Visitor<K, V> visitor)`

* 说到二叉树的遍历，你应该至少能想到四种方式：前序遍历、中序遍历、后序遍历、层序遍历
* 而外界想要遍历Map中的元素。他们可不知道内部用的什么遍历方式

```java
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        inorder(root, visitor);
    }
```

* 很显然，我这里使用了中序遍历，因为中序遍历是有顺序的。对外界来说，可能会有些许作用。（当然，使用其他方式也是可以的）



#### ② 中序遍历：`inorder(Node<K, V> node, Visitor<K, V> visitor)`

* 方便起见，我这里就直接使用递归的方式了

```java
    private void inorder(Node<K, V> node, Visitor<K, V> visitor) {
        if (node == null || visitor.stop) return; // 注：visitor.stop 用于停止递归调用
        inorder(node.left, visitor);
        if (visitor.stop) return; // 注：visitor.stop 用于取消调用访问逻辑
        visitor.stop = visitor.visit(node.key, node.value);
        inorder(node.right, visitor);
    }
```

* 至此，我们利用红黑树从零实现了映射`Map`，其实也并不是很难很难，是吧~


