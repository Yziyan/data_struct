package run.ciusyan.skiplist;

import java.util.Comparator;

/**
 * 跳表
 */
public class SkipList<K, V> {
    // 跳表元素数量
    private int size;

    // 比较器
    private Comparator<K> comparator;

    // 最大层数
    private static final int MAX_LEVEL = 2 << 4;

    // 增加层数的概率
    private static final double P = 0.25;

    /**
     * 用一个不存储任何 K, V 的虚拟头节点
     */
    private Node<K, V> first;

    /**
     * 现在跳表中最多有几层
     */
    private int level;


    public SkipList() {
        this(null);
    }

    public SkipList(Comparator<K> comparator) {
        this.comparator = comparator;
        first = new Node<>(null, null, MAX_LEVEL);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 查询
     */
    public V get(K key) {
        keyCheck(key);

        // 现在搜索到的节点
        Node<K, V> curNode = first;
        // 从最高一层开始搜索（跨度最大的一层）
        for (int i = level - 1; i >= 0 ; i--) {

            int cmp = -1;
            // 比较 key，与此层 next 节点的key
            //      如果 key > 节点的key，说明此层还可以尝试往下走
            // 但是要防止走到此层的终点，如果终点都还未找到，说明此层可能找不到，也需要跳到下一层搜索
            while (curNode.nexts[i] != null && (cmp = compare(key, curNode.nexts[i].key)) > 0) {
                // 来到这里，说明还可以往后走
                curNode = curNode.nexts[i];
            }

            // 说明找到了此节点。
            if (cmp == 0) return curNode.nexts[i].value;

            // 如果上面都不满足，说明会到下一层进行搜索，i - 1
        }

        return null;
    }

    /**
     * 添加
     * @return 有可能是覆盖，覆盖就返回覆盖前的值
     */
    public V put(K key, V value) {
        keyCheck(key);

        // 找到添加元素的所有前驱节点，最多有level个
        //      前驱节点如何找呢？
        //      其实每一次只要向下查找，那么需要查找的那个元素，
        //      必然比向下的元素大，比向下的后一个元素小
        Node<K, V>[] prevs = new Node[level];
        Node<K, V> curNode = first;

        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1;
            while (curNode.nexts[i] != null && (cmp = compare(key, curNode.nexts[i].key)) > 0) {
                curNode = curNode.nexts[i]; // 说明可以继续向后查找
            }

            // 说明以前存在此元素
            if (cmp == 0) {
                V oldV = curNode.nexts[i].value;
                curNode.nexts[i].key = key;
                curNode.nexts[i].value = value;
                return oldV;
            }

            // 来到这里，说明要向下查找了，说明现在的 curNode，就是第 i 个前驱节点
            prevs[i] = curNode;
        }

        // 来到这里说明，需要新添加节点，并且维护前驱后继的关系

        size++;
        // 新建前驱节点
        int newLevel = randomLevel();
        Node<K, V> newNode = new Node<>(key, value, newLevel);

        // 维护节点关系
        for (int i = 0; i < newLevel; i++) {
            // 如果新层数，比旧的有效层数还大，
            if (i >= level) {
                // 说明新节点高出来的层，需要将前驱设置为虚拟头节点
                first.nexts[i] = newNode;
            } else {
                // 维护前驱和后继
                 newNode.nexts[i] = prevs[i].nexts[i];
                 prevs[i].nexts[i] = newNode;
            }
        }

        // 增加后，有效层数可能增加
        level = Math.max(level, newLevel);

        return null;
    }

    /**
     * 删除
     */
    public V remove(K key) {
        keyCheck(key);

        // 前驱节点
        Node<K, V>[] prevs = new Node[level];
        Node<K, V> curNode = first;

        // 需要查找删除节点是否存在
        boolean exist = false;

        for (int i = level - 1; i >= 0 ; i--) {
            int cmp = -1;
            while (curNode.nexts[i] != null && (cmp = compare(key, curNode.nexts[i].key)) > 0) {
                // 说明还可往后查找
                curNode = curNode.nexts[i];
            }

            // 说明存在删除的节点
            if (cmp == 0) exist = true;

            // 来到这里说明需要往下查找
            prevs[i] = curNode;
        }

        if (!exist) return null;

        // 来到这里，说明存在被删除节点，
        size--;
        // 而且肯定是现在 curNode 最底层，的后继节点
        Node<K, V> removedNode = curNode.nexts[0];

        // 维护关系
        for (int i = 0; i < removedNode.nexts.length; i++) {
            // 将前驱的后继变为删除元素的后继
            prevs[i].nexts[i] = removedNode.nexts[i];
        }

        // 删除后，跳表的有效层数可能变低，如果变低了，那就将虚拟头节点高出来的层数的后继清空
        int newLevel = level;
        // 从有效层数挨个向下查看，
        while (--newLevel > 0 && first.nexts[newLevel] == null) {
            // 来到这里，说明之前这一层是删除的节点，但是现在被删除了，
            // 需要将有效层数 - 1
            level--;
        }

        return removedNode.value;
    }

    /**
     * 随机生成新节点的层数
     */
    private int randomLevel() {
        int newLevel = 1;

        // 随机层数，但是不能超过最高层数
        while (Math.random() < P && newLevel < MAX_LEVEL) {
            newLevel++;
        }

        return newLevel;
    }

    /**
     * 比较 K1 和 K2 的大小
     */
    private int compare(K k1, K k2) {
        return comparator != null ? comparator.compare(k1, k2) : ((Comparable<K>)k1).compareTo(k2);
    }

    private void keyCheck(K k) {
        if (k == null) {
            throw new IllegalArgumentException("Key 不能为 null");
        }
    }

    private static class Node<K, V> {
        K key;
        V value;

        // 后继节点数组，可能有很多层
        Node<K, V>[] nexts;

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            this.nexts = new Node[level];
        }
    }
}
