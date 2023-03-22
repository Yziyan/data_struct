package run.ciusyan.trie;

public interface Trie<V> {
    int size();
    boolean isEmpty();
    void clear();
    V get(String key);
    boolean contains(String str);
    V add(String str, V value);
    V remove(String str);
    boolean starsWith(String prefix);
}
