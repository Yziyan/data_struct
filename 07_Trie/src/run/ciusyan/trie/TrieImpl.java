package run.ciusyan.trie;

import java.util.HashMap;

public class TrieImpl<V> implements Trie<V> {

    private int size;
    private Node<V> root = new Node<>();



    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        root.children.clear();
    }

    @Override
    public V get(String key) {
        Node<V> node = node(key);
        return node == null ? null : node.value;
    }

    @Override
    public boolean contains(String str) {
        return node(str) != null;
    }

    @Override
    public V add(String key, V value) {
        keyCheck(key); // 检查key

        Node<V> currentNode = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            Node<V> childNode = currentNode.getChildren().get(c);
            if (childNode == null) {
                // 来到这里说明子节点为空，说明此字符未上树，将其上树
                childNode = new Node<>();
                currentNode.children.put(c, childNode);
            }

            currentNode = childNode;
        }

        // 遍历结束后，所有字符肯定都在树上了，判断是需要覆盖还是需要新增
        V oldValue = currentNode.value;
        currentNode.value = value;
        if (currentNode.word) return oldValue;

        // 来到这里说明是新增单词
        currentNode.word = true;
        size++;

        return null;
    }

    @Override
    public V remove(String str) {
        return null;
    }

    @Override
    public boolean starsWith(String prefix) {
        keyCheck(prefix);

        Node<V> currentNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            currentNode = currentNode.getChildren().get(c);

            if (currentNode == null) return false;
        }

        return true;
    }

    /**
     * 根据 key 获取Node
     * @param key：键
     * @return ：节点
     */
    private Node<V> node(String key) {
        keyCheck(key); // 检查key

        Node<V> currentNode = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);

            // 根据字符取出 child节点
            currentNode = currentNode.getChildren().get(c);
            if (currentNode == null) return null; // 说明此字符节点不存在，那么别提单词存在了
        }

        // 能来到后面，说明此单词的所有字符都存在，还得查看是否为一个单词

        return currentNode.word ? currentNode : null;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key 不能为空");
        }
    }

    private static class Node<V> {
        // 代表每一个节点的子节点
        HashMap<Character, Node<V>> children;
        // 如果字符是一个单词的结尾，存储的值
        V value;
        // 是否是一个完整的单词
        boolean word;

        /**
         * 获取子节点
         */
        public HashMap<Character, Node<V>> getChildren() {
            return children == null ? (children = new HashMap<>()) : children;
        }
    }

}
