
# TreeSet的实现

## 一、初识Set集合

* 先来看一个很简单的问题
* 给你一组数据，如：`arr = [1, 3, 5, 5, 2, 6, 10, 20, 2, 6, 10, 20];`需要你将其中重复的元素去掉
* 让我猜猜你的第一反应：`将这一组数据添加到Set集合中`，哈哈，被我猜对了吧！
* 别着急反驳我，往下看看，是不是真的猜对了~

* 相比于其他集合，它最主要的特征：**不存放重复的元素**
* 就因为这一点，当有需求谈及去重时，大部分人应该都会想到`Set`
* 如果是Java中，`Set`集合，继承自`Collection接口`，拥有的接口和`List`的差不多
* 当谈到`Java的Set`，它的实现类主要有两个：`HashSet、TreeSet`（不谈中间抽取的类）
* 今天我们主要研究的是`TreeSet`，另一个之后再说~



## 二、Set的实现

### （1）接口定义

```java
public interface Set<E> {

    int size();
    boolean isEmpty();
    void clear();
    boolean contains(E element);
    void add(E element);
    void remove(E element);
    /**
     * 遍历
     * @param visitor：访问器
     */
    void traversal(Visitor<E> visitor);
    /**
     * 访问器
     */
    abstract class Visitor<E> {
        boolean stop;
        public abstract boolean visit(E element);
    }
}
```

* 都是一些很简单的接口，就不解释太多了
* 可以看到，`Set`的接口与`List`的有一个很大的区别：它没有`索引`，也就不能够根据索引获取对应的元素了。这也说明了`Set`是无序的
* 所以我们提供了一个用于遍历的方法，并且允许外界传入一个访问器。如上代码所示



### （2）使用List实现Set

* 哎哎哎，不是说要用红黑树来实现Set吗？怎么整到List去了！！！
* 别着急，就当做我们来复习一下之前的List集合了，一会你就知道了~
* 我这里采用双向链表来实现，因为它是List的最优实现嘛

```java
public class ListSet<E> implements Set<E> {
	
    // 组合双向链表
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
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    @Override
    public void add(E element) {
        int index = list.indexOf(element); // 查找元素所在链表的索引【-1代表不存在】
        if (index != list.ELEMENT_NOT_FOUND) { // 以前存在，覆盖原值
            list.set(index, element);
        } else { // 以前不存在，添加
            list.add(element);
        }
    }

    @Override
    public void remove(E element) {
        list.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        if (visitor == null) return;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (visitor.visit(list.get(i))) return;
        }
    }
}
```

* 看看上面的实现，其实很简单，毕竟**大部分逻辑直接使用原先List的即可**
* 需要修改的就是：
* ①：`add()添加方法`，因为要保证Set中的值是唯一的嘛
    * 在添加前，查找一下存在链表中的索引。若存在，用新值覆盖掉旧值。若不存在，才真正的添加

* ②：`traversal()遍历方法`，上面说了，因为没有索引，所以我们给外界提供一个遍历的方法
    * 去遍历内部的链表，但是注意终止的判断即可

