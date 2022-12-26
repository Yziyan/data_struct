package run.ciusyan.set;

/**
 * Set集合
 */
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
