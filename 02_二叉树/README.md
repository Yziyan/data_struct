---
theme: channing-cyan
highlight: night-owl
---

# 02_二叉搜索树

## 一、树形结构

![image-20221023151744080](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/49089bf3ae7b4449874325993c783195~tplv-k3u1fbpfcp-zoom-1.image)



* 使用树形结构，可以大大的**提高效率**
* 下面我们一起来了解一下树形结构吧，看看它如何提高效率的
* 生活中也有很多树形结构

![image-20221023152952835](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/76959d8aed2443ac8b45e8315641c15a~tplv-k3u1fbpfcp-zoom-1.image)


## 二、二叉搜索树（Binary Search Tree）

* 有一些关于树的基本概念，在文章的下一小节，如果有什么概念不清楚，可以看看下面的介绍
* 下面，我们直接来学习一下`二叉搜索树`吧~



### （1）问题引入

* 在 n 个动态的整数中查找某一个整数，如何能更高效的搜索
    * 假设使用动态数组存放元素，那就是从头开始遍历搜索，平均时间复杂度：`O(n)`
* 如果这 n 个数是一个有序的
    * 那么使用二分法查找动态数组中的元素，最坏的时间复杂度：`O(logn)`
    * 但是添加、删除的平均复杂度是：`O(n)`
* 对于这样的需求，有没有更好的方案呢？
    * 这时候，我们就引入了高效的**二叉搜索树**
    * 可以将添加、删除、搜索的最坏时间复杂度都优化至：`O(logn)`



### （2）概念&特点&基本构造&接口设计

#### 概念

* 二叉搜索树是一种应用非常广泛的一种二叉树
* 英文缩写为 BST，又被称为二叉查找树、二叉排序树

![image-20221024100512480](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/8bca819f6d6146989a9f93d22edbf3c2~tplv-k3u1fbpfcp-zoom-1.image)

#### 特点

* 任意一个节点的值都**大于其左子树**所有节点的值
* 任意一个节点的值都**小于其右子树**所有节点的值
* 任何一个节点的**左右子树**也都是二叉搜索树
* 存储的元素必须**具有可比较性**
    * 存储的元素不能为 `null`



#### 基本构造

```java
public class BinarySearchTreeImpl<E> {

    private int size;
    /**
     * 根节点
     */
    private Node<E> root;
    /**
     * 内部节点类
     */
    private static class Node<E> {
        /**
         * 元素
         */
        E element;
        /**
         * 左子节点
         */
        Node<E> left;
        /**
         * 右子节点
         */
        Node<E> right;
        /**
         * 父节点
         */
        Node<E> parent;
        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }
    }
}
```

* 同链表一样，二叉搜索树的内部也要维护一个节点对象，而具体的元素，应该放在节点的内部
* 而每一个节点都有三个节点的引用`左子节点、右子节点、父节点`
* 而且二叉搜索树本身，还需要一个根节点的引用



#### 接口设计

```java
public interface BinarySearchTree<E> {

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
```

* 你可能已经发现了，二叉搜索树不像之前的数组、链表。它是没有索引的
* 因为我们没法标索引，后添加的元素，也可能在上面一层



### （3）添加节点——add(E element)

#### ① 第一次添加——根节点

* 我们想要构建一棵二叉搜索树，肯定得先找到根节点。从根节点依次往下发散延伸
* 那一开始的时候，根节点肯定是`null`的，也就是，我们得先构建根节点

```java
    public void add(E element) {
        elementNotNullCheck(element);
        if (root == null) { // 添加根节点
            root = new Node<>(element, null);
            size++;
            return;
        }
        // 来到这说明不是第一次添加
    }
```

* 如上代码，很简单，我们刚刚说了，二叉搜索树中节点的元素不能为`null`，所以我们添加元素时，得进行非`null`判断：`elementNotNullCheck(element)`，判断很简单，就不贴代码了
* 如图所示，我们第一次添加`10`，也就是构建出了根节点

![image-20221024112456363](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/651dacfd1959454b8d43c82d14ccb594~tplv-k3u1fbpfcp-zoom-1.image)



#### ② 不是根节点

* 那如果不是根节点，比如说我们想要继续添加`5和20`，该如何添加进去呢？
* 要想添加节点，肯定得先利用元素构建节点吧
* 而想要构建节点，肯定得先找到他的父节点吧
* 构建好节点之后，我们来复习一下二叉搜索树的性质：**每一个节点的左子节点都比自己小，右子节点都比自己大**
* 那既然牵扯到大小关系，是不是应该要有节点元素的大小比较呢？
* 比较完大小之后，是不是就知道，将刚刚构建好的节点，放在左边、还是右边了

![image-20221025200435201](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0e19664936a145a18be7aef188055e95~tplv-k3u1fbpfcp-zoom-1.image)

