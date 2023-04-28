package run.ciusyan.Top;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/lru-cache/
 * LinkedHashMap实现
 */
public class LRUCache1 {

    /**
     * 用于记录最多能够缓存的容量
     */
    private int capacity;

    /**
     * 用于存储所有的节点
     */
    private Map<Integer, Integer> cache;

    public LRUCache1(int capacity) {
        // 这里改用LinkedHashMap实现，
        // 这个HashMap的内部，维护了一个双向链表，保证了遍历时的插入顺序
        cache = new LinkedHashMap<>(capacity);
        this.capacity = capacity;
    }

    public int get(int key) {
        // 将此 key 删除，会返回对应的 value
        Integer v = cache.remove(key);
        if (v == null) return -1;

        // 插入到最后（最新的最近使用）
        cache.put(key, v);

        return v;
    }

    public void put(int key, int value) {
        Integer v = cache.remove(key);

        if (v == null) {
            // 到这里，说明是要添加新的缓存，检查容量还够不够
            if (cache.size() == capacity) {
                // 说明容量满了，需要淘汰最被加入
                // 通过遍历，最前面的就是最先添加的

                // cache.keySet().iterator().next() 这个就是最开头的那个
                cache.remove(cache.keySet().iterator().next());
            }
        }

        // 那么，插入最新的
        // 将其新值，插入到最后即可
        cache.put(key, value);
    }

}
