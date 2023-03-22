package run.ciusyan.trie;

import java.util.HashMap;

public class TrieImpl<V> implements Trie<V> {

    private int size;
    private Node<V> root;



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
        root = null;
    }

    @Override
    public V get(String key) {
        Node<V> node = node(key);

        return node != null && node.word ? node.value : null;
    }

    @Override
    public boolean contains(String str) {
        Node<V> node = node(str);

        return node != null && node.word;
    }

    @Override
    public V add(String key, V value) {
        keyCheck(key); // 检查key

        if (root == null) {
            root = new Node<>(null);
        }

        Node<V> currentNode = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            boolean childEmpty = currentNode.children == null;

            Node<V> childNode = childEmpty ? null : currentNode.children.get(c);
            if (childNode == null) {
                // 来到这里说明子节点为空，说明此字符未上树，将其上树

                // 这是判断是否有children，有就用自己的，没有就新建
                currentNode.children = childEmpty ?  new HashMap<>() : currentNode.children;
                childNode = new Node<>(currentNode);
                childNode.character = c;
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
        Node<V> node = node(str);

        if (node == null || !node.word) return null;

        // 能来到这里，肯定需要删除了
        size--;
        V oldValue = node.value;

        if (node.children != null && !node.children.isEmpty()) {
            // 来到这里说明需要清除单词标记即可
            node.word = false;

            return oldValue;
        }

        // 能来到这里，说明没有子节点，需要向上遍历，查看是否需要删除
        Node<V> parent;
        while ((parent = node.parent) != null) {
            parent.children.remove(node.character);

            // 需要判断父节点有没有单词标记，or 它还有其他子节点吗？有就直接退出，就不用往上走了
            if (parent.word || !parent.children.isEmpty()) break;

            node = parent;
        }

        return oldValue;
    }

    @Override
    public boolean starsWith(String prefix) {
        return node(prefix) != null;
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
            if (currentNode == null || currentNode.children == null || currentNode.children.isEmpty()) return null;

            char c = key.charAt(i);
            // 根据字符取出 child节点
            currentNode = currentNode.children.get(c);
        }

        // 能来到后面，说明此单词的所有字符都存在，还得查看是否为一个单词

        return currentNode;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key 不能为空");
        }
    }

    private static class Node<V> {
        // 该节点的父节点
        Node<V> parent;
        // 该节点存储的字符
        Character character;
        // 代表每一个节点的子节点
        HashMap<Character, Node<V>> children;
        // 如果字符是一个单词的结尾，存储的值
        V value;
        // 是否是一个完整的单词
        boolean word;

        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }

}
