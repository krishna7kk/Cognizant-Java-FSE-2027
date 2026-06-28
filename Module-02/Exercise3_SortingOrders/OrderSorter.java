package Exercise3_SortingOrders;

import java.util.Arrays;

/**
 * Sorting Customer Orders by totalPrice
 *
 * Bubble Sort  : O(n²) time  – simple but slow for large data
 * Quick Sort   : O(n log n) average – much faster in practice
 *
 * Quick Sort is generally preferred because:
 *  1. Average case O(n log n) vs Bubble Sort's O(n²)
 *  2. In-place sorting – O(log n) stack space
 *  3. Cache-friendly due to sequential memory access
 */
public class OrderSorter {

    // ===================== BUBBLE SORT =====================
    // Repeatedly swaps adjacent elements if they are in the wrong order.
    // Time: O(n²) worst & average | O(n) best (already sorted)
    // Space: O(1)
    public static void bubbleSort(Order[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (orders[j].getTotalPrice() > orders[j + 1].getTotalPrice()) {
                    // swap
                    Order temp = orders[j];
                    orders[j]     = orders[j + 1];
                    orders[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break; // optimisation: stop early if no swap occurred
        }
    }

    // ===================== QUICK SORT =====================
    // Picks a pivot, partitions array around it, recurses on sub-arrays.
    // Time: O(n log n) average | O(n²) worst (bad pivot choice)
    // Space: O(log n) stack
    public static void quickSort(Order[] orders, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(orders, low, high);
            quickSort(orders, low, pivotIndex - 1);
            quickSort(orders, pivotIndex + 1, high);
        }
    }

    private static int partition(Order[] orders, int low, int high) {
        double pivot = orders[high].getTotalPrice(); // last element as pivot
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (orders[j].getTotalPrice() <= pivot) {
                i++;
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }
        // Place pivot in correct position
        Order temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high]  = temp;
        return i + 1;
    }

    // Helper to print orders
    private static void printOrders(Order[] orders) {
        for (Order o : orders) System.out.println("  " + o);
    }

    // ===================== MAIN =====================
    public static void main(String[] args) {

        Order[] original = {
            new Order(1, "Alice",   15000.00),
            new Order(2, "Bob",      3500.50),
            new Order(3, "Charlie", 87200.00),
            new Order(4, "Diana",    1200.75),
            new Order(5, "Evan",    45000.00),
            new Order(6, "Fiona",    9999.99),
        };

        // --- Bubble Sort ---
        Order[] bubbleOrders = Arrays.copyOf(original, original.length);
        System.out.println("=== Bubble Sort (ascending totalPrice) ===");
        System.out.println("Before:");
        printOrders(bubbleOrders);
        bubbleSort(bubbleOrders);
        System.out.println("After:");
        printOrders(bubbleOrders);

        System.out.println();

        // --- Quick Sort ---
        Order[] quickOrders = Arrays.copyOf(original, original.length);
        System.out.println("=== Quick Sort (ascending totalPrice) ===");
        System.out.println("Before:");
        printOrders(quickOrders);
        quickSort(quickOrders, 0, quickOrders.length - 1);
        System.out.println("After:");
        printOrders(quickOrders);

        System.out.println("\n--- Complexity Summary ---");
        System.out.println("Bubble Sort : O(n²) – avoid for large datasets");
        System.out.println("Quick Sort  : O(n log n) avg – preferred in production");
    }
}
