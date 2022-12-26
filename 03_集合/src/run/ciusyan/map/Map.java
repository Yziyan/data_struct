package run.ciusyan.map;

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
