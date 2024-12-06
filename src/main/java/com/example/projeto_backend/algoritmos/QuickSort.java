package com.example.projeto_backend.algoritmos;

public class QuickSort {

    public static int[] sort(int[] array) {
        return quickSort(array, 0, array.length - 1);
    }

    private static int[] quickSort(int[] array, int low, int high) {
        if (low < high) {
            // Escolha do pivô mais robusta
            int pi = partition(array, low, high);

            // Recursão em subarrays
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
        return array;
    }

    private static int partition(int[] array, int low, int high) {
        // Escolha do pivô pelo método da mediana de três
        int mid = low + (high - low) / 2;
        int pivot = medianOfThree(array, low, mid, high);

        // Coloque o pivô no final
        swap(array, pivot, high);

        int pivotValue = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] <= pivotValue) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, high);
        return i + 1;
    }

    private static int medianOfThree(int[] array, int low, int mid, int high) {
        if (array[low] > array[mid]) {
            swap(array, low, mid);
        }
        if (array[low] > array[high]) {
            swap(array, low, high);
        }
        if (array[mid] > array[high]) {
            swap(array, mid, high);
        }
        return mid; // O elemento mediano
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