* 如上图所示，我们先添加`20`，那么就用 `20 和它的父节点 10 比大小`，发现自己 `> 10`那么将其放在父节点`parent`的右子节点`right`处
* 后添加的 `5` ，那么就用 `5 和它的父节点 10 比大小`，发现自己 `< 10`，那么将其放在父节点`parent`的左子节点`left`处
* 这个时候你可能会发现，我们上面只有大于和小于的情况，如果值相等，怎么办呢？
    * 你思考一下，如果我们添加的值是数字类型，那么比较大小，遇见和自己值相等的情况，我们需要处理吗？
        * 答案是：其实可以不处理，直接返回即可
    * 那如果不是数字类型，是一个`Person`对象呢？
        * 那么就不建议直接返回，建议覆盖掉旧的值。先留一个思考，之后再解答~



```java
    public void add(E element) {
		// ... 与上面一样的代码
        
        // 来到这说明不是第一次添加
        // 找到父节点
        Node<E> parent = root;
        Node<E> currentNode = root;
        int compare = 0;

        // 从根节点开始，依次往下寻找
        while (currentNode != null) {
            // 待添加节点与父节点比较大小
            compare = compare(element, currentNode.element);
            // 在比较后，先保存父节点，再改变指向
            parent = currentNode;

            if (compare > 0) { // 待添加的节点 > 当前节点
                currentNode = currentNode.right;
            } else if (compare < 0) { // 待添加的节点 < 当前节点
                currentNode = currentNode.left;
            } else { // 相等的情况
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
```



* 我们最开始的时候，只能拿到根节点，那就得从根节点开始，依次往下寻找。该节点应该添加在哪里
* 并且在遍历到当前节点为 `null`的时候，需要找出待添加节点的父节点、并且需要记录，应该添加到父节点的左边还是右边
* 如果遇到待添加节点与某一个节点相等的情况，那我们先直接返回，之后再做覆盖操作（上面的思考继续留着~）
* ❓到现在，你应该发现了，我们还有一个很大的问题没有解决——如何比较大小？



### （4）如何比较大小——compare(E e1, E e2)

#### ① 思考

* 比较大小，我们很容易就能想到，`大的数 - 小的数 ? 0`。这样即可知道，谁大谁小了。
* 然后上来balabal的将比较逻辑写到`BinarySearchTree`的`compare()中`。
* 可是，放入二叉搜索树中的元素，不是数字类型（比如说：我们想要比较`Person`，并且认定年龄较大的`Person`比较大）这时又该怎么办呢？难到又要改我们内部写的`compare()`方法吗？
* 学过`Java`的同学应该知道，`Java`给我们提供了两个接口：`Comparable、Comparator`。【使用自己写的也是可以的，但是Java默认的很多包装类，就不会实现自己的比较器，需要自己二次包装，所以直接使用官方的即可】那我们就分别来用`Comparable、Comparator`接口，实现一下如何比较吧~

#### ② 使用 `Comparable`接口

* 见名知意，意味着某个类实现了此接口中的`compareTo()`方法，那么就是一个可比较的类了
* 想要使用我们的二叉搜索树，**必须具有可比较性**
* 那么在定义泛型的时候，就可以让对方必须实现`Comparable`接口

```java
public class BinarySearchTreeImpl<E extends Comparable<E>> implements BinarySearchTree<E> {
    
    private int compare(E e1, E e2) {
        return e1.compareTo(e2);
    }
}
```

* 在二叉搜索树的内部，仅仅需要去调用`Comparable接口中的compareTo()方法即可`
* 那么在外部使用时，所传入的泛型，就必须实现`Comparable`接口中的`compareTo()`方法

```java
// 实体类
public class Person implements Comparable<Person> {
    private int age;
    private int height;

    @Override
    public int compareTo(Person o) {
        return age - o.age;
    }
}
// 外部即可直接使用
BinarySearchTree<Person> bst = new BinarySearchTreeImpl<>();
```

* 这样，我们是不是就将比较的逻辑，写在了外部。而且也实现了自定义比较逻辑
* 以为这样就结束了吗？那我们再来思考一个问题。
    * 如果我有再进一步的需求：有两棵`Person`的二叉搜索树`bst1、bst2`
    * `bst1`是根据年龄大的对象比较大来构建的
    * `bst2`是根据身高高的对象比较大来构建的
    * 那按上面的办法，`Person`类，是不是就不够用了

```java
// 按年龄较大的Person构建的一棵二叉搜索树
BinarySearchTree<Person> bst1 = new BinarySearchTreeImpl<>();
// 按身高较高的Person构建的一棵二叉搜索树
BinarySearchTree<Person> bst2 = new BinarySearchTreeImpl<>();
```

* 那么，这时候，我们就可以引出`Comparator`接口了



#### ③ 使用`Comparator`接口

