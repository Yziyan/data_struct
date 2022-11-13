# 平衡二叉搜索树之AVL树

## 一、问题引入

* 上一篇文章，我们实现并且分析了二叉搜索树。但在分析复杂度时发现
* 二叉搜索树有可能退化成链表，树的高度和元素的个数相等：`h = n`
* 那我们如何使二叉搜索树，尽量保持平衡（`h ≈ logn`），就是我们今天要谈的内容：**平衡二叉搜索树之AVL树**



#### 平衡

* ❓既然想要在我们的树中添加平衡，那什么是平衡呢？
* **平衡**：当节点数量固定时，左右子树的高度越接近，这棵二叉树就越平衡，高度就越低

![image-20221031145738181](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0a83834c21c64075bd57cab2bd2aebaa~tplv-k3u1fbpfcp-zoom-1.image)

* 如上所示，节点的数量一模一样，但当左右子树的高度越接近时，构建出来的二叉搜索树就越平衡，最终树的高度也就越低
* 而高度最小的情况，是将二叉树的节点`从上至下、从左到右`，依次排满。也就是完全二叉树和满二叉树了~这样的平衡就被称为**理想平衡**
* ❓那如何改进，才能在树中增添平衡的功能呢？这又是一个值得思考的问题

#### 如何改进

* 上篇文章我们分析了，造成树不平衡的原因是：**添加、删除元素的顺序**导致的
* 而外界在使用二叉搜索树的时候，我们是没有办法限制添加、删除顺序的
* 所以我们只能**从添加、删除之后**想办法，将二叉树趋向平衡，使该树的高度减小

![image-20221113192910022](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d9e0974bcc9941f8947f7a904d166607~tplv-k3u1fbpfcp-zoom-1.image)

* 如上所示，调整后，树的性质没变，高度却减少了一层
* 如果继续趋向平衡，完全可以调整到理想平衡。但是**可能会付出更多的时间去调整**
* 如果调整的次数越多，反而可能会增加时间复杂度，还不如不调整
* 所以我们得有一个约定：**用尽量少的调整次数，达到合适的平衡度**
* 而达到**适度平衡**的二叉树，被称为：**平衡二叉搜索树**



#### 平衡二叉搜索树（Balanced Binary Search Tree）

* 简称为：`BBST`
* 常见的平衡二叉搜索树有
    * `AVL树`：（今天要谈的主角）
    * `红黑树`
* 一般也称它们为：自平衡二叉搜索树



## 二、AVL树

### （1）基本概念【略过】

* 维基百科中的定义

