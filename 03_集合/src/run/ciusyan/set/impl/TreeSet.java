package run.ciusyan.set.impl;

import run.ciusyan.map.Map;
import run.ciusyan.map.TreeMap;
import run.ciusyan.set.Set;

import java.util.Comparator;

/**
 * 内部使用映射实现
 */
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
