package run.ciusyan;

import run.ciusyan.entity.Person;
import run.ciusyan.list.impl.*;
import run.ciusyan.list.List;
import run.ciusyan.queue.DeQue;
import run.ciusyan.queue.Queue;
import run.ciusyan.queue.impl.CircleDeQue;
import run.ciusyan.queue.impl.CircleQueue;
import run.ciusyan.queue.impl.DeQueLinkedImpl;
import run.ciusyan.queue.impl.QueueStackImpl;
import run.ciusyan.stack.Stack;
import run.ciusyan.stack.StackArrayImpl;

public class Main {
    public static void main(String[] args) {
        circleQueue();
    }

    static void test(List<Integer> list) {
        list.add(11);
        list.add(22);
        list.add(33);
        list.add(44);

        list.add(0, 55); // [55, 11, 22, 33, 44]
        list.add(2, 66); // [55, 11, 66, 22, 33, 44]
        list.add(list.size(), 77); // [55, 11, 66, 22, 33, 44, 77]

        list.remove(0); // [11, 66, 22, 33, 44, 77]
        list.remove(2); // [11, 66, 33, 44, 77]
        list.set(2, 333); // [11, 66, 333, 44, 77]
        System.out.println(list.indexOf(55) == list.indexOf(33)); // true
        System.out.println(list.contains(33) && list.contains(11)); // false
        System.out.println(list);
    }

    static void test2(List<Integer> list) {
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        for (int i = 0; i < 50; i++) {
            list.remove(0);
        }
        System.out.println(list);
    }

    static void josephus() {
        DoubleCircleLinkedList<Integer> list = new DoubleCircleLinkedList<>();
        for (int i = 1; i <= 8; i++) {
            list.add(i);
        }

        list.reset();
        while (!list.isEmpty()) {
            list.next();
            list.next();
            System.out.println(list.remove());
        }
        list.reset();

    }

    static void test3() {
        List<Person> list = new LinkedList<>();
        list.add(new Person("zhiyan", 20));
        list.add(new Person("zhiyan2", 20));
        list.add(new Person("zhiyan3", 20));
        list.add(new Person("zhiyan4", 20));
        list.add(0, new Person("ciusyan", 20));
        list.add(1, new Person("ciusyan", 20));
        list.remove(1);

        for (int i = 0; i < 30; i++) {
            list.add(new Person("hah", i));
        }
        System.out.println(list);
    }

    static void stack() {
        Stack<Integer> stack = new StackArrayImpl<>();
        stack.push(11);
        stack.push(22);
        stack.push(33);
        stack.push(44);

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    static void queue() {
        Queue<Integer> queue = new QueueStackImpl<>();
        queue.enQueue(11);
        queue.enQueue(22);
        System.out.println(queue.deQueue());
        System.out.println("Front：" + queue.front());
        queue.enQueue(11);
        queue.enQueue(33);
        queue.enQueue(44);
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }

    }

    static void deQueue() {
        DeQue<Integer> dequeue = new DeQueLinkedImpl<>();
        dequeue.enQueue(11);
        dequeue.enQueueFront(22);
        dequeue.enQueue(33);
        dequeue.enQueue(44);
        dequeue.deQueue();
        dequeue.deQueueRear();
        dequeue.enQueueFront(55);
        dequeue.enQueue(66);
        /*尾66 33 11 55头*/

    }

    static void circleQueue() {
        Queue<Integer> queue = new CircleQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enQueue(i);
        }
        System.out.println(queue);
        // [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

        for (int i = 0; i < 5; i++) {
            queue.deQueue();
        }
        System.out.println(queue);
        // [null, null, null, null, null, 5, 6, 7, 8, 9]

        for (int i = 10; i < 18; i++) {
            queue.enQueue(i);
        }
        System.out.println(queue);
        // [10, 11, 12, 13, 14, 5, 6, 7, 8, 9]
        // [5, 6, 7, 8, 9, 10, 11, 12, 13, 14, null, null, null, null, null]
        // [5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, null, null]

        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }

    }

    static void circleDeque() {
        DeQue<Integer> deQue = new CircleDeQue<>();

        for (int i = 0; i < 10; i++) {
            deQue.enQueueFront(i + 1);
            deQue.enQueue(i + 100);
        }
        System.out.println(deQue);

        /*头 5 4 3 2 1  100 101 102 103 104尾*/
        /*头 5 4 3 2 1  100 101 102 103 104 105 106 8 7 6 尾*/
        /*头 8 7 6 5 4 3 2 1  100 101 102 103 104 105 106 107 108 109 null null 10 9 尾*/
        for (int i = 0; i < 3; i++) {
            deQue.deQueue();
            deQue.deQueueRear();
        }
        System.out.println(deQue);
        /*头 null 7 6 5 4 3 2 1  100 101 102 103 104 105 106 null null null null null null null 尾*/

        deQue.enQueueFront(11);
        deQue.enQueueFront(12);

        System.out.println(deQue);
        /*头 11 7 6 5 4 3 2 1  100 101 102 103 104 105 106 null null null null null null 12 尾*/

        while (!deQue.isEmpty()) {
            System.out.println(deQue.deQueue());
        }

        System.out.println(deQue);
    }

}