* 上面的需求，无非是在使用我们的二叉搜索树`BinarySearchTree`时，想要动态传入比较的逻辑
* 那么使用`Java提供的比较器Compartor接口`，即可实现
* 在使用`BinarySearchTree`时，必须传入一个比较器

```java
public class BinarySearchTreeImpl<E> implements BinarySearchTree<E> {
    /**
     * 比较器
     */
    private Comparator<E> comparator;
    
    public BinarySearchTreeImpl(Comparator<E> comparator) {
        this.comparator = comparator;
    }
    
    private int compare(E e1, E e2) {
        return comparator.compare(e1, e2);
    }
}
```

* 那我们在外界使用时，就需要这样使用了

```java
        BinarySearchTree<Person> bst1 = new BinarySearchTreeImpl<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge() - o2.getAge();
            }
        });

        BinarySearchTree<Person> bst2 = new BinarySearchTreeImpl<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getHeight() - o2.getHeight();
            }
        });
```

* 其中使用了`匿名类的写法`，能够更清楚的看出我们的需求。当然，你还可以使用`Lambda`的方式来简化代码
* 我们这样设计，确实解决了上面的需求，可是我们再每一次使用的时候，都需要传入一个比较器`Comparator`，真的很麻烦。那我们能否中和一下刚刚的两种方式呢？



#### ④ 中和使用`Comparable 和 Comparator`接口

* `Comparable`的方式，可以不需要每一次都传入比较逻辑，但是不可以定制化多次比较逻辑
*  `Comparator`的方式，可以定制化多次比较逻辑，但是，每次使用时，都必须传入比较逻辑

* 如果把他们结合起来，是不是就完美了~

```java
public class BinarySearchTreeImpl<E> implements BinarySearchTree<E> {
    /**
     * 比较器
     */
    private Comparator<E> comparator;

    public BinarySearchTreeImpl() {
        this(null);
    }

    public BinarySearchTreeImpl(Comparator<E> comparator) {
        this.comparator = comparator;
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
```

* 外界使用时，如果传入了比较器。那么优先使用比较器`Comparator`
* 如果使用时没有传比较器，我们就默认对方实现了`Comparable`接口。因为我们的二叉搜索树，本身就需要具有可比较性
* 如果外界既没有传入比较器`Comparator`，也没有实现`Comparable`接口，那么肯定会有异常
* 这样就达到了我们的需求，在外界使用的时候，就可以这样使用了~

```java
// 实体类
public class Person implements Comparable<Person> {
    private int age;
    private int height;

    @Override
    public int compareTo(Person o) {
        return age - o.age;
    }
}

// 使用Comparable接口
BinarySearchTree<Person> bst1 = new BinarySearchTreeImpl<>();
// 使用Comparator接口
        BinarySearchTree<Person> bst2 = new BinarySearchTreeImpl<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getHeight() - o2.getHeight();
            }
        });
```



* 看完上面的比较的设计，是不是觉得豁然开朗呢？可是我们还有一个问题没有解决勒。
* 值相等如何处理？



#### ⑤ 值相等的处理

* 我们上面的实现是：如果发现值相等，不做任何处理，直接返回` else { return; } // 相等的情况 `

* 要说有问题吧，我觉得也没有问题。比如说原先的节点是`10、5、20`
* 那么再添加一个`20`时，发现已经有`20`这个节点了，直接返回，仿佛也没任何不妥，那如果是下面这种情况呢？

![image-20221026100228422](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4f9741cbf6c942ac994793c24fc85f18~tplv-k3u1fbpfcp-zoom-1.image)



* 上图是按照`Person的age大小`构建的一棵二叉搜索树
* 这时候，`P3：ZY的年龄为 20`，这时候想添加一个年龄也为`20的 P4：John`
* 按之前的逻辑，直接返回的话，那么树上就还是 `P3 对象`，和新添加的`P4`都不是同一个人，是不是有些不合理呢？

![image-20221026100809144](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2320eebec6544627a926f5d2a13fac7d~tplv-k3u1fbpfcp-zoom-1.image)

* 所以，我们最终选择了覆盖再返回，即：

```java
else { // 相等的情况
    currentNode.element = element; // 覆盖原先的值
    return;
}
```



### （5）二叉树的遍历

* 刚刚添加了一些元素，我们来看看应该如何遍历，也就是把树中的所有元素都访问一遍
* 之前所学习的线性数据结构的遍历，都比较简单，用它们的索引，`正序遍历、逆序遍历`即可
* 而二叉树的遍历，根据节点访问顺序的不同，常见的遍历方式有
    * 前序遍历：`Preoder Traversal`【根节点在最前即可】
    * 中序遍历：`Inoder Traversal`【根节点在中间即可】
    * 后序遍历：`Postorder Traversal`【根节点在后面即可】
    * 层序遍历：`Level Order Traversal`【从上层往下层，依层遍历】
