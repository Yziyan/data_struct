package run.ciusyan;

import run.ciusyan.binarysearchtree.BinarySearchTree;
import run.ciusyan.binarysearchtree.impl.BSTImpl;
import run.ciusyan.entity.Person;
import run.ciusyan.printer.BinaryTrees;

import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        test4();
    }


    static void test() {
        Integer[] elements = new Integer[]{
            10, 5, 20, 3, 7, 14, 24, 1, 4, 9, 11
        };
        BinarySearchTree<Integer> bst = new BSTImpl<>();
        for (Integer element : elements) {
            bst.add(element);
        }
        BinaryTrees.println(bst);
        System.out.println("\n");

        bst.preorder(new BSTImpl.Visitor<>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + " ");

                return element == 7;
            }
        });
        System.out.println("\n");
        bst.inorder(new BSTImpl.Visitor<>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                return element == 9;
            }
        });

        System.out.println("\n");
        bst.postorder(new BSTImpl.Visitor<>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                return element == 5;
            }
        });
        System.out.println("\n");
        bst.levelOrder(new BSTImpl.Visitor<>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                // 当遍历到元素为 7 时，停止遍历
                return element == 7;
            }
        });
        System.out.println("\n");
    }

    static void test1() {
        Integer[] ages = new Integer[]{
            10, 5, 20, 3, 7, 14, 24, 1, 4, 9, 11
        };

        Integer[] heights = new Integer[]{
            46, 6, 85, 18, 30, 9, 42, 2, 67, 16, 100
        };
        BinarySearchTree<Person> bst1 = new BSTImpl<>();
        BinarySearchTree<Person> bst2 = new BSTImpl<>(Comparator.comparingInt(Person::getHeight));
        for (int i = 0; i < ages.length; i++) {
            Person person = new Person(ages[i], heights[i]);
            bst1.add(person);
            bst2.add(person);
        }
        BinaryTrees.println(bst1);
        System.out.println("\n\n");
        BinaryTrees.println(bst2);
    }

    static void test3() {
        Integer[] elements = new Integer[]{
            10, 5, 20, 3, 7, 14, 24, 1, 4
        };

//        for (int i = 0; i < elements.length; i++) {
//            elements[i] = (int) (Math.random() * 100);
//        }

        BinarySearchTree<Integer> bst = new BSTImpl<>();
        for (Integer element : elements) {
            bst.add(element);
        }

        BinaryTrees.println(bst);
        System.out.println(bst.height());
        System.out.println(bst.isComplete());
    }

    static void test4() {
        Integer[] elements = new Integer[]{
            10, 5, 20, 3, 7, 14, 4
        };

        BinarySearchTree<Integer> bst = new BSTImpl<>();
        for (Integer element : elements) {
            bst.add(element);
        }

        BinaryTrees.println(bst);
        bst.remove(5);
        BinaryTrees.println(bst);

    }
}
