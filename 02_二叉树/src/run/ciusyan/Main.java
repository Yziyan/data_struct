package run.ciusyan;

import run.ciusyan.avltree.AVLTree;
import run.ciusyan.binarysearchtree.BinarySearchTree;
import run.ciusyan.binarysearchtree.impl.BSTImpl;
import run.ciusyan.entity.Person;
import run.ciusyan.printer.BinaryTrees;
import run.ciusyan.redblacktree.RBTree;

import java.util.Comparator;

public class Main {

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

    static void test5() {
        Integer[] elements = new Integer[]{
            39, 83, 64, 1, 53, 65, 88, 57, 10, 82, 86, 61, 93, 8, 72, 35, 21, 84, 5, 100
        };
        BinarySearchTree<Integer> AVLTree = new AVLTree<>();
        for (Integer element : elements) {
            AVLTree.add(element);
        }

        BinaryTrees.println(AVLTree);
        for (int i = 0; i < elements.length; i++) {

            AVLTree.remove(elements[i]);

            System.out.println("【" + elements[i] + "】");
            BinaryTrees.println(AVLTree);

            System.out.println("----------------");
        }
    }

    static void test6() {
        Integer[] elements = new Integer[]{
            83, 14, 84, 81, 55, 100, 90, 31, 79, 15, 85, 1, 2, 40, 46, 36, 62
        };
        BinarySearchTree<Integer> rbTree = new RBTree<>();
        for (Integer element : elements) {
            System.out.println("【"+ element +"】");
            rbTree.add(element);
            BinaryTrees.println(rbTree);
        }

    }

    public static void main(String[] args) {
        test5();
    }

}