* ❗这四种遍历方式，不仅适用于刚写的二叉搜索树，只要是一颗二叉树即可

![image-20221026144315525](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/af3e77b9e6aa425b986b6f57baedec76~tplv-k3u1fbpfcp-zoom-1.image)



#### ① 前序遍历（Preoder Traversal）

* 前序遍历中元素的访问顺序是：`根节点 -> 前序遍历左子树 -> 前序遍历右子树`

* 如上面的一棵二叉搜索树，使用前序遍历的方式，元素的访问顺序是如何的呢？

* 先访问根节点：`10`
* 再访问根节点的左子树、那么紧接着访问的就是`10节点`的左子树的根节点： `5`
* 依次访问完左子树后，再去访问右子树
* 那么最终的顺序应该是：`10、5、3、1、4、7、9、20、14、11、24`

![image-20221026144055898](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/09e61dd5ade6451f869aafaa4aeab93d~tplv-k3u1fbpfcp-zoom-1.image)

* 经过上面的思路分析，你肯定能想到，使用递归的方式，能够很简单的实现该功能

```java
    private void preorderTraversal(Node<E> node) {
        if (node == null) return;
        // 先访问根节点
        System.out.println(node.element);
        // 再使用递归方式，前序遍历左子树
        preorderTraversal(node.left);
        // 最后使用递归方式前序遍历右子树
        preorderTraversal(node.right);
    }
```



#### ② 中序遍历（Inoder Traversal）

* 中序遍历中元素的访问顺序是：`中序遍历左子树 -> 根节点 -> 中序遍历右子树`
* 如上面的一棵二叉搜索树，使用这样的中序遍历方式，元素访问的最终顺序是：`1、3、4、5、7、9、10、11、14、20、24`
* 或者还可以：`中序遍历右子树 -> 根节点 -> 中序遍历左子树`，只要将根节点放中间即可

* 使用这样的中序遍历方式，元素访问的最终顺序是：`24、20、14、11、10、9、7、5、4、3、1`

![image-20221026145748178](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/8d428915ecdd48cf8101924c90e6abc6~tplv-k3u1fbpfcp-zoom-1.image)

* 细心的你可能发现了，如果使用中序遍历的方式来遍历**二叉搜索树**，遍历结果要么是升序、要么是降序
* 如果先访问左子树，将会是升序排列。如果先访问右子树，将会是降序排列

```java
    private void inorderTraversal(Node<E> node) {
        if (node == null) return;
        // 先使用递归方式，中序遍历左子树
        inorderTraversal(node.left);
        // 再访问根节点
        System.out.println(node.element);
        // 最后使用递归方式，中序遍历右子树
        inorderTraversal(node.right);
    }
```



#### ③ 后序遍历（Postorder Traversal）

* 后序遍历中元素的访问顺序是：`后序遍历左子树 -> 后序遍历右子树 -> 根节点 `
* 如上面的一棵二叉搜索树，使用后序遍历方式，元素访问的最终顺序是：`1、4、3、9、7、5、11、14、24、20、10`

![image-20221026151355319](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/896fe3fc833d48dcaa6ef69d9e88ff1c~tplv-k3u1fbpfcp-zoom-1.image)



```java
private void postorderTraversal(Node<E> node) {
    if (node == null) return;
    // 先使用递归的方式，后续遍历左子树
    postorderTraversal(node.left);
    // 再使用递归的方式，后续遍历右子树
    postorderTraversal(node.right);
    // 最后访问根节点
    System.out.println(node.element);
}
```



####  ④ 层序遍历（Level Order Traversal）

* 层序遍历的元素访问顺序是按层依次访问
* 就不像上面的`前、中、后序`那么简单了，那该如何访问呢？
* 我们也先来看看使用层序遍历，元素最终的访问顺序：`10、5、20、3、7、14、24、1、4、9、11`

![image-20221026152503474](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/db094354200e4259860a8c5803ed8d76~tplv-k3u1fbpfcp-zoom-1.image)

* 我们可以发现，先访问的节点，进入下一层时，它的子节点也会先被访问
    * （如：访问第二层：先访问 5 后访问 20，那么进入第三层时，5 的子节点3 、7也会先被访问 ，再访问 20 的子节点 14、24）
* 回想一下上次学习的队列，是不是就是这样的思想：**先进先出**

![image-20221026154955439](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/613e7dd10a3f4ae4878f428f25c5dd47~tplv-k3u1fbpfcp-zoom-1.image)

* 思路也写在图中了，这时我们已经准备好一个队列了。并且将根节点`10`放入队列中了，只需要循环执行如下图所示内容，直至队列为`empty`

![image-20221026155256956](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/86ff5ac4dce242c7afdbb09deb872ca6~tplv-k3u1fbpfcp-zoom-1.image)

