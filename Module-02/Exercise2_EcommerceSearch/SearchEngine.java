package Exercise2_EcommerceSearch;

import java.util.Arrays;
import java.util.Comparator;

/**
 * E-Commerce Platform – Search Function
 *
 * Big O Notation recap:
 *  O(1)    – constant time   (best possible)
 *  O(log n)– logarithmic     (binary search)
 *  O(n)    – linear          (linear search)
 *  O(n²)   – quadratic       (nested loops)
 *
 * Linear Search  : Best O(1) | Average O(n) | Worst O(n)
 * Binary Search  : Best O(1) | Average O(log n) | Worst O(log n)
 *
 * Binary search requires the array to be SORTED first.
 * For large catalogs, binary search is clearly superior.
 */
public class SearchEngine {

    // ---------- LINEAR SEARCH ----------
    // Scans every element until a match is found.
    // Time: O(n) in worst/average case
    public static Product linearSearch(Product[] products, int targetId) {
        for (Product p : products) {
            if (p.getProductId() == targetId) {
                return p;
            }
        }
        return null;
    }

    // ---------- BINARY SEARCH ----------
    // Works only on a SORTED array (sorted by productId here).
    // Halves the search space each iteration → O(log n)
    public static Product binarySearch(Product[] sortedProducts, int targetId) {
        int low = 0, high = sortedProducts.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;   // avoids integer overflow
            int midId = sortedProducts[mid].getProductId();

            if (midId == targetId) {
                return sortedProducts[mid];
            } else if (midId < targetId) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {

        // Unsorted array for linear search
        Product[] products = {
            new Product(105, "Headphones",   "Electronics"),
            new Product(102, "Mouse",        "Electronics"),
            new Product(108, "Desk Lamp",    "Furniture"),
            new Product(101, "Laptop",       "Electronics"),
            new Product(110, "Notebook",     "Stationery"),
            new Product(103, "Keyboard",     "Electronics"),
            new Product(107, "Office Chair", "Furniture"),
        };

        // Sorted copy for binary search (sort by productId)
        Product[] sortedProducts = products.clone();
        Arrays.sort(sortedProducts, Comparator.comparingInt(Product::getProductId));

        int target = 107;

        System.out.println("=== Linear Search ===");
        Product result1 = linearSearch(products, target);
        System.out.println("Searching for ID " + target + ": " +
                (result1 != null ? result1 : "Not found"));

        System.out.println("\n=== Binary Search ===");
        Product result2 = binarySearch(sortedProducts, target);
        System.out.println("Searching for ID " + target + ": " +
                (result2 != null ? result2 : "Not found"));

        System.out.println("\n=== Search for non-existing ID 999 ===");
        System.out.println("Linear : " + linearSearch(products, 999));
        System.out.println("Binary : " + binarySearch(sortedProducts, 999));

        System.out.println("\n--- Complexity Summary ---");
        System.out.println("Linear Search : O(n)     – suitable for small/unsorted datasets");
        System.out.println("Binary Search : O(log n) – preferred for large sorted datasets");
    }
}
