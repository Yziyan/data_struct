package run.ciusyan.redblacktree;

import run.ciusyan.balancebinarysearchtree.BBST;

import java.util.Comparator;

/**
 * 自平衡二叉搜索树 —— 红黑树
 */
public class RBTree<E> extends BBST<E> {

    private static final boolean RED = false; // 红色
    private static final boolean BLACK = true; // 黑色

    public RBTree() {
        this(null);
    }

    /**
     * 可以传入一个比较器的构造函数
     *
     * @param comparator：比较器
     */
    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;
        if (parent == null) { // 添加的节点是根节点
            black(node); // 将其染成黑色
            return;
        }

        if (isBlack(parent)) return; // 1、父节点是黑色 —— 不需要处理

        // 写完发现：2、3两种情况，都需要将祖父节点先染成红色，将其抽出来
        Node<E> grandparent = red(parent.parent);
        Node<E> uncle = parent.sibling(); // 取出叔父节点

        if (isRed(uncle)) { // 2、叔父节点是红色 —— 通过合并解决
            // 将父节点、叔父节点染成黑色
            black(parent);
            black(uncle);
            afterAdd(grandparent); // 再将祖父节点当做新添加的节点，去递归调用添加后的逻辑
            return;
        }

        // 来到这里说明：3、叔父节点不是红色 —— 通过旋转解决

        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                black(parent); // 父节点染成黑色
            } else { // LR
                black(node); // 将自己染成黑色
                // 先左旋、后右旋
                rotateLeft(parent);
            }
            // LL、LR最后都需要将祖父节点右旋
            rotateRight(grandparent);
        } else { // R
            if (node.isRightChild()) { // RR
                black(parent); // 父节点染成黑色
            } else { // RL
                black(node); // 将自己染成黑色
                // 先右旋、后左旋
                rotateRight(parent);
            }
            rotateLeft(grandparent);
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        /*
            ①、被删除的节点是红色的情况
            ②、删除的节点有且仅有一个红色的子节点【也就是在二叉搜索树中删除的度为 1 的节点，找到取代它的子节点】
        */
        if (isRed(node)) {
            /*
                ①：如果被删除的节点是红色的情况，将它染黑也没关系，反正它的内存马上就要释放掉了
                ②：将用于代替被删除节点的子节点染成黑色即可
             */
            black(node);
            return;
        }
        Node<E> parent = node.parent; // 取出被删除节点的父节点

        // ③、如果父节点是空的，说明删除的是根节点
        if (parent == null) return;

        /*
            说明以前删除的是左边
            1、(parent.left == null)是被删除的节点
            2、(node.isLeftChild())是父节点下溢
        */
        boolean isLeft = parent.left == null || node.isLeftChild();
        // 左边为 null 说明右边是兄弟节点，否则是左边
        Node<E> sibling = isLeft ? parent.right : parent.left;

        if (isLeft) { // 被删除的节点在左边，与下面的操作对称
            // 兄弟节点是红色
            if (isRed(sibling)) { // 将其转换为兄弟节点是黑色的两种情况
                rotateLeft(parent); // 左旋，变换兄弟节点
                red(parent); // 父节点染成红色
                black(sibling); // 兄弟节点染成黑色
                sibling = parent.right; // 兄弟节点变了，改变引用
            }
            // 能来到这里，兄弟节点肯定是黑色的了
            if (isRed(sibling.right) || isRed(sibling.left)) { // 兄弟至少有一个红色的子节点
                if (isRed(sibling.left)) { // RL的情况，将其转换为RR，与下面统一处理
                    rotateRight(sibling); // 兄弟节点右旋
                    sibling = parent.right; // 兄弟节点变化了
                }
                // 能来到这里，说明都可以看成是RR的情况了
                rotateLeft(parent); // 将父节点左旋
                /*
                    1、兄弟节点变成了新的父节点（新的中心节点）
                    2、将新父节点的颜色继承旧父节点的颜色
                    3、将新父节点的左右子节点都染成黑色
                */
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(sibling.right);
            } else { // 兄弟一个红色的子节点都没有
                boolean isBlack = isBlack(parent); // 记录父节点原先的颜色
                // 将父节点染成黑色，兄弟节点染成红色
                black(parent);
                red(sibling);
                if (isBlack) { // 如果父节点原先就是黑色的
                    afterRemove(parent); // 说明向下合并后，它也会下溢，将它当做被删除的节点
                }
            }
        } else { // 被删除的节点在右边，与上面的操作对称
            // 兄弟节点是红色
            if (isRed(sibling)) { // 将其转换为兄弟节点是黑色的两种情况
                rotateRight(parent); // 右旋，变换兄弟节点
                red(parent); // 父节点染成红色
                black(sibling); // 兄弟节点染成黑色
                sibling = parent.left; // 兄弟节点变了，改变引用
            }
            // 能来到这里，兄弟节点肯定是黑色的了
            if (isRed(sibling.left) || isRed(sibling.right)) { // 兄弟至少有一个红色的子节点
                if (isRed(sibling.right)) { // LR的情况，将其转换为LL，与下面统一处理
                    rotateLeft(sibling); // 兄弟节点左旋
                    sibling = parent.left; // 兄弟节点变化了
                }
                // 能来到这里，说明都可以看成是LL的情况了
                rotateRight(parent); // 将父节点右旋
                /*
                    1、兄弟节点变成了新的父节点（新的中心节点）
                    2、将新父节点的颜色继承旧父节点的颜色
                    3、将新父节点的左右子节点都染成黑色
                */
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(sibling.right);
            } else { // 兄弟一个红色的子节点都没有
                boolean isBlack = isBlack(parent); // 记录父节点原先的颜色
                // 将父节点染成黑色，兄弟节点染成红色
                black(parent);
                red(sibling);
                if (isBlack) { // 如果父节点原先就是黑色的
                    afterRemove(parent); // 说明向下合并后，它也会下溢，将它当做被删除的节点
                }
            }
        }
    }


    /*protected void afterRemove(Node<E> node, Node<E> child) {
        // ①、被删除的节点是红色的情况
        if (isRed(node)) return;
        // ②、删除的节点有且仅有一个红色的子节点【也就是在二叉搜索树中删除的度为 1 的节点，找到取代它的子节点】
        if (isRed(child)) {
            black(child); // 将用于代替被删除节点的子节点染成黑色即可
            return;
        }
        Node<E> parent = node.parent; // 取出被删除节点的父节点

        // ③、如果父节点是空的，说明删除的是根节点
        if (parent == null) return;

        *//*
            说明以前删除的是左边
            1、(parent.left == null)是被删除的节点
            2、(node.isLeftChild())是父节点下溢
        *//*
        boolean isLeft = parent.left == null || node.isLeftChild();
        // 左边为 null 说明右边是兄弟节点，否则是左边
        Node<E> sibling = isLeft ? parent.right : parent.left;

        if (isLeft) { // 被删除的节点在左边，与下面的操作对称
            // 兄弟节点是红色
            if (isRed(sibling)) { // 将其转换为兄弟节点是黑色的两种情况
                rotateLeft(parent); // 左旋，变换兄弟节点
                red(parent); // 父节点染成红色
                black(sibling); // 兄弟节点染成黑色
                sibling = parent.right; // 兄弟节点变了，改变引用
            }
            // 能来到这里，兄弟节点肯定是黑色的了
            if (isRed(sibling.right) || isRed(sibling.left)) { // 兄弟至少有一个红色的子节点
                if (isRed(sibling.left)) { // RL的情况，将其转换为RR，与下面统一处理
                    rotateRight(sibling); // 兄弟节点右旋
                    sibling = parent.right; // 兄弟节点变化了
                }
                // 能来到这里，说明都可以看成是RR的情况了
                rotateLeft(parent); // 将父节点左旋
                *//*
                    1、兄弟节点变成了新的父节点（新的中心节点）
                    2、将新父节点的颜色继承旧父节点的颜色
                    3、将新父节点的左右子节点都染成黑色
                *//*
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(sibling.right);
            } else { // 兄弟一个红色的子节点都没有
                boolean isBlack = isBlack(parent); // 记录父节点原先的颜色
                // 将父节点染成黑色，兄弟节点染成红色
                black(parent);
                red(sibling);
                if (isBlack) { // 如果父节点原先就是黑色的
                    afterRemove(parent, null); // 说明向下合并后，它也会下溢，将它当做被删除的节点
                }
            }
        } else { // 被删除的节点在右边，与上面的操作对称
            // 兄弟节点是红色
            if (isRed(sibling)) { // 将其转换为兄弟节点是黑色的两种情况
                rotateRight(parent); // 右旋，变换兄弟节点
                red(parent); // 父节点染成红色
                black(sibling); // 兄弟节点染成黑色
                sibling = parent.left; // 兄弟节点变了，改变引用
            }
            // 能来到这里，兄弟节点肯定是黑色的了
            if (isRed(sibling.left) || isRed(sibling.right)) { // 兄弟至少有一个红色的子节点
                if (isRed(sibling.right)) { // LR的情况，将其转换为LL，与下面统一处理
                    rotateLeft(sibling); // 兄弟节点左旋
                    sibling = parent.left; // 兄弟节点变化了
                }
                // 能来到这里，说明都可以看成是LL的情况了
                rotateRight(parent); // 将父节点右旋
                *//*
                    1、兄弟节点变成了新的父节点（新的中心节点）
                    2、将新父节点的颜色继承旧父节点的颜色
                    3、将新父节点的左右子节点都染成黑色
                *//*
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(sibling.right);
            } else { // 兄弟一个红色的子节点都没有
                boolean isBlack = isBlack(parent); // 记录父节点原先的颜色
                // 将父节点染成黑色，兄弟节点染成红色
                black(parent);
                red(sibling);
                if (isBlack) { // 如果父节点原先就是黑色的
                    afterRemove(parent, null); // 说明向下合并后，它也会下溢，将它当做被删除的节点
                }
            }
        }
    }*/


    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent); // 需要使用红黑树的节点
    }

    /**
     * 红黑树内部的节点
     */
    private static class RBNode<E> extends Node<E> {
        /**
         * 节点的颜色【默认为红色】
         */
        boolean color = RED;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "RED_";
            }
            return str + element.toString();
        }
    }

    /**
     * 将节点上色
     *
     * @param node：待染色节点
     * @param color：待染的颜色
     * @return ：被染色的节点
     */
    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return node;
        ((RBNode<E>) node).color = color; // 染色
        return node;
    }

    /**
     * 将节点染成红色
     */
    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    /**
     * 将节点染成黑色
     */
    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    /**
     * 查看节点颜色
     *
     * @param node：节点
     * @return ：节点的颜色
     */
    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    /**
     * 查看是否是红色的节点
     */
    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    /**
     * 查看是否是黑色的节点
     */
    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }
}