```java
public void levelOrderTraversal() {
        if (root == null) return;

        // 准备队列，并且将根节点入队
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // 将队头元素出队
            Node<E> node = queue.poll();

            // 访问队头元素
            System.out.println(node.element);

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
```

* 四种不同遍历顺序的二叉树遍历方式，都已经完成了
* ❓可是，真的完成了吗？万一我不仅仅想打印元素呢？我想要自定义访问逻辑，又该怎么办呢？



#### ⑤ 改造成访问者模式

* 我们原先的访问逻辑，都是写在遍历逻辑的内部。而且都是最简单的打印操作
* 和前面写的比较器类似的想法。我们能不能做到，每一次遍历，可以自定义访问逻辑，让其更加的灵活呢？

```java
    /**
     * 用于访问内部某些细节
     */
    public interface Visitor<E> {
        /**
         * 访问时，执行的操作
         * @param element：被访问元素
         */
        void visit(E element);
    }
```

* 先定义一个访问者的接口，❗注意：**让其访问元素，而不是节点Node**，因为节点对外是不可见的
* 在需要遍历的时候，让对方传入一个访问者接口`Visitor`，并且实现`visit()`方法，告诉遍历时，如何访问元素

```java
// 层序遍历
public void levelOrder(Visitor<E> visitor) {}
// 前序遍历
public void preorder(Visitor<E> visitor) { }
// 中序遍历
public void inorder(Visitor<E> visitor) { }
// 后序遍历
public void postorder(Visitor<E> visitor) { }
```

* 那我们的递归函数也需要改造了
* 下面以中序遍历为例，其余的类似

```java
    public void inorder(Visitor<E> visitor) {
        if (visitor == null) return;
        inorder(root, visitor);
    }

	private void inorder(Node<E> node, Visitor<E> visitor) {
        if (node == null) return;
        inorder(node.left, visitor);
        // 访问根节点
        visitor.visit(node.element);
        inorder(node.right, visitor);
    }
```

* 这样就完成了吗？
* 上面的需求确实完成了：可以动态传入访问逻辑。
* ❓那如果有一万个元素，可是我们仅仅想要遍历到第十个元素呢？



#### ⑥ 增强访问器

* 在外界使用者的眼中，可能遍历到某种程度，就不想遍历了。可是我们的操作，就是将其全部遍历
* 所以，我们可以做以下的增强

```java
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
```

* 将接口变成抽象类【因为接口不可以放置**成员**变量】
* 增添成员变量`stop`，用于记录每一次遍历时，下一次是否需要停止访问
* 将访问方法`visit()`增添`bolean`类型的返回值，返回 true 就不访问了【因为实现此方法时，默认的返回值就是`false`（当然，是看你自己的设计）】

* 通过上面的改造后，外界就可以这样使用了

```java
        // 使用前序遍历
		bst.preorder(new BinarySearchTreeImpl.Visitor<Integer>() {
            @Override
            protected boolean visit(Integer element) {
                System.out.print(element + " ");
                // 默认情况，需要全部遍历
                return false;
            }
        });
		// 使用层序遍历
        bst.levelOrder(new BinarySearchTreeImpl.Visitor<>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                // 当遍历到元素为 7 时，停止遍历
                return element == 7;
            }
        });
```

* 那我们内部应该如何使用这个返回值呢？
* 层序遍历不涉及递归操作，我们先看看如何改造它

![image-20221026201935506](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/bf3be93e2a6b4d65b78e3d6dc3560554~tplv-k3u1fbpfcp-zoom-1.image)

* 拿到`visit()方法`的返回值做判断即可【❗注意（我们这里的设计是，当返回 true 时，就结束遍历）】

* 下面的三个方法，都是使用递归来实现的
* 如何来记录需要停止操作了呢？【在`Visitor`中提供一个成员变量】

![image-20221026201555542](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2a522bfa4f1c4bc78217c90ccfb0565e~tplv-k3u1fbpfcp-zoom-1.image)

* ❓看看内部的实现，除了前序遍历，为什么要进行两次判断呢？
* 它们的作用是不相同的
    * 第①次：用于停止递归调用
    * 第②次：用于判断是否还需要调用访问逻辑



#### ⑦ 遍历的作用【二叉树】

* ❗**注**：下面所讲遍历的作用，也是不局限于二叉搜索树，只要是一棵二叉树即可

##### 1、打印出树状结构

![image-20221026210723860](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/f7eea33308124842a9ddbd1dffd56d8b~tplv-k3u1fbpfcp-zoom-1.image)

##### 2、利用中序遍历将二叉搜索树按升序或者降序排列

##### 3、利用后序遍历，可以做一些先子后父的操作

##### 4、利用层序遍历，可以计算二叉树的高度、判断一棵树是否为完全二叉树

###### 计算二叉树的高度

1. 递归的方式

