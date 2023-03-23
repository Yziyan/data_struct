package run.ciusyan;

public class Main {

    static void bubbleSort1() {
        int[] array = {10, 20, 31, 41, 98, 65, 21, 53, 8, 33, 87, 43, 29};

        for (int end = array.length; end > 0; end--) {
            for (int begin = 1; begin < end; begin++) {
                if (array[begin] < array[begin - 1]) { // 说明前一个数比后一个数大
                    int temp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = temp;
                }
            }
        }

        for (int i : array) {
            System.out.print(i + "_");
        }
    }

    public static void main(String[] args) {
        bubbleSort1();
    }
}
