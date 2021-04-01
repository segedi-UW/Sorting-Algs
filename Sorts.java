import java.util.Arrays;

public class Sorts {

    private static boolean PRINT = false;

    private enum Type {
        COUNT, RADIX, BUBBLE, MERGE, QUICK, INSERTION, SELECTION, HEAP;
    }

    /**
     * Count sort is a stable sorting algorithm of O(n). Count sort sorts by
     * using the range of values passed. Performance decreases as the range
     * approaches large numbers. To handle extreme array sizes, radix sort should
     * be used instead.
     * @param array the array to sort
     * @param min the minimum value in the array
     * @param max the maximum value in the array
     */
    public static void countSort(int[] array, int min, int max) {
        // calculate the range
        int range = max - min;
        // make an array of values (dependent on range)
        int[] count = new int[range + 1];

        // count how many occurrences there are of each value O(n)
        for (int i = 0; i < array.length; i++) {
            count[array[i] - min]++;
        }

        // change count to an end points array
        // this could also be done by making a new array called "endpoints" but
        // I did this to save memory. It makes the cumulative complexity O(3n) = O(n)
        countsToEndpoints(count);

        int[] sorted = new int[array.length];

        // reloop backwards (to maintain a stable sort), sorting using endpoints (need to decrement
        // endpoints
        // before using them, firstly to make them an index, then afterword to
        // correctly sort them
        for (int i = array.length - 1; i >= 0; i--) {
            // sorted[endpoint[arrayvalue - min]]
            sorted[--count[array[i] - min]] = array[i];
        }

        deepCopy(array, sorted);
        print(array, Type.COUNT);
    }

    /**
     * Modifies the original array to match the copy. The original copy must be length
     * (copy.length - startIndex) in order to avoid natural exceptions.
     * @param arr the original array
     * @param toCopy the array to copy
     * @param startIndex the index to start copying from
     */
    private static void deepCopy(int[] arr, int[] toCopy, int startIndex) {
        int i = 0;
        for (int k = startIndex; k < arr.length; k++) {
            arr[i] = toCopy[k];
            i++;
        }
    }

    /**
     * Modifies the original array to match the copy array assuming the original 
     * is the same length as the copy. If it is smaller a natural exception will
     * be thrown. If larger, then only all elements up to the copy's size will
     * match.
     * @param arr the original array
     * @param toCopy the array to copy
     */
    private static void deepCopy(int[] arr, int[] toCopy) {
        deepCopy(arr, toCopy, 0);
    }

    /**
     * Radix sort is a stable sorting algorithm of O(n). Radix sort sorts by using
     * count sort on a per digit basis. Handles large array sizes as the count range
     * is always 10 (0 - 9), however it requires that all numbers be the same number
     * of digits.
     * @param array the array to sort
     * @param length the length of all the elements in the array.
     * @see #countSort(int[], int, int)
     */
    public static void radixSort(int[] array, int length) {
        // 1235 length = 4
        // 1235 * (0.1^length) = 1235 * (0.1 * 0.1 * 0.1 * 0.1)
        // = 123.5 * 0.1 * 0.1 * 0.1 = 12.35 * 0.1 * 0.1
        // = 1.235 * 0.1 -> should use (0.1^length - i)
        // then use % 10 to get the digit

        int[] sorted = new int[array.length];
        int[] digits = new int[array.length];
        // needs to cover 0 - 9 (10 numbers)
        int[] count = new int[10];

        for (int i = 0; i < length; i++) {
            // reset count
            if (i != 0)
                Arrays.fill(count, 0);
            for (int k = 0; k < array.length; k++) {
                digits[k] = (int) ((array[k] * Math.pow(0.1, i)) % 10);
                count[digits[k]]++;
            }
            countsToEndpoints(count);
            for (int k = array.length - 1; k >= 0; k--) {
                sorted[--count[digits[k]]] = array[k];
            }
            // copy sorted to array.
            deepCopy(array, sorted);
            print(array, Type.RADIX + " Iteration: " + (i + 1) + " ");
        }
        print(array, Type.RADIX);
    }

    /**
     * Takes all counts and makes them endpoints. Used in Count sort and Radix sort
     * @param count the count array
     */
    private static void countsToEndpoints(int[] count) {
        int sum = 0;
        for (int i = 0; i < count.length; i++) {
            sum += count[i];
            count[i] = sum;
        }
    }

