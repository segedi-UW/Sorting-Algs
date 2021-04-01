import java.util.Arrays;

public class TestBench {

    public static void main(String[] args) {
        final boolean debug = false;
        System.out.println(testAll(debug) ? "Passed All Tests!" : "Did Not Pass All Tests!");
        // Sorts.setPrint(true);
        // Sorts.radixSort(
        // new int[] {6666, 1537, 9570, 5765, 3012, 4847, 3375, 8603, 7025, 3125, 4926, 1092}, 4);
    }

    public static boolean testAll(boolean debug) {
        Sorts.setPrint(debug);
        if (testCountSort())
            if (testDigitAlg(3240, 4))
                if (testRadixSort())
                    if (testBubbleSort())
                        if (testMergeSort())
                            if (testQuickSort())
                                if (testInsert())
                                    if (testSelectionSort())
                                        if (testInsertionSort())
                                            if (testHeapSort())
                                                return true;
        return false;
    }

    private static boolean testHeapSort() {
        int[] unsorted = {3, 94, 2, 1, 3, 5, 9, 20, 56};
        int[] expected = {1, 2, 3, 3, 5, 9, 20, 56, 94};
        Sorts.heapSort(unsorted);
        if (!Arrays.equals(unsorted, expected)) {
            printError(expected, unsorted);
            return false;
        }
        return true;
    }

    private static boolean testInsertionSort() {
        int[] unsorted = {3, 94, 2, 1, 3, 5, 9, 20, 56};
        int[] expected = {1, 2, 3, 3, 5, 9, 20, 56, 94};
        Sorts.insertionSort(unsorted);
        if (!Arrays.equals(unsorted, expected)) {
            printError(expected, unsorted);
            return false;
        }
        return true;
    }

    private static boolean testSelectionSort() {
        int[] unsorted = {3, 94, 2, 1, 3, 5, 9, 20, 56};
        int[] expected = {1, 2, 3, 3, 5, 9, 20, 56, 94};
        Sorts.selectionSort(unsorted);
        if (!Arrays.equals(unsorted, expected)) {
            printError(expected, unsorted);
            return false;
        }
        return true;
    }

    private static boolean testInsert() {
        int[] sorted = {3, 9, 35, 56, 96};
        int value = 39;
        int[] expected = {3, 9, 35, 39, 56, 96};
        sorted = Sorts.insert(sorted, value);
        if (!Arrays.equals(sorted, expected)) {
            printError(expected, sorted);
            return false;
        }

        int[] values = {3, 59, 2, 39, 53};
        int[] s = {1, 4, 59, 90, 103};
        int[] exp = {1, 2, 3, 4, 39, 53, 59, 59, 90, 103};
        s = Sorts.insert(s, values);
        if (!Arrays.equals(s, exp)) {
            printError(exp, s);
            return false;
        }
        return true;
    }

    private static boolean testQuickSort() {
        int[] unsorted = {33, 93, 520, 3, 4, 69, 93, 234, 34};
        int[] expected = {3, 4, 33, 34, 69, 93, 93, 234, 520};
        Sorts.quickSort(unsorted);
        if (!Arrays.equals(unsorted, expected)) {
            printError(expected, unsorted);
            return false;
        }
        return true;
    }

    private static boolean testBubbleSort() {
        int[] unsorted = {12, 9, 2, 5, 29, 49, 39, 95};
        int[] expected = {2, 5, 9, 12, 29, 39, 49, 95};
        Sorts.bubbleSort(unsorted);
        if (!Arrays.equals(unsorted, expected)) {
            printError(expected, unsorted);
            return false;
        }
        return true;
    }

    private static boolean testRadixSort() {
        int[] unsorted = {201, 113, 957, 343, 567, 573, 345, 379, 887, 447, 267};
        Sorts.radixSort(unsorted, 3);
        int[] expected = {113, 201, 267, 343, 345, 379, 447, 567, 573, 887, 957};
        if (!Arrays.equals(expected, unsorted)) {
            printError(expected, unsorted);
            return false;
        }
        return true;
    }

    private static boolean testDigitAlg(int num, int length) {
        String numStr = "" + num;
        for (int i = 0; i < length; i++) {
            int digit = (int) ((num * Math.pow(0.1, i)) % 10);
            if (Integer.parseInt("" + numStr.charAt(length - i - 1)) != digit) {
                System.out.println("Error, expected: " + numStr.charAt(i) + " actual: " + digit);
                return false;
            }
        }
        return true;
    }

    private static boolean testCountSort() {
        int[] unsorted = {2, 1, 9, 3, 7, 5, 3, 3, 8, 4, 2};
        int min = 1;
        int max = 9;
        Sorts.countSort(unsorted, min, max);
        int[] expected = {1, 2, 2, 3, 3, 3, 4, 5, 7, 8, 9};
        if (!Arrays.equals(expected, unsorted)) {
            printError(expected, unsorted);
            return false;
        }
        return true;
    }

    private static boolean testMergeSort() {
        int[] unsorted = {1, 39, 21, 45, 94, 35, 65, 73, 9};
        int[] expected = {1, 9, 21, 35, 39, 45, 65, 73, 94};
        Sorts.mergeSort(unsorted);
        if (!Arrays.equals(unsorted, expected)) {
            printError(expected, unsorted);
            return false;
        }
        return true;
    }

    private static <T> void printError(int[] expected, int[] actual) {
        System.out.println("Expected: " + Arrays.toString(expected));
        System.out.println("Actual:   " + Arrays.toString(actual));
    }

}
