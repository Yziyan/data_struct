package run.ciusyan.set.impl;

import run.ciusyan.set.Set;
import run.ciusyan.tree.BinaryTreeImpl;
import run.ciusyan.tree.RBTree;

/**
 * 内部使用红黑树实现
 */
public class RBTreeSet<E> implements Set<E> {

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
        tree.inorder(new BinaryTreeImpl.Visitor<>() {
            @Override
            protected boolean visit(E element) {
                // 因为访问器定义的位置不一样，需要这样使用
                return visitor.visit(element);
            }
        });
    }
}