> **AVL树**（Adelson-Velsky and Landis Tree）是[计算机科学](https://zh.wikipedia.org/wiki/计算机科学)中最早被发明的[自平衡二叉查找树](https://zh.wikipedia.org/wiki/自平衡二叉查找树)。在AVL树中，任一节点对应的两棵子树的最大高度差为1，因此它也被称为**高度平衡树**。查找、插入和删除在平均和最坏情况下的[时间复杂度](https://zh.wikipedia.org/wiki/时间复杂度)都是`O(logn)`。增加和删除元素的操作则可能需要借由一次或多次[树旋转](https://zh.wikipedia.org/wiki/树旋转)，以实现树的重新平衡。AVL树得名于它的发明者[G. M. Adelson-Velsky](https://zh.wikipedia.org/wiki/格奥尔吉·阿杰尔松-韦利斯基)和[Evgenii Landis](https://zh.wikipedia.org/w/index.php?title=Evgenii_Landis&action=edit&redlink=1)，他们在1962年的论文《An algorithm for the organization of information》中公开了这一数据结构。

* 其中有几个关键的点，我们眼熟一下
    * 是最早发明的自平衡二叉搜索树
    * 任一节点对应的两棵子树的最大高度差为`1`
    * 查找、插入和删除在平均和最坏情况下的时间复杂度都是：`O(logn)`
    * 借由一次或多次树旋转，以实现树的重新平衡



#### 平衡因子（Balance Factor）

* 某节点**左右子树的高度差**
    * [下图红色的数为该节点的平衡因子]


![image-20221031155708921](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/ea434354c15b45a0a4cc6d49896cac61~tplv-k3u1fbpfcp-zoom-1.image)

* 而`AVL树`每个节点的平衡因子**只能是 1、0、-1** ，也就是`|平衡因子| ≤ 1`，不在这个范围内称为失衡

![image-20221031174110475](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/b2976ea47ed34ec284129a4779782362~tplv-k3u1fbpfcp-zoom-1.image)

* 换句话说：每个节点的左右子树的高度差不超过 `1`，节点的数量和树高度的关系就越接近：`h = logn`
* 所以AVL树的添加、删除、搜索的时间复杂度是 `O(h) = O(logn)`



### （2）失衡

#### 添加导致失衡

* 如下所示，一棵本身平衡的 `AVL树`，在添加完元素后，就可能导致失衡
* 而且**可能不仅仅是一个**节点失衡
* 最坏的情况就是：**所有的祖先节点都失衡了**，仅剩父节点和非祖先节点不会失衡

![image-20221031175244582](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2d2b5d5b685d409a8a188e03219981ec~tplv-k3u1fbpfcp-zoom-1.image)

* 添加了节点，导致`AVL`树失衡了，那我们该如何解决失衡的问题呢？



#### 解决失衡问题

* 再看一下定义中的这句话：**“借由一次或多次树旋转，以实现树的重新平衡”**
* 其实已经告诉了我们，如何使树恢复平衡 —— **一次或多次旋转**
* 先来看一个动画演示，来自维基百科

![img](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e0c25b5c0e3c416d9aabc45b94526113~tplv-k3u1fbpfcp-zoom-1.image)



> 此动画演示了不断将节点插入AVL树时的情况，并且演示了左旋（Left Rotation）、右旋（Right Rotation）、右左旋转（Right-Left Rotation）、左右旋转（Left-Right Rotation）以及带子树的右旋（Right Rotation with children）。



* 了解一下即可，那我们一起来详细总结一下该如何旋转吧~

* 如下抽象的树结构，使用中序遍历来访问树时，在平衡时的顺序。都是：`H1 -> C -> H2 -> B -> H3 -> A -> H4`
* 注意看图中红色的基准线。超过基准线，某些节点的平衡因子，就可能超出 `|平衡因子| ≤ 1`的范围，也就是会失衡



##### 1、在左子树的左子树添加元素（LL——单旋）

![image-20221101142158926](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/ec4abb1d055e4bcaa5a3ecf408fb9536~tplv-k3u1fbpfcp-zoom-1.image)

* 这是第一种情况，在某棵树的局部添加节点，并且是在树的**左子树的左子树**中添加节点，简称为左左、`LL`

![image-20221113200534889](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/cc31930ec6044a7da581ddd2b1989b18~tplv-k3u1fbpfcp-zoom-1.image)

* 如若失衡了，说明是**左边太重了**，那么将树**往右旋转**一下，即可恢复平衡



##### 2、在右子树的右子树添加元素（RR——单旋）

![image-20221103105903379](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/56529920c5aa449889dae2911b2ed541~tplv-k3u1fbpfcp-zoom-1.image)

* 这是第二种情况，在某棵树的局部添加节点，并且是在树的**右子树的右子树**中添加节点，简称为右右、`RR`

![image-20221113200649462](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4ce9667daf9b446298de205e138fd81e~tplv-k3u1fbpfcp-zoom-1.image)

* 如若失衡了，说明是**右边太重了**，那么将树**往左旋转**一下，即可恢复平衡



##### 3、 在左子树的右子树添加元素（LR——双旋）

![image-20221101142857713](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/7e73b689184d46cfa73a1b106e6999c0~tplv-k3u1fbpfcp-zoom-1.image)

* 这是第三种情况，在某棵树的局部添加节点，并且是在树的**左子树的右子树**中添加节点，简称为左右、`LR`

![image-20221113200736569](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/1c81e0faa0d843f5b4cf8149a4af271d~tplv-k3u1fbpfcp-zoom-1.image)

* 如若失衡了，说明是**中间的右边太重了**，那么将树**往左旋转**一次，变成左边太重了，**再往右旋转**一次，，即可恢复平衡



##### 4、在右子树的左子树添加元素（RL——双旋）

![image-20221101143025911](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4a444e8832594bbb8a92cdab8a7196f1~tplv-k3u1fbpfcp-zoom-1.image)

* 这是第四种情况，在某棵树的局部添加节点，并且是在树的**右子树的左子树**中添加节点，简称为右左、`RL`

![image-20221113200811795](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/5f4e2b83eebd458cbe42d9da23d0152b~tplv-k3u1fbpfcp-zoom-1.image)

* 如若失衡了，说明是**中间的左边太重了**，那么将树**往右旋转**一次，变成右边太重了，**再往左旋转**一次，，即可恢复平衡
* 介绍完添加后四种导致失衡的情况。那我们下面，就来实现一下在添加后的处理逻辑吧



### （3）添加之后的处理 —— afterAdd(Node<E> node)

#### ① 思路分析

* 在实现之前，我们必须要明确一个点：“**AVL树是在二叉搜索树的基础上加上了自平衡的功能**”
* 所以仅需要将AVL树`AVLTree`，继承自二叉搜索树`BST`即可
    * ❗注：二叉搜索树的分析与实现，在上一篇文章中。本篇文章`AVL树`的实现，是基于上一篇文章的


```java
public class AVLTree<E> extends BSTImpl<E> {
    public AVLTree() { this(null); }
    public AVLTree(Comparator<E> comparator) { super(comparator); }
}
```

* 通过上面的分析，你也可以知道，我们没法左右外界使用者的添加顺序
* 但是我们可以在他添加完之后，去看看需不需要修改节点的顺序

* 因为添加的逻辑是写在`BST`中的，所以我们可以在`BST`中，提供一个方法，给子类实现

![image-20221101160153440](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3ddeaecb316241cebc881a3dd188c486~tplv-k3u1fbpfcp-zoom-1.image)



* 那我们应该在`AVLTree`添加节点之后，做什么处理呢？
* 上面谈到，添加完节点之后可能并不会失衡，如下图所示

![image-20221101161133457](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3ae06bd49a85490189231fa891d4eb39~tplv-k3u1fbpfcp-zoom-1.image)

* 但是更多的情况，是我们刚刚谈的解决失衡的四种情况：`LL、RR、LR、RL`

![image-20221101161808217](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/acfcbe5814c4405aa91b11fb24936033~tplv-k3u1fbpfcp-zoom-1.image)

* 因为这些情况，导致添加完后，二叉搜索树失衡了，那我们是不是需要去找到失衡的节点，把他修复呢？
* 是的，找到失衡的节点，将它修复即可。可是我们刚刚也说了哎，最坏的情况是它所有的祖先节点都失衡了
* ❓难到我们要一一修复吗？如果不是，那修复谁呢？
* 出现了一个失衡节点，可能会导致一系列的节点失衡
* 我们仅仅需要在失衡节点中，找到**离添加节点最近**的祖先节点，将其修复，那么再上面的所有祖先节点都随之一起修复了

```java
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null) { // 从父节点不断的往上寻找失衡的祖先节点
            if (/*node 是否平衡 */) {
                // 未失衡的逻辑 ...
            } else {
                // 失衡后的处理 ...
            }
        }
        // 退出循环说明解决完失衡了 【也可能没有失衡】
    }
```

* ❓那如何判断某一节点是否失衡呢？我们得先来探讨一下这个问题



#### ② 判断某一节点是否失衡

* 再回看一下定义：**任一节点对应的两棵子树的最大高度差为`1`**，也就是`|平衡因子| ≤ 1`的情况
* 所以，我们可以根据节点左右子树的高度差，来判断是否失衡了。在实现之前，我们先来改造一下节点类

* 之前的节点是**二叉树通用**的节点。里面并没有高度这一属性，更别谈平衡因子了

![image-20221101165359412](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/54d39e14ffad4fe48244fff6767ec12f~tplv-k3u1fbpfcp-zoom-1.image)



>节点的**平衡因子**是它的左子树的高度减去它的右子树的高度（有时相反）。带有平衡因子1、0或 -1的节点被认为是平衡的。带有平衡因子 -2或2的节点被认为是不平衡的，并需要重新平衡这个树。**平衡因子可以直接存储在每个节点中**，或从可能存储在节点中的**子树高度计算出来**。

* 我们想要求树的平衡因子，是不是只需要有节点的高度即可，当然，你也可以维护一个平衡因子的属性
* 因为别的二叉树，可能不需要高度属性，所以不能将高度的属性写在通用的二叉树节点中，可能会浪费内存
* 那只能再单独写一个节点类`AVLNode`，就可以了
* 可是这样的话，如下图，在二叉搜索树中添加节点的时候，又会有问题了

![image-20221101170000707](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/580d67b63059475f9689a67f6584cf2c~tplv-k3u1fbpfcp-zoom-1.image)

* 所以，我们这里还得改造一下`add()`这个模板方法，同刚刚的`afterAdd()`思路一致

![image-20221101170813693](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/030f55f112b2443794c3577ce7ee15f8~tplv-k3u1fbpfcp-zoom-1.image)

* 那么在我们的`AVLTree`中，就可以重写此方法了

```java
protected Node<E> createNode(E element, Node<E> parent) {
    return new AVLNode<>(element, parent); // 在 AVL树中，使用的是 AVL的节点，里面额外维护了一个高度的属性
}
```

* 改造完之后，我们就能计算某一节点的平衡因子了，进而就可以判断节点是否失衡了

```java
    /**
     * AVL树的节点类
     */
    private static class AVLNode<E> extends Node<E> {
        /**
         * 树的高度
         */
        int height;
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
```

* 如上的代码，其实很简单，但是有一个问题，创建AVL节点的时候，默认高度是0。此后，我们都没有更新过它的高度，所以，我们还需要维护节点的高度，进而就维护了AVL树的性质



#### ③ 更新节点的高度

* 更新高度是为了在判断是否平衡时不出错，那如何更新呢？
* 我们添加一个节点时，按理来说，节点的默认高度就是 `1`，因为他是叶子节点嘛
* 所以我们可以做第一个改造：`height = 1`
* 改造完之后，我们回想一下，以前实现二叉搜索树的时候，写了一个求节点高度的递归函数
* 其中的思路是：`自己的高度 + Math.max(左子树高度, 右子树高度)`，那我们这里还需要去写递归函数吗？
* 其实不然，现在`AVLTree`的每一个节点，里面都有`height`属性，那么我们用同样的思路，不用写递归函数，是不是也可以求出这个节点的高度

```java
        public void updateHeight() {
            int LH = left != null ? ((AVLNode<E>)left).height : 0; // 左子树的高度
            int RH = right != null ? ((AVLNode<E>)right).height : 0; // 右子树的高度
            height = 1 + Math.max(LH, RH); // 自己的高度 + 左右子树高度的最大值
        }
```

* 可是还有一个问题，在哪里去执行这个函数呢？

![image-20221101201532842](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/c7787a7c71964614a34e62fe626c4d46~tplv-k3u1fbpfcp-zoom-1.image)

* 没错，直接在查找失衡节点的时候去更新高度即可。为什么呢？
* 你能来到这个函数，说明肯定是在平衡的`AVL`树添加了一个节点
* 那么顺着它的父节点去查询它的祖先节点，带上它的父节点，依次遍历的时候`node.parent.parent....parent`，就只有两种情况，失衡和平衡
    * 如果是平衡的节点，那么它的高度肯定会发生变化，因为它的下面多了一个子孙节点，也就需要更新高度
    * 如果是失衡的节点，那么我们需要将它**先恢复为平衡**的节点，**再去更新它的高度**
* 最终遍历完成。每一个节点的高度肯定都更新了。**要么是恢复平衡再更新高度，要么就是节点没有失衡，直接更新高度**



#### ④ 恢复平衡

* 通过上面的分析，可以得出，未失衡的节点，直接更新高度即可。失衡的节点，得先将其恢复平衡，再更新高度。那我们得去做恢复平衡的逻辑
* 定义中说：通过一次或多次旋转去恢复平衡。具体如何旋转呢？
* 也就是上面的四种情况：`LL、RR、LR、RL`，通过左旋、右旋即可解决
* ❓那又遇到了一个问题，我们该如何判断，是这四种情况的哪一种呢？
* 我们已经通过遍历，找到了最低的失衡节点，我们通过它，可以找到它的**子节点**
* 再通过子节点，就可以找到它的**孙子节点**
* 找到了对应的节点，再分别查看，它们在失衡节点的左边还是右边，即可知道是哪一种情况了



##### 查找子节点和孙子节点

* ❓可是失衡节点可能有左子树，也有右子树，都是它的子节点，我们该找谁呢？
* 找孙子节点的时候，也是一样的道理

![image-20221103090118304](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4c6b06a551a649cba98a4165bbab7989~tplv-k3u1fbpfcp-zoom-1.image)

* 从图中可以发现，我们要找的子节点，是它**左右子树中，高的那个**，孙子节点当然也是同理了~

```java
        public Node<E> tallerChild() {
            int LH = left != null ? ((AVLNode<E>)left).height : 0; // 左子树的高度
            int RH = right != null ? ((AVLNode<E>)right).height : 0; // 右子树的高度
            if (LH > RH) return left; // 左边较高，返回左子树
            if (LH < RH) return right; // 右边较高，返回右子树
            // 左右高度一样，返回与父节点同方向的节点【其实这时返回左右都可以，但是返回同侧，会更省事些】
            return isLeftChild() ? left : right;
        }
```

* 找到失衡节点的子节点和孙子节点后，就可以判断是：`左左、右右、左右、右左`中的哪种情况了

![image-20221103095642411](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/48a62a12723a450b916a02e78899815b~tplv-k3u1fbpfcp-zoom-1.image)



* 我们根据失衡的节点，就可以找到他的子节点、孙子节点
* 根据子、孙节点，就可以判断出，是位于父节点的左边还是右边，进而就知道是：`LL、RR、RL、LR`的哪种情况了
* 旋转的核心秘诀就是：**重的一侧向轻的一侧旋转**
* 根据核心秘诀，就能确定，需要左旋、还是右旋了
* ❓那最后一个问题就是，该如何左旋、右旋呢？



#### ⑤ 左旋、右旋

![image-20221103105958113](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/5e70cb53cab947dc9b448aa539b1bf1e~tplv-k3u1fbpfcp-zoom-1.image)

* 如图所示，我们需要将待旋转节点旋转到下面，需要将它的子节点旋转到上面
* 如果是左旋，那么需要跟他交换层级的子节点肯定在右边

```java
/**
     * 左旋转
     * @param node：待旋转节点
     */
    private void rotateLeft(Node<E> node) {
        Node<E> child = node.right; // 取出子节点 【左旋，节点肯定在右边】
        Node<E> grandChild = child.left; // 取出孙子节点
        node.right = grandChild; // 将上面的节点往下旋转【将待旋转的子节点指向它的孙子节点】
        child.left = node; // 将下面的节点向上旋转【将子节点的子节点变成待旋转节点】

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

        // 更新待旋转节点的父节点
        node.parent = child;

        // 更新节点的高度 【先更新矮的、后更新高的】
        updateHeight(node);
        updateHeight(child);
    }
```

* 如上代码，我们通过旋转交换层级后，还需要将它们的父节点更新
* 父节点更新完成之后，还需要将旋转后的节点，高度更新一下
* ❗注意，需要先更新低的，因为我们更新某一节点，是通过该节点的左右子树高度的最大值 + 自己的高度

* 左旋搞定了，**右旋**也是一样的道理

```java
    /**
     * 右旋转
     * @param node：待旋转节点
     */
    private void rotateRight(Node<E> node) {
        Node<E> child = node.left; // 取出子节点【右旋：节点肯定在左边】
        Node<E> grandChild = child.right; // 取出孙子节点
        node.left = grandChild; // 将上面的节点往下旋转【将待旋转的子节点指向它的孙子节点】
        child.right = node; // 将下面的节点向上旋转【将子节点的子节点变成待旋转节点】

        // 旋转后的操作
        afterRotate(node, child, grandChild);
    }
```

* 不论是左旋还是右旋，在旋转完成后，都需要额外维护一些内容，而且是相同的逻辑。所以我们可以将其抽出来

![image-20221103111902705](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/bd15f09c5b07439cbc5ba7db72bed5f4~tplv-k3u1fbpfcp-zoom-1.image)



* 旋转逻辑完成了，说明恢复平衡就没问题了

* 那我们再来思考一个问题。四种导致节点失衡的情况，都用了不同的旋转方式，来使其重新恢复平衡
* ❓能不能将旋转逻辑统一呢？也就是四种失衡的方式：`LL、RR、RL、LR`都使用一样的逻辑来使其恢复平衡



#### ⑥ 统一旋转逻辑【进阶补充】

* 我们先来看一幅图

![image-20221103141922357](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/af761bc0c58640fd8b0d5ccd30aaf11f~tplv-k3u1fbpfcp-zoom-1.image)

* `AVL树`是在二叉搜索树的基础上增加了自平衡的功能，那它肯定也满足：`左子树 < 根节点 < 右子树`
* 也就是图中所示的：`A -> B -> C -> D -> E -> F -> G`
* 不论是失衡前、还是平衡后，顺序都应该不变（大小不会变），要不然就不是一颗二叉搜索树了
* 我们将重点放在恢复平衡后的节点构造。四种失衡方式，最终都变成了
    * D失衡时处于树的**正中间**。恢复平衡后变成了**树的根节点**，还是在中间
    * 原先比 **D小** 的节点，平衡后都用 **B** 作为根节点，作为D的**左**子树
    * 原先比 **D大** 的节点，平衡后都用 **F**  作为根节点，作为D的**右**子树
* ❗注：这里的节点可能不是一颗完整的树，也可能是一棵树的局部，反正，D最后变成了这棵树的根节点

```java
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

    /**
     * 统一旋转逻辑
     */
    private void rotate(Node<E> R, // 待旋转节点的根节点
                        Node<E> A, Node<E> B, Node<E> C, // 左子树
                        Node<E> D, // 最终的根节点
                        Node<E> E, Node<E> F, Node<E> G // 右子树
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
        
        // 更新树的高度【也要先更新矮的】
        updateHeight(B); // 更新左子树B 的高度
        updateHeight(F); // 更新右子树F 的高度
        updateHeight(D); // 更新 D 的高度
    }
```

* 重点看如何传参进入`rotate()方法`
    * 根据四种失衡方式，中序遍历的顺序传入进去的
    * 不信你对照着图看看~



### （4）删除之后的处理 —— afterRemove(Node<E> node)

#### 思路分析

* 上面分析了添加导致失衡，并且通过旋转的方式，解决了失衡的问题
* 可是删除节点后，会不会导致失衡呢？如果有，又该如何解决呢？

![image-20221103174652413](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/da7a43e31623472ba1640acba788e641~tplv-k3u1fbpfcp-zoom-1.image)

* 我们发现，在删除节点后，**也可能会导致节点失衡**
* 但是**最多有一个节点失衡**，要么是父节点，要么是祖先节点
* 我这里为什么仅仅说是只会有一个节点失衡呢？其他节点难到不会失衡吗？
* 其实这一点很容易想清楚。当删除节点后，失衡了
    * 肯定只能是该节点较矮的那边被删除了，这个时候该失衡的节点**整体高度是不变**的
    * 那么在往上查找，也就不会有第二个失衡的节点了
* 如果删除某一节点后，它父节点的高度减少了，那这种情况删除的肯定是本身就较高的节点，本身就不会失衡
* 所以，我们需要找到它，并且**通过一次或多次旋转，将其恢复平衡**即可

![image-20221103181500344](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/ca37c95594b74dacb72f23fa51256285~tplv-k3u1fbpfcp-zoom-1.image)

* 如上图，我们通过旋转，将其恢复平衡了，其他节点也是平衡的
* 嗯？难到恢复平衡了，还会有其他节点不平衡的情况？

![image-20221103181902017](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/a5f8234dab964b69af0d642b44a7e0f3~tplv-k3u1fbpfcp-zoom-1.image)

* 如上图，我们修复失衡节点后，发现局部高度减少了1，导致原失衡节点的父节点也失衡了
* 那我们还得再次修复

![image-20221103182451657](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/f435e3a4d01843efa3700651bbdacfd7~tplv-k3u1fbpfcp-zoom-1.image)

* 终于，它又平衡了~
* 那下面，我来总结一下删除后，节点失衡时，恢复平衡的思路
    * 根据被删除的节点，从它的父节点开始，遍历查找失衡的节点
    * 如果有失衡的节点，通过旋转**恢复平衡**
    * 修复之后，**继续遍历查找**，还有没有失衡节点【修复完之后，可能会出现新的失衡节点】
    * 同添加后的处理类似，如果发现节点是未失衡的，需要去更新它的高度【有节点被删除了，高度可能会变化】
* 具体的代码实现，同添加之后的处理类似
* 我们的删除方法，是写在二叉搜索树中的，那我们在那里提供一个给子类实现的模板角色方法`afterRemove()`，给`AVL树`实现

```java
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) { // 未失衡
                updateHeight(node);
            } else {
                rebalanced(node); // 恢复平衡后，还需继续遍历
            }
        }
    }
```

* 是不是和添加后的处理很像？连如何恢复平衡的逻辑都一样
* ❗❗❗但是有一个注意点：
    * 添加可能会导致**一系列**的节点失衡，我们仅仅需要修复最低的失衡节点，即可恢复平衡
    * 而删除**仅会有一个**节点失衡，但是**修复之后**，**可能会导致一系列**的节点失衡
* 所以我们修复完一个失衡节点后，还需要继续遍历，确保再也没有失衡的节点了

* 额外说明：在二叉搜索树中，去哪里调用删除后的逻辑

![image-20221103190112886](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/80d029264c4149ac9895adfffe46d75b~tplv-k3u1fbpfcp-zoom-1.image)



* 必须确保在删除后，再去调用`afterRemove()`方法
* 真正删除节点的逻辑，在二叉搜索树中，我们当时是分别去删除：度为 0、1、2的节点
* 而度为 2 ，我们也将其转换为了0、1统一删除，那我们就分别在真实删除节点之后，去调用此方法
* 当然，在`AVL树`中，也可以放在删除后，统一调用~
