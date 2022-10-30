
# 线性表


* [**文章地址**](https://juejin.cn/post/7159955445266776071)

## 一、线性结构

### （1）线性表

* 线性表是具有 n 个**相同类型**元素的有限序列 (n >= 0)
* 常见的线性表
    * 数组、链表、栈、队列、哈希表（散列表）
* 生活中也有很多线性表

![排队图片_排队素材_排队高清图片_摄图网图片下载](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0d91c4b9bd124e89831e86adbccee963~tplv-k3u1fbpfcp-zoom-1.image)

* 笔者在此篇文章会学习`数组、链表、栈、队列`，`哈希表`还没有学习

## 二、动态数组（DynamicArray）



### （1）数组（Array）

* 数组是一种顺序存储的线性表，所有元素的内存地址是连续的

![image-20221012201459587](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/a2f6d4a75a7148f0ad178c4b1f036606~tplv-k3u1fbpfcp-zoom-1.image)

```java
int [] array = new int[] {1, 2, 3};
```

* 数组有一个很大的缺点。**无法动态修改数组的容量**
* 在创建的时候，有多少个元素，就已经定死了
* 而我们平时很多场景，一开始可能都不知道需要多少申请多大的内存来存放数据
* 在这个背景下，出现了**动态数组**
* 那么我们下面，一起实现一个动态数组吧~
* 不用犹豫，我们只是为了学习常见的数据结构，并不是要造一个没有那么好的轮子


### （2）动态数组的设计



![image-20221011215043660](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e52edec9f6804dabb8dca652f300dffd~tplv-k3u1fbpfcp-zoom-1.image)

* 动态数组里，至少应该要维护两个成员变量
    * 一个是数组元素的数量
    * 另一则是需要维护的一个数组

```java
public class ArrayList<E> {

    /**
     * 数组的元素个数
     */
    private int size;
    /**
     * 所有的元素
     */
    private E[] elements;
	
    // 默认容量
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayList() { this(DEFAULT_CAPACITY); }

    public ArrayList(int capacity) {
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        elements = (E[]) new Object[capacity];
    }
}
```

* 我这里设置一个默认容量为 `10`。当然，在使用此动态数组时，也可以传入一个容量



### （3）接口设计

```java

    /**
     * 获取数组元素的数量
     * @return ：元素数量
     */
    int size();

    /**
     * 数组是否为空
     * @return ：是否为 null
     */
    boolean isEmpty();

    /**
     * 查看某元素是否在数组中
     * @param element：查看元素
     * @return ：是否存在
     */
    boolean contains(E element);

    /**
     * 在数组的末尾添加元素
     * @param element：添加的元素
     */
    void add(E element);

    /**
     * 根据索引获取数组元素
     * @param index：索引
     * @return ：对应元素
     */
    E get(int index);

    /**
     * 修改特定的位置的元素
     * @param index：待替换的索引
     * @param element：待替换的元素
     * @return ：以前的元素
     */
    E set(int index, E element);

    /**
     * 在特定位置添加新元素
     * @param index：待添加位置
     * @param element：待添加的元素
     */
    void add(int index, E element);

    /**
     * 删除某一位置的元素
     * @param index：带删除元素位置
     * @return ：被删除的元素
     */
    E remove(int index);

    /**
     * 删除某一元素
     * @param element：待删除的元素
     * @return ：被删除的元素
     */
    E remove(E element);

    /**
     * 查看某元素的所在的所索引
     * @param element：查找的元素
     * @return ：对应索引
     */
    int indexOf(E element);

    /**
     * 清空数组元素
     */
    void clear();

```

* 都是增删改查的接口，都很常用，就不多解释了



### （4）简单接口的实现

#### ① `size()`

```java
    public int size() {
        return size;
    }
```

* 直接返回`成员变量size`即可

#### ② `isEmpty()`

```java
   public boolean isEmpty() {
        return size == 0;
    }
```

* 如果数组元素个数为0，代表这个数组为空

#### ③ `get()`

```java
   public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
        }
        return elements[index];
    }
```

* 从我们内部维护的数组中，取出索引为 index 的元素即可
* 但是在取元素之前，我们需要**判断索引是否越界**

#### ④ `set()`

```java
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
        }
        E oldElement = elements[index];
        elements[index] = element;
        return oldElement;
    }
```

* 这个就是一个简单的修改方法，但是我们也需要判断索引是否越界
* 而且这种判断，在数组里是常用的，所以这种重复的代码，我们可以将其抽出



#### 判断索引是否越界

```java
    /**
     * 判断索引是否越界
     * @param index：索引
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throwBoundMsg(index);
        }
    }

    /**
     * 抛出数组越界的异常
     */
    private void throwBoundMsg(int index) {
        throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
    }
```



#### ⑤ `indexOf()`

```java
	public int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }
```

* 数组里的某个元素，可能存放`null`，那么我们得做`null`的判断
* 如果是查找的元素是 `null` ，那么我们返回第一个为空的索引
* 不为空的时候，这里有两个小细节
    * 使用 `equals()`方法，而没有直接使用 `==` （题外话：equals 与 == 的区别？）
    * 上面判断过：`element != null`了，将待查找的 element 在前，就不会有**空指针异常**了



#### ⑥ `clear()`

```java
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }
```

* 数组的每一个元素，将其指向 `null`，回收其内存
* 数组的内存可以不回收，因为之后可能还会使用



### （5）添加元素——add(E element）

```java
// 下面一起实现
 add(size, element);
```



![image-20221012205701976](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3ae9c09a59eb48c4b0e29f6459f50b91~tplv-k3u1fbpfcp-zoom-1.image)



* 我们需要往数组的最后添加元素，那么只需要找到末尾元素的后一元素索引即可
* 很容易发现规律，`待添加位置的索引 = size`
* 并且在添加完成之后，需要将 `size + 1`
* 我们等会和另一个添加元素的方法`add(int index, E element)`一起实现
* 也就是在最后一个位置添加元素，所以我们共用一个实现即可~



### （6）删除元素——remove(int index)

* 实现之前，我们一起来想想实现的思路
* 有的同学可能会想，这还不简单吗？如下图所示
    * 要删除谁，把谁抠掉不就完了吗？
    * 将后面的元素拼接到被删除的元素之前的位置

![image-20221012211647988](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0b6068be4104497e93f9cba894f6a2da~tplv-k3u1fbpfcp-zoom-1.image)

* 思路确实很清晰，可是现实是，我们没办法这样管理内存，我们开头也说了
* 数组的内存是连续的，一开始申请多少空间，那么这一段空间是**连续且固定**的
* 动态数组的内部维护的也是一个数组，所以，我们来看看正确的思路

```java
   public E remove(int index) {
        checkIndex(index); // 判断索引是否越界
        
        // 先保存要删除的元素
        E oldEle = elements[index];
        
        // 将待删除元素后面的元素，依次往前挪动
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        // 将最后挪动完成后的元素清空
        elements[--size] = null;
        return oldEle;
    }
```

![image-20221012220555615](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d215ea8f68554ee28823b10f0fcd1158~tplv-k3u1fbpfcp-zoom-1.image)

* 如上图所示，正确的思路应该是：将待删元素之后的元素，依次挪动到前面
* 很显然，我们这里需要挪动3次，也就是从待删除的索引 2 到 5 的位置
* 通用一点也就是 从 `index 到 size -1` 的位置
* 你也看到了，在挪动操作完成后，我们最后一个元素还存在。你可能会想，不需要处理这一个位置的元素吗？
* 是的，我们还可以做一个操作， 将挪动完成之后的最后一个元素，清空掉
* 即`size--; elements[size] == null;`



### （7）添加元素——add(int index, E element)

```JAVA
    public void add(int index, E element) {
        checkIndexForAdd(index); // 检查索引
        // 确保需要多少容量，不够就去扩容 【在下面解释】
        ensureCapacity(size + 1);
        // 将待添加元素后面的元素依次挪动到后面，将待添加元素位置空出来
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        // 空出来后就可以添加元素了~
        elements[index] = element;
        size++;
    }
```

* 我们这个方法，是想要在某一位置添加一个元素
* 如果你理解了上面删除的思路，那么，你看看这个方法的实现，是不是用了同样的思路呢~

![image-20221017183832417](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/7e1ab3f3a3f04d0c9a38292210ab1a92~tplv-k3u1fbpfcp-zoom-1.image)

* 但是要注意❗
    * 与删除不同的地方，这个要**先挪动大**的【否则到时候待添加位置之后元素的值都是同一个了】
    * 还有判断索引的边界，这里是可以等于索引的，因为我们可以往最后一个位置添加元素
* 所以我们另外写了一个判断边界是否越界的方法



#### 判断索引是否越界

```java
    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) { // 可以等于数组的size
            throwBoundMsg(index);
        }
    }
```



### （8）如何扩容

* 既然我们是一个动态数组。那么当容量不够时，应该要去扩充数组的容量
* 也就是要解决几个问题
    * 何时、何地 扩容
    * 扩充多少容量
    * 怎么扩容
* 那我们先实现一下，再继续分析

```java
private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length; // 旧容量
        if (oldCapacity >= capacity) return; // 如果旧数组容量比所需的最小容量还大，就不需要扩容
        // 扩充容量为原来的 1.5 倍 【 >> （右移运算符是 ÷ 2）】
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity]; // 申请更大内存的一段空间
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i]; // 将旧数组的元素拷贝到新数组
        }
        elements = newElements; // 让内部维护的数组指向新数组
    }
```

* 这就是我们的扩容方法，原理其实很简单，我来画一幅图你就知道了

![image-20221017191629760](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/5cb0c4dd660945c9ac6b5a49d49751f8~tplv-k3u1fbpfcp-zoom-1.image)



* 因为申请的堆空间，地址是**随机且连续**的，我们没法申请一段新空间，拼接数组尾部
* 只能申请一段**更大的空间**，将旧数组的元素挪动到新申请的空间
* 至于何时何地去扩容的话，也不难想清楚
    * 就是在添加元素地方去扩容
    * 而且是当旧容量小于最小容量`size + 1`时，才需要去扩容



### （9）缩容——trim()

* 说完了扩容，我们顺便谈谈缩容
* 至于为什么要缩容？肯定是剩余容量太多了嘛，想省点未利用的空间。也是一样的几个问题
    * 何时、何地缩容
    * 容量缩减为多少
    * 如何缩容

```java
    private void trim() {
        int oldCapacity = elements.length; // 旧数组容量
        int newCapacity = oldCapacity >> 1; // 缩减为旧容量的一半
        if (newCapacity <= size || oldCapacity <= DEFAULT_CAPACITY) return;
        E[] newElements = (E[]) new Object[newCapacity]; // 更小的一段空间
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i]; // 将旧数组拷贝到新数组
        }
        elements = newElements;
    }
```

* 和扩容一样，也是新申请一段内存空间、将旧数组的元素拷贝到新的数组里即可
* 我们这里是当元素数量小于数组容量的一半的时候，才进行缩容操作【当然，不能比默认容量还小，要不然下次使用的时候扩容也比较耗时】
* 缩容操作应该是放在 `size`减少的地方，也就是 `clear() 和 remove()`方法需要缩容操作
* 如何缩减，在上面了，就不多说了~



## 三、链表（Linked List）

### （1）问题引入

* 我们上面说完了动态数组，你应该能感觉到，动态数组也有一些不太好的地方
    * 当扩容的时候，会有大量的元素拷贝
    * 拷贝完成后，新的空间，**可能有很多都未使用**
    * 也就是说，**动态数组可能会造成空间的大量浪费**
* 那我们能不能用多少，就申请多少内存空间呢？
    * 这个时候，我们就引入了链表，因为它刚好可以这样做【❗注：并不是取代动态数组】



### （2）基本构造

* **链表是一种链式存储的线性表，所有元素的内存地址不一定是连续的**
* 和动态数组差不多，最大的区别在于：数组的内存空间是连续的，需要一次性申请
* 而链表的**内存空间不一定是连续**的，可以用到时再申请

![image-20221018205457431](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/991dbe1ade264d3884d4731c414e9072~tplv-k3u1fbpfcp-zoom-1.image)



### （3）链表的设计

* 链表里应该也有元素的个数 `size`
* 还应该有一个指向头节点 `Node`的引用
* 并且内部还有一个**节点类**。里面装着具体的元素`element`和指向下一个节点对象的引用

![image-20221018205251891](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/8d321a2ba97944b896600a3853742a66~tplv-k3u1fbpfcp-zoom-1.image)

```java
public class LinkedList<E> {
    /**
     * 链表元素个数
     */
    private int size;
    /**
     * 头结点
     */
    private Node<E> first;

    /**
     * 结点类【外部不可见】
     */
    private static class Node<E> {
        /**
         * 具体的元素
         */
        E element;
        /**
         * 下一个节点
         */
        Node<E> next;
        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

    }
}
```



### （4）接口设计

* 其实链表中，有很多对外界提供的接口是一样的，只是有些实现不一样，所以我们可以共用一个接口
* 而且里面有一些代码是可以和动态数组共用的，那么我们来改造一下代码结构

![image-20221018203218035](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4ed8ea5f7a5c444eb5eb4daeac06cd41~tplv-k3u1fbpfcp-zoom-1.image)



* 我们将中间加一层抽象类，让链表和动态数组继承，这里面可以放一些公共的代码
* 然后让其抽象父类`AbstractList`实现 `List`接口



### （5）清空链表——clear()

```java
public void clear() {
    size = 0;
    first = null;
}
```

* 很简单，只需要将 `size = 0`
* 并且将头结点的引用指向空即可，`first = null`



### （6）添加元素——add(int index, E element)

* 我们在某一索引位置添加元素，为了方便找到原来的节点，可以找到索引位置的**前一个节点**`prev`【前驱节点】
* 将待添加的节点的**下一个节点**`next`【后继节点】指向`prev.next`
* 然后再将**前一个节点**的`next`指向新添加的节点

![image-20221018210939860](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4b34848586a6417a80a3ce0d44bae86e~tplv-k3u1fbpfcp-zoom-1.image)

* 那如果往第一个位置添加元素呢？
* 就不能获取前一个节点了，所以，我们还的对边界做特殊处理

![image-20221018212748591](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/581ca6f79d244e6da5cafb8d1ee3561c~tplv-k3u1fbpfcp-zoom-1.image)

* 也是一样的思路，只不过就是找不到前一个节点了
* 但是我们天然就有一个 `first`，是指向原先的第一个节点的
* 所以思路是：将新节点的下一个节点指向`first`所指向的节点
* 再将`first`指向新插入的节点即可

```java
    public void add(int index, E element) {
        checkIndexForAdd(index); // 坚持是否索引是否越界
        if (index == 0) { // 往头结点处添加元素
            first = new Node<>(element, first);
        } else {
            // 拿到索引位置的前一个节点 【node(index)方法在下面说】
            Node<E> prev = node(index - 1);
            prev.next = new Node<>(element, prev.next);
        }
        size++; // 别忘了数量要++哟 
    }
```

> 一般对链表的操作，如果index 的等于 0 、size、size - 1，这几个位置没有问题，那么中间部分一般不会出现问题

* 如上面所言，我们如果往尾结点添加元素，是否可以呢？
* 如果你这个疑问，那么你可以自己画图，然后用线连一下看看
* 你会发现，我们所写的代码，也是可以满足其需求的



#### 根据索引获取节点对象——node(index)

* 很多地方，可能都需要通过索引来获取节点对象
* 那么我们单独提供一个私有的方法【因为节点对外部不可见】

```java
    private Node<E> node(int index) {
        checkIndex(index); // 检查索引
        Node<E> node = first; // 从头节点依次往下寻找
        for (int i = 0; i < index; i++) { // 寻找 index 次即可
            node = node.next; 
        }
        return node;
    }
```

![image-20221018213544104](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/a9f26d919908488594ea9c481901ed47~tplv-k3u1fbpfcp-zoom-1.image)

* 只能拿着自己的头结点`first`，从前往后遍历取`next`
* 但是遍历几次呢？从图中可以看出来，想要获取的索引是多少。那么就遍历几次
* 写完了这个方法，我们有好几个方法，就都能实现了

### （7）获取元素——get(int index)

```java
public E get(int index) {
    return node(index).element; // 获取index处节点中的元素
}
```

### （8）修改元素——set(int index, E element)

```java
public E set(int index, E element) {
    Node<E> node = node(index); // 未修改前的节点
    E old = node.element;
    node.element = element; // 覆盖原先的值即可
    return old;
}
```

### （9）删除元素——remove(int index)

```java
    public E remove(int index) {
        checkIndex(index); // 检查索引
        Node<E> node = first;
        if (index == 0) { // 删除头节点
            first = first.next; // 将头结点指向原先头结点的后继节点
        } else {
            Node<E> prev = node(index - 1); // 获取待删除位置的前驱节点
            node = prev.next; // 取出原先的节点
            prev.next = node.next; // 将前驱节点的后继节点换成指向待删除节点的后继节点
        }
        size--;
        return node.element;
    }
```

* 如果你了解了上面添加元素时的思路，那么删除元素应该难不倒你
* 如果还不清楚，那我们再来看幅图：
    * 思路也是先找到待删除元素的**前驱节点** `prev`
    * 让前驱节点的`next`，指向待删除节点的`next`

![image-20221018220125572](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e9b2620339ba47cf8c4ea14d868b4c2f~tplv-k3u1fbpfcp-zoom-1.image)



### （10）有虚拟头节点的链表

* 在上面的代码中，你可能发现了，链表的**添加、删除**方法
* 都对`index == 0`（对头结点的操作）都进行了特殊处理，那我们能不能做到，所有位置，都是一样的处理呢？
* 这时候，我们就在链表中引入了**虚拟头结点**的概念
    * 有时候为了让代码更加精简
    * 统一所有节点的处理逻辑
    * 可以在最前面增加一个不存储数据的**虚拟头结点**

![image-20221020105151408](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d026ac17ed5640b98ec758039a10a3bb~tplv-k3u1fbpfcp-zoom-1.image)

#### ① 改造构造函数

```java
/**
 * 增加一个虚拟头结点
 */
public LinkedListVNode() {
    first = new Node<>(null, null); // 不需要存储元素
}
```

* 在创建对象时，就默认添加一个不存储数据的虚拟结点，并且让头结点 `first`指向它

#### ② 改造node()方法

```java
private Node<E> node(int index) {
    checkIndex(index);
    Node<E> node = first.next; // 从真实头结点开始遍历
    for (int i = 0; i < index; i++) {
        no de = node.next;
    }
    return node;
}
```

* 与上面的链表一模一样的处理方式，只不过是从头结点`first`的后继节点`next`开始遍历

#### ③ 改造remove()、add()方法

```java
public E remove(int index) {
    checkIndex(index); // 检查索引
    Node<E> prev = (index == 0) ? first : node(index - 1); // 看看是不是在头结点操作
    Node<E> node = prev.next;
    prev.next = node.next;
    size--;
    return node.element;
}

public void add(int index, E element) {
    checkIndexForAdd(index);
    // 拿到索引位置的前一个节点【没有就拿头结点】
    Node<E> prev = (index == 0) ? first : node(index - 1);
    prev.next = new Node<>(element, prev.next);
    size++;
}
```

* 也是同之前一样的处理方式，只不过现在不需要对 `index == 0`单独处理了



### （11）双向链表

#### ① 问题引入

* 我们上面所学习的链表，都是单向链表，我们去分析单向链表的复杂度会发现
* 增删改查的平均复杂度都是 `O(n)`，动态数组的查询和修改复杂度都是 `O(1)`
* 那我们还需要使用链表吗？毋庸置疑，当然需要【况且Java官方的链表，也是双向链表~】
    * 首先，使用链表不会浪费很多空间
    * 其次，我们还可以改进链表，使其变成双向链表。**以提升链表的综合性能**



#### ② 基本构造

```java
public class DoubleLinkedList<E> {
    /**
     * 头结点
     */
    private Node<E> first;
    /**
     * 尾结点
     */
    private Node<E> last;

    /**
     * 内部的节点类
     */
    private static class Node<E> {
        /**
         * 具体的元素
         */
        E element;
        /**
         * 前驱节点
         */
        Node<E> prev;
        /**
         * 后继节点
         */
        Node<E> next;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}
```

* 如下图所示
    * 链表内部还应该维护一个尾结点`last`
    * 每一个节点内部也应该还有一个前驱节点`prev`

![image-20221019220358847](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2627c90704604affad0659d6455384f3~tplv-k3u1fbpfcp-zoom-1.image)

#### ③ 优化查找节点——node(int index)

```java
    private Node<E> node(int index) {
        checkIndex(index);
        Node<E> node;
        // 看看索引是否超过链表长度的一半
        if (index < (size >> 1)) { 
            // index < (size / 2) 从前往后找
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            // 从后往前找
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }
```

* 以前只能从头结点`first`依次**往后**查找节点
* 现在内部有尾结点`last`了，就可以**从后往前**找节点了
* 这样一改造，我们最多只需要遍历`size / 2`次，即可找到对应索引的节点



#### ④ 修改清空元素——clear()

```java
    public void clear() {
        size = 0;
        first = null;
        last = null;
    }
```

* 没错，在`java`中就是这么简单，`last`和`first`引用一样，指向`null`即可



#### ⑤ 优化添加元素——add(int inxex, E element)

* 我们回想一下，单向链表的添加操作。需要拿到添加位置的前驱节点 `prev = node(index - 1)`，是为了方便拿到添加前的原节点
* 而现在，拿到当前节点即可。因为当前节点里，就会有前驱节点的引用

```java
public void add(int index, E element) {
    checkIndexForAdd(index);

    if (index == size) { // index == size【往最后面添加元素】
        Node<E> oldLast = last; // 原先的尾结点
        last = new Node<>(oldLast, element, null); // 将新构建的节点赋值给尾结点
      
        if (oldLast == null) { // index == size == 0【第一次添加元素】
            first = last; // 头尾都指向同一个节点
        } else {
            oldLast.next = last;
        }
    } else { // 往任意位置
        Node<E> next = node(index); // 拿到添加前的节点【也就是新添加节点的后继节点】
        Node<E> prev = next.prev; // 拿到它的前驱节点
        Node<E> node = new Node<>(prev, element, next); // 构建新的节点
        next.prev = node;

        if (prev == null) {  // index == 0【往最前面添加元素】
            first = node; // 将头结点指向新的节点即可
        } else {
            prev.next = node;
        }
    }
    size++;
}
```

* 上面是完整实现，下面我会将其拆分成往几个位置添加节点，跟着我的思路，你也可以学会😊~

##### 1、往链表中间任意位置添加

![image-20221020093856701](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/53e9c2f411ca4023850253b4726bc99b~tplv-k3u1fbpfcp-zoom-1.image)

* 我们想要将33添加到`index == 2`的位置
* 思路如下、图所示
    * 取出原先`index == 2`的节点`next`，作为待添加节点的后继节点
    * 根据上面的`next`节点取出`prev`节点，作为待添加节点的前驱节点
    * 用`next、prev`节点构建待添加的节点`node`
    * 构建完成后，待添加节点需要连接的线就接上了
    * 最后只需要将`prev`的后继节点指向`node`
    * 将`next`的前驱节点指向`node`即可

![image-20221020094526821](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/ec2d0e019c4640878c65f29bd2bc45de~tplv-k3u1fbpfcp-zoom-1.image)

```java
Node<E> next = node(index); 
Node<E> prev = next.prev;
Node<E> node = new Node<>(prev, element, next);
next.prev = node;
prev.next = node;
size++;
```

* 代码如上，不难发现，为什么要分情况讨论
* 比如说：我们想要在第一个位置添加元素，那么根据节点`next`取出来的节点`prev`是为`null`的
* 那么下面在用`null`去指向节点`node`，肯定会出异常嘛
* 那我们再看看，如何添加元素到`index == 0`的位置

##### 2、往链表头部添加

![image-20221020100131426](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/8a2098c4ba384687abf7ee95ef73a2ad~tplv-k3u1fbpfcp-zoom-1.image)

* 我们想要将11添加到`index == 0`的位置
* 思路如下、图所示
    * 取出原先`index == 0`的节点`next`，作为待添加节点的后继节点
    * 根据上面的`next`节点取出`prev`节点，**值为`null`**，作为待添加节点的前驱节点
    * 用`next、null`节点构建待添加的节点`node`
    * 构建完成后，待添加节点需要连接的线就接上了
    * `prev == null`，说明是往第一个位置添加，将**头结点**`first`指向`node`
    * 最后将`next`的前驱节点指向`node`即可

![image-20221020100552655](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4a099a035e3f42f8a5206cca37bcc49e~tplv-k3u1fbpfcp-zoom-1.image)

```java
Node<E> next = node(index);
Node<E> prev = next.prev;
Node<E> node = new Node<>(prev, element, next);
next.prev = node;

if (prev == null) {  // index == 0【往最前面添加元素】
    first = node;
} else {
    prev.next = node;
}
size++;
```

* 代码如上，这时：往中间、最前面添加元素，都解决了
* 那么，我们再来讨论一下，剩下两种情况

##### 3、往链表尾部添加

![image-20221020102524099](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/efaaac8260ec4d67b69d631397dbc758~tplv-k3u1fbpfcp-zoom-1.image)

* 我们想要将55添加到`index == size`的位置
* 思路如下、图所示
    * 取出原先`index == 4`的节点`oldNode`，作为待添加节点的前驱节点
    * 用`oldNode、null`节点构建待添加的节点`node`
    * 构建完成后，待添加节点需要连接的线就接上了
    * 将**尾结点**`last`指向`node`
    * 最后将`oldNode`的后继节点指向`node`即可

![image-20221020102911905](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/c32e08f4159a41de8e4950beb153ab4b~tplv-k3u1fbpfcp-zoom-1.image)

```java
if (index == size) { // index == size【往最后面添加元素】
    Node<E> oldLast = last;
    last = new Node<>(oldLast, element, null);
    oldLast.next = last;
} else {
    // 上面一样的代码...
}
size++;
```

* 代码如上，这时：从尾部添加元素，也可以解决了
* 那么，我们最后讨论一种最特殊的情况



##### 4、第一次添加

![image-20221020104305325](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3b209ae3269f47bc86ca70fdaee34642~tplv-k3u1fbpfcp-zoom-1.image)

* 我们想要将11添加到`index == size == 0`的位置
* 思路如下、图所示
    * 第一次添加元素时，链表的**头尾**节点都是指向`null`的
    * 取出原先`index == 0`的节点`oldNode`，**值为`null`**，作为待添加节点的前驱节点
    * 用`null、null`节点构建待添加的节点`node`
    * 构建完成后，待添加节点的**前驱和后继节点**都指向了`null`
    * 将**尾结点**`last`指向`node`
    * 将**头节点**`first`，也指向`node`
    * 最后发现，链表里只有一个元素时，链表的头尾节点都指向这唯一的节点

![image-20221020104809071](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/15c3553da162465588a720d1b208199e~tplv-k3u1fbpfcp-zoom-1.image)

```java
if (index == size) { // index == size【往最后面添加元素】
    Node<E> oldLast = last;
    last = new Node<>(oldLast, element, null);

    if (oldLast == null) { // index == size == 0【第一次添加元素】
        first = last;
    } else {
        oldLast.next = last;
    }
} else {
    // 上面一样的代码...
}
size++;
```



#### ⑥ 优化删除元素——remove(int index)

```java
public E remove(int index) {
    checkIndex(index);
    Node<E> node = node(index); // 取出删除前的节点 A
    Node<E> prev = node.prev; // 拿到A的前驱节点
    Node<E> next = node.next; // 拿到A的后继节点
    
    if (prev == null) { // index == 0【删除第一个元素】
        first = next; // 将头结点指向A的后继节点
    } else {
        prev.next = next; // 将A的前驱节点的后继节点指向A的后继节点
    }
    
    if (next == null) { // index == size - 1【删除最后一个元素】
        last = prev; // 将尾结点指向A的前驱节点
    } else {
        next.prev = prev; // 将A的后继节点的前驱节点指向A的前驱节点
    }
    size--;
    return node.element;
}
```

* 刚刚详细的讲解过了`add()`方法，删除方法也是一样的道理
* 拿到待删除的节点`node`
* 根据`node`获取前驱节点`prev`，后继节点`next`
* 将节点`prev`的后继节点指向`next`
* 将节点`next`的前驱节点指向`prev`
* 当然，别忘了`prev == null`【删除第一个节点】和`next == null`【删除最后一个节点】的情况
* 删除完成后，将`size - 1`即可，我这里就不去画图解释了，可以参照添加节点的思路~自己画图试试



### （12）单向循环链表

#### ①基本构造

* 在单向链表`LinkedList`的基础上，将其最后一个节点的后继节点`next`，指向头结点即可
* 那我们来改造一下单向链表`LinkedList`的几个方法，应该就可以了

![image-20221020133814652](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/55d8f49c46b84b59ac3699ea0938b8f8~tplv-k3u1fbpfcp-zoom-1.image)



#### ② 改造添加元素——add(int index, E element)

```java
    public void add(int index, E element) {
        checkIndexForAdd(index);
        if (index == 0) { // 往头节点添加元素
            Node<E> newFirst = new Node<>(element, first); // 构建新的节点
            Node<E> last = (size == 0) ? newFirst :node(size - 1); // 获取尾结点
            first = newFirst; // 改变头结点指向【必须再上面获取尾节点操作之后进行】
            last.next = first; // 将尾结点的后继节点指向头结点
        } else {
            // 拿到索引位置的前一个节点
            Node<E> prev = node(index - 1);
            prev.next = new Node<>(element, prev.next);
        }
        size++;
    }
```

* 相比于单向链表，就是在往第一个位置添加元素的时候，需要将最后一个节点`last`的后继节点`next`
* 指向添加后新的头结点`first`
* **注意**：要先获取以前的尾结点`last`，再改变头结点的指向
* 思路很简单，我就画一个，第一次添加元素时的图

![image-20221020140010781](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/37e11358528c4667aa37fd117685c05e~tplv-k3u1fbpfcp-zoom-1.image)



#### ③ 改造删除元素——remove(int index)

```java
    public E remove(int index) {
        checkIndex(index);
        Node<E> node = first;
        if (index == 0) { // 删除第一个节点
            if (size == 1) { // 只有一个节点的情况
                first = null;
            } else {
                Node<E> last = node(size - 1); // 获取尾结点
                first = first.next; // 将头结点指向原头结点的后继节点
                last.next = first; // 将尾结点的后继节点指向新的头节点
            }
        } else { // 删除中间、尾部 【操作不需要改变】
            Node<E> prev = node(index - 1);
            node = prev.next;
            prev.next = node.next;
        }
        size--;
        return node.element;
    }
```

* 相比于单向链表，就是在往第一个位置删除元素的时候，需要将最后一个节点`last`的后继节点`next`
* 指向删除后的头结点`first`即可
* **注意**：要先获取以前的尾结点`last`，再改变头结点的指向
* 但是也有特殊情况，当链表中，只有一个节点的时候
* 仅改变引用，不做特殊处理的话，会删不掉那一个节点
* 但是当只有一个节点，我们还想要删除该节点，是不是直接将头结点`first`指向`null`就行啦~



### （13）双向循环链表

#### ① 基本构造

![image-20221020143732817](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/1822acd809c5411a8d5d1b28808916bd~tplv-k3u1fbpfcp-zoom-1.image)

* 同上面的单向循环链表一样，我们仅需要将双向链表`DoubleLinkedList`改造一下即可
    * 将尾结点`last`的后继节点指向头结点`first`
    * 将头结点`first`的前驱节点指向尾结点`last`



#### ② 改造添加元素——add(int index, E element)

```java
public void add(int index, E element) {
    checkIndexForAdd(index);

    if (index == size) { // index == size【往最后面添加元素】
        Node<E> oldLast = last;
        last = new Node<>(oldLast, element, first); // 改造①

        if (oldLast == null) { // index == size == 0【第一次添加元素】
            first = last;
            last.next = last; // 改造②
            first.prev = first; // 改造③
        } else {
            oldLast.next = last;
            first.prev = last; // 改造④
        }
    } else {
        Node<E> next = node(index);
        Node<E> prev = next.prev;
        Node<E> node = new Node<>(prev, element, next);
        next.prev = node;
        prev.next = node; // 改造⑤
        
        // 改造⑥
        if (index == 0) {  // index == 0【往最前面添加元素】
            first = node;
        }
        
    }
    size++;
}
```

* 大体思路和上面的双向链表一致，但是里面做了几处改造
* 改造①：现在尾结点`last`的后继节点，将其改为头结点`first`
* 改造② ③：若是第一次添加元素，还要将添加节点的**后继、前驱节点**都指向自己

![image-20221020150821009](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/6b4474d45916488380a22f53a00e28f8~tplv-k3u1fbpfcp-zoom-1.image)

* 改造④：将头结点`first`的前驱节点，指向尾结点`last`
* 改造⑤：现在不存在头结点`first`的前驱节点为`null`的情况了，所以可以拿出来，让其必须执行
* 改造⑥：现在`prev`不可能为`null`，即判断是否是往最前面添加元素，就得用其他方法判断了

#### ③ 改造删除元素——remove(int index)

```java
    public E remove(int index) {
        checkIndex(index);
        Node<E> node = first; // 改造①
        if (size == 1) { // 改造②
            first = null;
            last = null;
        } else {
            node = node(index);
            Node<E> prev = node.prev;
            Node<E> next = node.next;
            prev.next = next; // 改造③
            next.prev = prev; // 改造④

            // 改造⑤
            if (node == first) { // index == 0【删除第一个元素】
                first = next;
            }

            // 改造⑥
            if (node == last) { // index == size - 1【删除最后一个元素】
                last = prev;
            }
        }
        size--;
        return node.element;
    }
```

* 改造①：该方法最终需要返回被删除节点的元素，所以，将其拿出来，默认指向头结点`first`
* 改造②：和上面单向循环链表类似，如果仅有一个元素，仅仅是改变指向，但是节点删不掉，所以需要做特殊处理
* 改造 ③ ④：现在`prev`和`next`都不存在为`null`的情形，所以将其拿出来必须执行
* 改造 ⑤ ⑥：同上，判断是否是删除头尾节点，就不能用prev、next是否为null了



## 四、栈（Stack）

* 栈是一种只能在**单端**操作的一种特殊的线性表

### （1）基本构造

* 往栈中添加元素的操作一般叫入栈
* 往栈中移除元素的操作一般叫出栈。而且只能移除栈顶元素
* 遵循后进先出的原则

![image-20221020190203027](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/fffd5cb701a54cce87b25096a9e769b6~tplv-k3u1fbpfcp-zoom-1.image)

### （2）接口设计

```java
public interface Stack<E> {
    /**
     * 获取栈中元素的数量
     * @return ：元素数量
     */
    int size();

    /**
     * 栈是否为空
     * @return ：是否为 null
     */
    boolean isEmpty();

    /**
     * 入栈
     */
    void push(E element);

    /**
     * 出栈
     */
    E pop();

    /**
     * 获取栈顶元素
     */
    E top();

    /**
     * 清空栈的所有元素
     */
    void clear();
}
```

### （3）具体实现

* 我们刚写完了动态数组和链表，刚好可以利用它们来实现栈的方法
* 而且不管是出栈、入栈，都是在栈顶操作，也就是在线性表的尾部操作
* 那么用动态数组和用双向链表来实现，都是差不多的。我们这里就使用动态数组`ArrayList`来实现了

```java
public class StackArrayImpl<E> implements Stack<E>{

    private final List<E> list = new ArrayList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void push(E element) {
        list.add(element);
    }

    @Override
    public E pop() {
        return list.remove(list.size() - 1);
    }

    @Override
    public E top() {
        return list.get(list.size() - 1);
    }
    
    @Override
    public void clear() {
        list.clear();
    }
}
```

* 没错，就是这么简单，已经写完了~
* 我们这里没有直接继承，因为会引入`List`中其他的方法
* 而是使用组合的设计



### （4）栈的应用

* 浏览器的前进和后退

![image-20221020193708787](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/89adc5d534364cff9a5df97fbb55b9e2~tplv-k3u1fbpfcp-zoom-1.image)

* 还有很多类似的应用，比如步骤的撤销和重做...



## 五、队列（Queue）

* 队列是一种只能在**头尾两端**操作的特殊的一种线性表
* 就和我们日常生活中的排队差不多【当然不算插队的情况~🤷‍♂️】



### （1）基本构造

* 只能从**队尾rear**添加元素，一般叫做入队`enQueue`
* 只能从**队头front**删除元素，一般叫做出队`deQueue`
* 遵循**先进先出**的原则

![image-20221021084724549](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/f99f17c5ca79499d83b174d52c61e764~tplv-k3u1fbpfcp-zoom-1.image)



### （2）接口设计

```java
public interface Queue<E> {
    /**
     * 获取队列中元素的数量
     * @return ：元素数量
     */
    int size();

    /**
     * 队列是否为空
     * @return ：是否为 null
     */
    boolean isEmpty();

    /**
     * 入队
     */
    void enQueue(E element);

    /**
     * 出队
     */
    E deQueue();

    /**
     * 获取队头元素
     */
    E front();

    /**
     * 清空队列的所有元素
     */
    void clear();
}
```



### （3）具体实现

* 同栈的思路一样，我们完全可以复用之前写的链表和动态数组来实现队列的功能
* 并且我们一直是在头尾两端进行操作，那我们应该优先`组合`双向链表`DoubleLinkedList`

```java
public class QueueLinkedImpl<E> implements Queue<E> {

    private final List<E> list = new DoubleLinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void enQueue(E element) {
        list.add(element);
    }

    @Override
    public E deQueue() {
        return list.remove(0);
    }

    @Override
    public E front() {
        return list.get(0);
    }

    @Override
    public void clear() {
        list.clear();
    }
}
```

* 是的，已经实现完成了
* 由栈和队列的实现可见，我们的动态数组和链表有多么重要了吧
* 你也就知道我前面为什么要花那么多的时间来谈动态数组和链表了吧~



### （4）用栈实现队列

* 我们刚刚用链表实现了栈，那我们思考一下，可否使用栈，来实现队列呢？



```java
public class QueueStackImpl<E> implements Queue<E> {

    /**
     * 仅用于 入队
     */
    private final Stack<E> inStack = new StackArrayImpl<>();
    /**
     * 用于 出队、获取队头
     */
    private final Stack<E> outStack = new StackArrayImpl<>();

    @Override
    public int size() {
        return inStack.size() + outStack.size();
    }

    @Override
    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    @Override
    public void enQueue(E element) {
        inStack.push(element);
    }

    @Override
    public E deQueue() {
        checkOutStack();
        return outStack.pop();
    }

    @Override
    public E front() {
        checkOutStack();
        return outStack.top();
    }

    @Override
    public void clear() {
        inStack.clear();
        outStack.clear();
    }
    
    /**
     * 检查是否需要将 inStack中的元素弹到 outStack中
     */
    private void checkOutStack() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
    }
}
```

* 我这里用的也是我们自己实现的栈，思路不难
* 准备两个栈
    * 一个用于入栈`inStack`：仅用于**入队**操作
    * 一个用于出栈`outStack`：用于**出队、获取队头**操作
* `enQueue`入队时，直接将元素添加到`inStack`中
* `deQueue`出队时，将`outStack`中的栈顶元素弹出
    * 查看`outStack`中是否为空
    * 若为空，将`inStack`中的所有元素弹出到`outStack`中后，再弹出`outStack`的栈顶元素
    * 若不为空，直接弹出

![image-20221022150757596](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2fc9d14d7032456687fe39ba856eec67~tplv-k3u1fbpfcp-zoom-1.image)



### （5）双端队列（Deque）

* 就是在普通队列的基础上，增加了可以从尾部操作元素

#### ① 新增接口

```java
public interface DeQueue<E> extends Queue<E> {

    /**
     * 从队头入队
     * @param element：待入队的元素
     */
    void enQueueFront(E element);

    /**
     * 从队尾出队
     * @return ：出队的元素
     */
    E deQueueRear();

    /**
     * 从队尾获取元素
     */
    E rear();

}
```

#### ② 实现

```java
    @Override
    public void enQueueFront(E element) {
        list.add(0, element);
    }

    @Override
    public E deQueueRear() {
        return list.remove(list.size() - 1);
    }

    @Override
    public E rear() {
        return list.get(list.size() - 1);
    }
```

* 就贴新增的接口的实现，其余的再上面有，就不重复贴了



### （6）循环队列（Circle Queue）

#### ① 基本构造

* 循环队列的底层是用数组来实现的：队列的底层也可以使用动态数组来实现，这个用数组实现并且优化后的队列叫做**循环队列**

![image-20221022161056443](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/b2d65b97f49f487d83d25b20611c5a32~tplv-k3u1fbpfcp-zoom-1.image)

* 如上图操作所示
    * 循环队列里还得有一个队头索引`front`
    * 循环队列的关键点是：出队时，不挪动元素，仅仅改变队头的索引

```java
public class CircleQueue<E> implements Queue<E> {

    /**
     * 指向队头的索引
     */
    private int front;
    /**
     * 内部维护的数组
     */
    private E[] elements;
	/**
	 * 元素个数
	 */
    private int size;

    private static final int DEFAULT_CAPACITY = 10;
    public CircleQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }
}
```



#### ② 入队——enQueue(E element)

* 既然我们内部使用的是数组，那么我们想要入队，也就是从数组的尾部添加元素，那是不是直接`elements[size] = element;`呢？
* 那我们来看这种情况

![image-20221022163427512](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/5ad85ea1eae44c4b86a745faa02a1f4f~tplv-k3u1fbpfcp-zoom-1.image)

* 现在的队头索引`front = 1, size = 3` ，我们想要将 77 入队，按上面的思路，也就是`elements[3] = 77`，是不是就不太合理了？
* 是不是还需要加上队头索引呢：`elements[size + front] = element`
* 放上面的例子来看：`elements[3 + 1] = 77`，好像确实可以了哎~
* 那这样呢？

![image-20221022163945058](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/1f13764a5dfb4016932db9bacce62204~tplv-k3u1fbpfcp-zoom-1.image)

* 现在的队头索引`front = 1, size = 4` ，我们想要将 88 入队，按上面的思路，也就是`elements[4 + 1] = 88`，是不是也不太合理？
* 所以，我们来看看最终的思路
    * 用 `front + size` 再模上**数组长度**

```java
    public void enQueue(E element) {
        elements[(front + size) % elements.length] = element;
        size++;
    }
```



#### ③ 出队——deQueue()

* 与上面的入队思路很类似
* 一开始，清空原来的队头元素后，`front++`即可，但队头索引在最后时，就需要模上数组长度了

```java
    public E deQueue() {
        E oldElement = elements[front]; // 取出原先的队头元素
        elements[front] = null; // 将其指向null
        front = (front + 1) % elements.length; // 将队头索引 front + 1 （但必须是真实的索引）
        size--;
        return oldElement;
    }
```



#### ④ 动态扩容

* 与我们之前动态数组的扩容很相似
* 但是之前动态数组元素挪动时，新数组和**旧循环数组**的索引是相同的
* 在这里，旧的**循环数组**的索引，得从队头开始挪动，也就是要找到元素在数组中**真实的位置**

```java
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(front + i) % oldCapacity]; // 从队头元素开始挪动元素到新数组
        }
        elements = newElements;
        front = 0;
    }
```



#### ⑤ 获取循环数组的真实索引——index(int index)

* 刚刚写的`入队、出队、扩容`，还有`clear()方法`。都需要找到元素**在循环数组里真实的索引**
* 方便解释，我们引入两个概念
    * 物理索引：数组构造时的索引，如`arr = [11, 22, 33]`中用物理索引获取 `22`，也就是`arr[1]`
    * 真实索引：循环数组中元素的真实索引，如`front = 1, arr = [11, 22, 33]`中用真实索引获取`22`，也就是`arr[2]`

* 如果我们是循环数组，都需要将索引处理一下：`真实索引 = (物理索引 + front) % capacity`，那么我们可以将其封装一下

```java
    private int index(int index) {
        return (index + front) % elements.length;
    }
```



### （7）循环双端队列

#### ① 新增的方法

```java
public class CircleDeQue<E> implements DeQue<E> {

    private int front;
    private E[] elements;
    
    @Override
    public void enQueueFront(E element) {
        ensureCapacity(size + 1);
        front = index(-1);
        elements[front] = element;
        size++;
    }

    @Override
    public E deQueueRear() {
        int rearIndex = index(size - 1);
        E oldElement = elements[rearIndex];
        elements[rearIndex] = null;
        size--;
        return oldElement;
    }

    @Override
    public E rear() {
        return elements[index(size - 1)];
    }
}
```

* 同上面的双端链表一样，新增了几个方法`队尾获取、队头入队、队尾出队`
    * 加入了往队尾入队的方法，那你可能会想，我们是否需要添加一个指向队尾的索引
    * 你看看实现，其实不需要，有了队头索引`front`，找队尾只需要将`front + size -1`即可
* 与普通的双端链表比起来，这里需要将数组的物理索引，映射成循环数组里元素真正的索引



#### ② 改造映射物理索引为真实索引

```java
    private int index(int index) {
        index += front;
        int capacity = elements.length;
        return (index < 0) ? index + capacity : index % capacity;
    }
```



* 我们想要从队头入队，也就是从`真实索引 = 0`的前面插入元素
* 按上面统一的实现，也就是往`真实索引 = -1`处添加元素
* 按封装索引的思路：`真实索引 = (front + 物理索引) % capacity`来看，在大多数位置，都是可以的
* 可是当`真实索引 = 0`的时候，如下图
    * 按照之前的处理：`真实索引 = (0 - 1) % 5 = -1`，那往 `真实索引 = -1`的位置插入元素，就得做处理了

![image-20221023092047444](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/908dc436d7da4874a694ee2ca739b6bf~tplv-k3u1fbpfcp-zoom-1.image)

* 我们观察发现，当想要访问的`物理索引`为负数的时候
    * 我们仅仅需要：`真实索引 = 物理索引 + 数组容量`即可

#### ③ 优化取模运算

* 改造完了循环双端队列后，我们还可以将取模运算优化一下【计算机处理 `乘 除 取模 浮点数运算`效率比较低】

```java
    private int index(int index) {
        index += front;
        int capacity = elements.length;
        // 优化取模运算，仅适用于【capacity ∈ (0, 2 * index]】
        return (index < capacity) ? index : index - capacity;
    }
```

* 我们这里的操作，也就是优化：`n % m，且当 m ∈ (0, 2n)时`
    * 当 `n < m`时，取模的结果直接是 `n`
    * 当 `n >= m`时，取模的结果是 `n - m`