```java
    public int height() {
        return height(root);
    }

    /**
     * 计算树的高度【递归法】
     */
    private int height(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }
```

* 递归的思路很简单：`树的高度 = Math.max(左子树高度, 右子树高度) + 根节点占的高度`



2. 层序遍历的方式

* 核心点还是层序遍历，主要思想是：`树的高度 = 树的层数`
* 也就是要根据层序遍历，得出树的层数。进而算出高度
* `而树的层数 = 何时进入下一层? + 进入下一层几次?`
* 弄清楚这两个问题，即可知道如何求树的高度。回看一下之前的一张图片

![image-20221027082727634](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/b2fe493343494bc09812dabc3c2483af~tplv-k3u1fbpfcp-zoom-1.image)

* 何时进入下一层，不难想清楚：当某一层元素访问完时，进入下一层【如果还有】
* 那我们怎么知道，某一层的元素什么时候被访问完呢？
* 是不是可以记录一下每一层的元素，可是如何记录呢？
* 看看图中每一层已访问的元素，每层都访问完成时，看看队列里现有的元素个数，是不是就是下一层元素个数的总数呢~
* 搞清楚了何时进入下一层遍历。那么是不是进入下层遍历多少次，树的高度就会加几次啊

```java
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
                queue.offer(node.right);F
            }
            
            // 当每层元素个数为 0 时，说明要开始遍历下一层了【如果还有的话】，有几层，height就等于多少
            if (levelSize == 0) {
                levelSize = queue.size();
                height++;
            }

        }
        return height;
    }
```



###### 判断一棵树是否为完全二叉树

* 在实现练习前，我们先看看，什么样的二叉树，是完全二叉树

![image-20221027092459169](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/8812917d9e4b4666944fa8fc2605ea57~tplv-k3u1fbpfcp-zoom-1.image)

* 看了上面的定义，用数学排列组合的思想来看，判断一棵二叉树是否为完全二叉树，也就是需要排列出四种情况
    * ①：`左子节点 != null，右子节点 != null`
    * ②：`左子节点 != null，右子节点 == null`
    * ③：`左子节点 == null，右子节点 != null`
    * ④：`左子节点 == null，右子节点 == null`
* 思路也就如下、图所示
    * 第①种情况：度为 2 ，直接将`左右节点都入队`即可
    * 第②种情况：度为 1 ，且靠左对齐。说明之后遍历到的节点，都必须是**叶子节点**【`节点的度 = 0`】
    * 第③种情况：度为 1，不满足左对齐。说明可以直接返回 `false`了
    * 第④种情况：度为 0 ，说明之后遍历到的节点，都必须是**叶子节点**

![image-20221027094843698](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/bfc1b1f4a2b346cc9cdb350b2fd559dd~tplv-k3u1fbpfcp-zoom-1.image)



* ❗**注**：上面为了写清楚我们对应的四种情况，没有简化判断逻辑【IDEA都看不下去了~】
* 对应着我们上面的四种排列组合。确实很清晰，也利用了层序遍历的思想，但是代码也太难看了吧🥶而且有很多重复判断
* 改造代码：

```java
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
```

* 这样看代码，是不是好看多了~✌️，但核心思路还是：`四种情况 + 层序遍历`



### （6）删除元素——remove(E element)

* 我们想要删除某一元素，在外界的眼中，删除的是某一元素。而在二叉搜索树的内部其实是删除对应的节点
* 所以得先根据待删除元素查找对应的节点，再将其节点删除

```java
private Node<E> node(E element) {
    if (element == null) return null;
    Node<E> node = root;
    while (node != null) {
        int compare = compare(element, node.element); // 对比元素的大小
        if (compare == 0) return node; // 和自己相等，就是我们需要找的节点
        if (compare > 0) { // 说明节点可能在右边，继续查找
            node = node.right;
        } else { // 说明节点可能在左边，继续查找
            node = node.left;
        }
    }
    // 没有找到对应的节点
    return null;
}
```

* ❓❓❓查找到了节点，我们来思考一下这几个问题
    * 删除的有哪几种节点呢？
    * 删除节点的处理方式都是一致的吗？
    * 该如何删除该节点呢？
* 二叉搜索树上的每一个节点，度只能有 `0、1、2`
* 那么，也就是说，待删除的节点就是这③种情况，我们试着来分析一下

![image-20221028184246955](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/9a4b6a925a91463794f68e94ed1c3e85~tplv-k3u1fbpfcp-zoom-1.image)

#### ① 删除度为 0 的节点

* 度为 0 的情况，咱们直接找到这个节点，让其**父节点取消对它的引用即可**
* 但是这棵二叉树也可能仅有一个节点`root`，直接让`root = null`即可

![image-20221028185252641](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/02ccd7a8447849c6a115809da7af01cb~tplv-k3u1fbpfcp-zoom-1.image)

* 将其思路转化为代码：

