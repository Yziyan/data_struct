package run.ciusyan;

import run.ciusyan.trie.Trie;
import run.ciusyan.trie.TrieImpl;

public class Main {

    static void test1() {
        Trie<Integer> trie = new TrieImpl<>();
        trie.add("zyan", 1);
        trie.add("ciusyan", 2);
        trie.add("cherlin", 3);
        trie.add("zhiyan", 4);
        trie.add("志颜", 5);

        Asserts.test(trie.size() == 5);
        Asserts.test(trie.starsWith("c"));
        Asserts.test(trie.starsWith("ci"));
        Asserts.test(trie.starsWith("志"));
        Asserts.test(!trie.starsWith("jo"));
        Asserts.test(trie.get("志颜") == 5);
    }

    public static void main(String[] args) {
        test1();
    }
}