    /**
     * Simplest sorting algorithm that swaps values that are immediately adjacent to
     * each other if they are not correctly ordered. Knows the list is sorted if
     * there is a pass of the entire list without a swap. Best case: O(n) one sort; 
     * Worst case: O(n^2) when list is reverse sorted. Bubble sort is a stable sorting
     * algorithm.
     * @param array the array to sort
     */
    public static void bubbleSort(int[] array) {
        boolean sorted;
        do {
            sorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    swap(array, i, i + 1);
                    sorted = false;
                }
            }
        } while (!sorted);
        print(array, Type.BUBBLE);
    }

    /**
     * Divide and conquer algorithm that splits the array in half repeatedly until 
     * each subarray is length 1. Then merges all arrays together, sorting as it goes.
     *  Merge sort is a stable sorting algorithm. O(nlogn) in all cases.
     * @param array
     */
    public static void mergeSort(int[] array) {
        mergeHelper(array, 0, array.length - 1);
        print(array, Type.MERGE);
    }

    /**
     * Recursive method for mergeSort(int[]) that splits the array in half untill
     * length 1, and then merges them all back together using merge(int[], int, int, int)
     * @param array the array to sort
     * @param left the left index
     * @param right the right index
     */
    private static void mergeHelper(int[] array, int left, int right) {
        if (left >= right)
            return;
        int middle = (left + right - 1) / 2;
        mergeHelper(array, left, middle);
        mergeHelper(array, middle + 1, right);
        merge(array, left, middle, right);
    }

    /**
     * Merges two arrays using the mergeSort merge algorithm.
     * Geeks for geeks reference: 
     * {@link https://www.geeksforgeeks.org/merge-sort/}
     * Merge sort is a stable sorting algorithm with O(nlogn) performance
     * 
     * Useful for sorting linked lists as random access is not as much a problem like
     * it is for quick sort.
     * @param array the array to sort
     * @param left the left size
     * @param midIndex the middle index
     * @param right the right size
     */
    private static void merge(int[] array, int left, int midIndex, int right) {
        int sizeL = midIndex - left + 1;
        int sizeR = right - midIndex;

        int[] leftArr = new int[sizeL];
        int[] rightArr = new int[sizeR];

        // copy the respective sections of each
        for (int i = 0; i < sizeL; i++)
            leftArr[i] = array[left + i];
        for (int i = 0; i < sizeR; i++)
            rightArr[i] = array[midIndex + 1 + i];

        int l = 0, r = 0, i = left;
        while (l < leftArr.length && r < rightArr.length) {
            // if the left array value is less than or equal to the right array
            // then add that value first... otherwise add the right value first
            if (leftArr[l] <= rightArr[r]) {
                array[i] = leftArr[l];
                l++;
            } else {
                array[i] = rightArr[r];
                r++;
            }
            i++;
        }

        while (l < leftArr.length) {
            array[i] = leftArr[l];
            l++;
            i++;
        }

        while (r < rightArr.length) {
            array[i] = rightArr[r];
            r++;
            i++;
        }

    }

    /**
     * A divide and conquer algorithm that picks an element as a pivot and
     * partitions the given array around the picked pivot. 
     * {@link https://www.geeksforgeeks.org/quick-sort/}
     * 
     * Worst case is O(n^2), however the average case is O(nlogn). Merge sort is
     * considered better when dealing with large amounts of data and when it is in
     * external storage.
     * 
     * Quick sort is generally considered better when in use with arrays, as there
     * is less overhead since the array does not need to be stored more than once
     * (except in the aforementioned huge cases)
     * TODO I want to implement the median pivot case... that or the random one
     * @param array the array to sort
     */
    public static void quickSort(int[] array) {
        quickSortHelper(array, 0, array.length - 1);
        print(array, Type.QUICK);
    }

    /**
     * This implementation uses the last element as the pivot. The pivot
     * is then put at the correct position in a sorted array.
     * @param array the array to sort
     * @return the pivot index
     */
    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1; // index of smaller element
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;

                swap(array, i, j);
            }
        }

        swap(array, i + 1, high);

        return i + 1;
    }

    /**
     * Recursively partitions and then sorts the list based off the partition
     * @param array the array to sort
     * @param low the lowIndex
     * @param high the highIndex
     */
    private static void quickSortHelper(int[] array, int low, int high) {
        if (low < high) {
            // partitioning index
            int pi = partition(array, low, high);
            print(array, "partioned array (" + pi + "): ");
            quickSortHelper(array, low, pi - 1);
            quickSortHelper(array, pi + 1, high);
        }
    }

    /**
     * Takes the minimum value and puts it at the first index, then does this
     * until the end of the list. O(n^2) time complexity. Useful when memory
     * write is a costly operation.
     * @param array the array to sort
     */
    public static void selectionSort(int[] array) {
        int sortIndex = 0;
        for (int i = 0; i < array.length; i++) {
            int minIndex = sortIndex;
            for (int j = sortIndex; j < array.length; j++) {
                if (array[j] < array[minIndex])
                    minIndex = j;
            }
            swap(array, sortIndex, minIndex);
            sortIndex++;
        }
        print(array, Type.SELECTION);
    }

    /**
     * Sorts value into the sorted array. Worst case O(n)
     * @param sortedArray the sorted array
     * @param value the value to add
     * @return the new sorted array
     */
    public static int[] insert(int[] sortedArray, int value) {
        int[] insertArray = Arrays.copyOf(sortedArray, sortedArray.length + 1);
        int insertIndex = 0;
        boolean added = false;
        for (int i = 0; i < sortedArray.length; i++) {
            if (!added && value < sortedArray[i]) {
                insertArray[insertIndex] = value;
                i--;
                added = true;
            } else {
                insertArray[insertIndex] = sortedArray[i];
            }
            insertIndex++;
        }
        return insertArray;
    }

    /**
     * Sorts values into the sorted array. Worst case O(n^2).
     * @param sortedArray the sorted array
     * @param values the values to add
     * @return the new sorted array
     */
    public static int[] insert(int[] sortedArray, int[] values) {
        for (int i = 0; i < values.length; i++) {
            sortedArray = insert(sortedArray, values[i]);
        }
        return sortedArray;
    }

    /**
     * Insertion sort works by sorting in ascending order, checking if the current
     * element is smaller than the previous. If it is, a nested loop activates to
     * loop until it is in the correct position. This sorting algorithm has O(n^2).
     * However, best case of O(n) if the array is already sorted except for some
     * number of values (small). It is a stable sort.
     * @param array the array to sort
     */
    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                for (int j = i; j > 0; j--) {
                    if (array[j] < array[j - 1])
                        swap(array, j, j - 1);
                    else
                        break;
                }
            }
        }
        print(array, Type.INSERTION);
    }

    /**
     * Sorts the array by creating a max-heap and then calling heapify recursively
     * after putting the root as a leaf to sort all elements. Heapify is not stable,
     * but has O(nlogn). In practice, Mergesort and Quicksort are better, but Heapsort
     * is used because of how common the Heap data structure is.
     * @param array to sort
     */
    public static void heapSort(int[] array) {
        int n = array.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(array, 0, i);
            heapify(array, i, 0);
        }
        print(array, Type.HEAP);
    }

    /**
     * Moves the largest value to the root.
     * @param array the array to heapify
     * @param n the size of the array
     * @param i the index of the current node
     */
    private static void heapify(int[] array, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && array[l] > array[largest])
            largest = l;
        if (r < n && array[r] > array[largest])
            largest = r;

        if (largest != i) {
            swap(array, i, largest);
            heapify(array, n, largest);
        }
        print(array, "Heapify for node: " + i + " ");
    }

    /**
     * Swaps the two indexes of the array
     * @param array the array to swap in
     * @param index1 the first index
     * @param index2 the second index
     */
    private static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    /**
     * Prints the contents of the array if printing is enabled. Used for debugging.
     * @param array the array to print
     */
    private static void print(int[] array, Type sort) {
        print(array, sort + " Sorted Array: ");
    }

    /**
     * Prints the contents of the array if printing is enabled with a label.
     * Used for debugging.
     * @param array the array to print
     * @param label the label to print
     */
    private static void print(int[] array, String label) {
        if (PRINT)
            System.out.println(label + Arrays.toString(array));
    }

    /**
     * Sets if the sorted arrays should be printed upon completion of the respective
     * sorting algorithm.
     * @param print true or false if printing should occur
     */
    public static void setPrint(boolean print) {
        PRINT = print;
    }

    // public static char[] radixSort(char[] array) {}

}