```java
        if (node.parent != null) {
            if (node == node.parent.left) { // 待删除节点在它父节点的左边
                node.parent.left = null;
            } else { // node = node.parent.right
                node.parent.right = null;
            }
        } else { // 仅有一个根节点
            root = null;
        }
```



#### ② 删除度为 1 的节点

* 度为 1 的情况，咱们得先找到它的**子节点`child`**
* 然后将它**子节点的父节点**指向它的**父节点**，最后改变它**父节点的子节点**的引用即可
* 但是它的父节点可能为 `null`，也就是下图的第二种情况。那么在第②步时，直接将根节点`root`指向它的子节点即可

![image-20221028191659834](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/182f7930dcb74d24979350902ae88794~tplv-k3u1fbpfcp-zoom-1.image)

```java
        Node<E> child = node.left != null ? node.left : node.right; // 取出子节点
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
        } 
```

#### ③ 删除度为 2 的节点

* 在谈如何删除度为 2 的节点前，我们先探讨两个概念

##### 前驱与后继节点

* ❗**注**：下面所讲前驱与后继节点，不局限于二叉搜索树，只要是一棵二叉树即可

* 之前学的链表，也有前驱节点和后继节点的概念，也就是**遍历时**，某一节点前一个节点和后一个节点
* 那我们的二叉树的前驱节点和后继节点是什么呢？我们先来说说前驱节点，后继节点自然就懂了
* 前驱节点（predecessor）：**二叉树中序遍历**时，节点的**前**一个节点
* 如下图所示：

![image-20221027200208506](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/7dc6af413434482b8a2708901b0d5585~tplv-k3u1fbpfcp-zoom-1.image)

* ❓了解了什么是前驱节点，如果让我们求某个节点的前驱节点，思路又该如何呢？
* 先看一看我这灵魂画手秒的边，关于二叉树的中序遍历顺序

![image-20221027194832344](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/67af34451e16407e9e92799774abe5c2~tplv-k3u1fbpfcp-zoom-1.image)

* 我们可以发现，任意节点的前驱节点，有如下几种情况：

![image-20221027201503848](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/6674bc2f402449f48d0365b83d5ebdfa~tplv-k3u1fbpfcp-zoom-1.image)

* 那我们分别来看看这几种情况吧
* 情况①：当某一个节点拥有左子树时，那么它的前驱节点一定在**左子树**上面，而且是在左子树的**最右边**
* 情况②：没有左子树时，只能**一直找父节点**，当此节点在父节点的右子树中时，才有前驱节点

![image-20221027203642068](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/c7b4c6bc5c4b47b08e43b1bfb6b2d3d5~tplv-k3u1fbpfcp-zoom-1.image)

* 看图说话，没有左子树，**向上找父节点时必须要拐弯**，才有前驱节点
* 一直向上找父节点，当父节点为`null`时都还没拐弯，说明没有前驱节点
* 情况③：没有前驱节点的情况【也就是中序遍历时，**第一个遍历**到的节点】

```java
private Node<E> predecessor(Node<E> node) {
    if (node == null) return null;

    Node<E> predecessorNode = node.left;
    if (predecessorNode != null) { // 来到这说明有左子树，前驱节点一定在左子树上
        while (predecessorNode.right != null) {
            predecessorNode = predecessorNode.right; // 找到左子树中，最靠右的节点
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
```

* 我们上面详细分析了前驱节点，那我们下面来说说，与其对称的后继节点
* 后继节点（successor）：**二叉树中序遍历**时，节点的**后**一个节点
* 看看下面的代码，就知道它和查询前驱节点，有多么对称了

![image-20221028192918027](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e7b6c7e4c06a4677817c2b39eb442374~tplv-k3u1fbpfcp-zoom-1.image)



* 好了，如果你了解了什么是前驱、后继节点，那我们就方便探讨，如何删除二叉搜索树度为 2 的节点了

* 我们直接看代码

![image-20221028194225191](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/523f31cbc3a24b528e8dae7d02ec51e9~tplv-k3u1fbpfcp-zoom-1.image)

* 核心思路：去**删除度为 2** 的节点，**转换成**删除**度为 0 或 1** 的节点【前驱节点 或者 后继节点】

![image-20221028195234972](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/8618168bba294e6cbb97149fc9fe20a8~tplv-k3u1fbpfcp-zoom-1.image)



#### ④ 最终实现

```java
private void remove(Node<E> node) {
    if (node == null) return;
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
```



### （7）二叉搜索树的复杂度分析

* 我们实现完上述`增删改查`的接口，那我们最后来做一下复杂度分析吧
* 如下一棵二叉搜索树，节点的添加顺序为：`[10, 5, 20, 2, 8, 14, 24]`

![image-20221031141409696](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0aa6c08bcd1e466583226f3d616a50aa~tplv-k3u1fbpfcp-zoom-1.image)