* 如果需要查看链表内部的实现，可以看看之前的文章：[《动态数组、链表、栈、队列的总结》](https://juejin.cn/post/7159955445266776071)
* 具体的我们来看，如何用红黑树实现`TreeSet`~



### （3）使用红黑树实现Set

```java
public class TreeSet<E> implements Set<E> {

    // 组合红黑树
    private final RBTree<E> tree = new RBTree<>();

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public void clear() {
        tree.clear();
    }

    @Override
    public boolean contains(E element) {
        return tree.contains(element);
    }

    @Override
    public void add(E element) {
        tree.add(element);
    }

    @Override
    public void remove(E element) {
        tree.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        tree.inorder(new BinaryTreeImpl.Visitor<E>() {
            @Override
            protected boolean visit(E element) {
                // 因为访问器定义的位置不一样，需要这样使用
                return visitor.visit(element); 
            }
        });
    }
}
```

* 直接将之前实现的红黑树组合进来即可，基本上不需要做额外的处理
* 你可能会想，添加不需要像上面一样，先查询节点存不存在吗？
* 其实不需要，因为我们二叉搜索树的内部，遇到值相等的处理，本身就会覆盖，天生就具备了去重的能力
* 我们就来看看遍历方法：`traversal()`：
    * 其中因为Set的访问器和RbTree中的访问器没有定义在外面，所以需要这样中转一下来访问Set内部~
    * 这一点确实不难发现，可是二叉树的四种遍历方式，我为什么使用了中序遍历呢？
    * 这一点其实没有严格的标准，只是因为二叉树的中序遍历，会将节点变成有序的节点。这一点可能对外界比较有用。当然，你使用其他三种遍历方式也是可以的
* 实现完后，我们先来分析分析用这两种方式实现的`Set`



### （4）List、RbTree实现的Set的对比

#### ① 时间复杂度分析

##### List实现

* `contains()`查询：
    * 链表内部是去调用`indexOf(E element)`方法，此方法的时间复杂度为`O(n)`
    * 即`ListSet`的查询也是`O(n)`的时间复杂度
* `add()`添加：
    * 链表内部是去调用`add(E element)`方法，最终是去调用`add(int index, E element)`方法，因为最终都是添加在链表的尾部，所以时间复杂度为`O(1)`
    * 但是因为要保证元素是不重复的，在`ListSet`添加元素时，会先去查询元素是否存在，会调用`indexOf(E element)`方法
    * 即`ListSet`的添加方法是`O(n)`的时间复杂度
* `remove()`删除：
    * 链表内部是去调用`remove(E element)`，会先调用`indexOf(E element)`方法，最终去调用`remove(int index)`方法，此方法内部会调用`node(int index)`方法，此方法的平均时间复杂度是`O(n)`
    * 即`ListSet`的删除方法是`O(n)`的时间复杂度



##### RbTree实现

* 查询：就是去调用红黑树的查询方法，时间复杂度为`O(logn)`
* 添加：就是去调用红黑树的添加方法，时间复杂度为`O(logn)`
* 删除：就是去调用红黑树的删除方法，时间复杂度为`O(logn)`



#### ② 性能测试

* 看着这复杂度，好像不直观，我们来简单测试一下：
* 先贴测试代码：（保证最大生成的随机数在10000以内）

![image-20221124084845702](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/914311984017441c88c4d89b67ba07dd~tplv-k3u1fbpfcp-zoom-1.image)

* 看测试数据：（仅笔者电脑的测试）

|                       | 10000  | 100000 | 1000000 | 10000000 |
| :-------------------: | :----: | :----: | :-----: | :------: |
|  ListSet（链表实现）  | 0.195s | 2.672s | 26.008s | 223.454s |
| TreeSet（红黑树实现） | 0.023s | 0.072s | 0.424s  |  2.42s   |

* 测试截图：

![image-20221124085227472](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/fbae6dff183c4a6fb0a654fa9d01a454~tplv-k3u1fbpfcp-zoom-1.image)

* 虽然上面的测试较简单、也很随意，但是不妨碍我们直观的看出它们的运行速度嘛
* 下面我们再来看一种实现方式


### （5）使用映射实现Set

* 我们上面用红黑树很简单的实现了`Set`，也粗略的看到了红黑树的高效！
* 说到`Set`能**去重**的特性，不知道你能否想起映射`Map`
* `Map`的一个建`key`只会有一个值与之对应，换句话说，这个不就是**去重**吗？
* 是的，用`Map`来实现`Set`，也会很简单。那么我们下面就一起使用`TreeMap`来实现`TreeSet`吧

```java
public class TreeSet<E> implements Set<E> {

    private final Map<E, Object> map;

    public TreeSet() {
        this(null);
    }
    
    public TreeSet(Comparator<E> comparator) {
        map = new TreeMap<>(comparator);
    }
    
    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean contains(E element) {
        return map.containsKey(element);
    }

    @Override
    public void add(E element) {
        map.put(element, null);
    }

    @Override
    public void remove(E element) {
        map.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        map.traversal(new Map.Visitor<>() {
            @Override
            public boolean visit(E key, Object value) {
                return visitor.visit(key);
            }
        });
    }
}
```

* 你看，组合进来就行了，虽然`Map`需要两个泛型参数，但是我们根本不关心它，因为用不到它！
* 我们为什么还要用`TreeMap`来实现呢？`TreeMap`的底层不也是使用的红黑树吗？
* 是的，所以这种实现其实和上面的实现是差不多的，本质上都是组合了一棵红黑树
* 但是当你了解了这一点，还有人问你`TreeSet`的底层，你也可以自信的告诉他，我知道了，然后balabala...
* 其实`JDK`中的`TreeSet`大概就是这样实现的，而具体的操作，其实是在`TreeMap`中的
* 当然，如果想知道`TreeMap`是如何的，可以戳这篇文章：[《TreeMap的实现》](https://juejin.cn/post/7180656499859914810)