* 按这样的添加顺序，构建好的一棵树：`高度: h = 3，元素数量：n = 7`
* 那我们增删改查的操作，都需要去比较节点的大小。再决定从左子树查找？添加到右子树？还是从右子树删除？
* 一般情况下，最多只需要比较 `h（树的高度）`次即可。时间复杂度也就为：`O(h) = O(logn)`
* 如果有 1000000 条数据，树的高度最低仅仅只有 20 ，是不是大大提高了效率？
* 那如果按这样的顺序添加呢？`[2, 5, 8, 10, 14, 20, 24]`

![image-20221031142703181](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/8fcd284501ac4448af7047276130f69c~tplv-k3u1fbpfcp-zoom-1.image)



* 哎呀，怎么这么眼熟呢？这不是上节课学习的**链表**吗，是的，它退化成了一个链表
* 按这样的添加顺序，构建好的一棵树：`高度: h = 7，元素数量：n = 7`
* 那么他的复杂度就和链表的复杂度一样了。删除也可能使二叉搜索树退化成链表
* 那么，我们有没有什么办法，防止二叉搜索树退化成链表呢？也就是让其添加、删除、搜索的复杂度维持在 `O(logn)`




## 三、树的基本概念【可以略过】

### （1）最基础概念

* 都是一些基本概念，很容易理解，就统一过一下
* 如下有一颗树

![image-20221023154051274](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/bf682ff958374009853dc86a22cff5b3~tplv-k3u1fbpfcp-zoom-1.image)

**节点**

* 每一个元素都是放在一个节点中的

* 有这样几种节点：根节点、父节点、子节点、兄弟节点
* 一颗树可以没有任何节点，这样的树被称为**空树**
* 叶子节点：度为0的节点
* 非叶子节点：度不为0的节点

**子树**

* 树的每一个节点下面，可以看做是有很多颗子树组成的
* 有左子树、右子树

**度**

* 节点的度：子树的个数
* 树的度：所有节点度中的最大值

**层数**：根节点在第一层，依次往下数到最远的叶子节点所在的层

**深度**：从根节点到当前节点的唯一路径上的节点总数

**高度**：从当前节点到最远叶子节点的路径上的节点总数

* 一整颗树的**高度等于深度**

**有序树**：树中任意节点的子节点之间**有顺序关系**

**无序树**：树中任意节点的子节点之间**没有顺序关系**，也叫自由树



### （2）二叉树（Binary Tree）

* 这里也是说一些概念性的东西

![image-20221023160234756](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/a150682e97954620a6ea30b474f9dd13~tplv-k3u1fbpfcp-zoom-1.image)

#### 二叉树的特点

* 每个节点的度，最大为2（每个节点最多拥有2棵子树）
* 左子树和右子树是**有顺序**的【有序树】
* 即使某一节点只有一棵子树，也要区分左右子树

![image-20221023160819002](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/869d9ce70ab24973aa3163355d587fe4~tplv-k3u1fbpfcp-zoom-1.image)

* 上面这些，都是二叉树
* 空树也属于二叉树



#### 二叉树的性质

![image-20221023162933256](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/21cc4f4570f14dd3a5bdffbf11bd341b~tplv-k3u1fbpfcp-zoom-1.image)



* 不好写上标，只能借助外界截图

![image-20221023164358822](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/c4d0104e0e33474fb11345642a12b6ed~tplv-k3u1fbpfcp-zoom-1.image)

#### 特殊的二叉树

**1、真二叉树**

* 所有节点的度，不能为 `1`，只能是`0 or 2`

**2、满二叉树**

* 必须是一棵真二叉树，并且所有的**叶子节点都在最后一层**

![image-20221023164924422](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/dc1b8f96a14a45e68a8c0296a1762905~tplv-k3u1fbpfcp-zoom-1.image)



* 在同样高度的二叉树中，满二叉树的叶子节点的数量最多，总节点数量最多
* 满二叉树一定是真二叉树，真二叉树不一定是满二叉树

![image-20221023165730155](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e1096587b811468ab81f6810faae92bd~tplv-k3u1fbpfcp-zoom-1.image)



**3、完全二叉树**

* 叶子节点**只会出现在最后2层**，且最后一层的**叶子节点都靠左对齐**
* 完全二叉树**从根节点至倒数第二层**，是一棵满二叉树
* 满二叉树一定是完全二叉树，完全二叉树不一定是满二叉树

![image-20221023185202484](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/8f54d60bd8114c8595f5124797087c54~tplv-k3u1fbpfcp-zoom-1.image)

* 度为 1 的节点只有左子树
* 度为 1 的节点最多有1个，要么没有

![image-20221023190641472](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3b749febc3fe4cf69f7b1be2461ca939~tplv-k3u1fbpfcp-zoom-1.image)



