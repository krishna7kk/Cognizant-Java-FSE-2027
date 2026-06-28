package Exercise6_LibraryManagement;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Library Management System – Search by Title
 *
 * Linear Search : O(n) – works on unsorted data
 * Binary Search : O(log n) – requires sorted data
 *
 * When to use which:
 *  - Small collection (< ~1000 books) or unsorted → Linear Search (simpler code)
 *  - Large collection, sorted (e.g., alphabetical catalogue) → Binary Search
 *  - If data changes frequently (inserts/deletes), maintaining sorted order
 *    adds overhead – weigh that cost against search speed benefit.
 */
public class Library {

    // ---------- LINEAR SEARCH by title  O(n) ----------
    public static Book linearSearchByTitle(Book[] books, String title) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) return b;
        }
        return null;
    }

    // ---------- BINARY SEARCH by title  O(log n) ----------
    // Assumes books[] is sorted alphabetically by title (case-insensitive)
    public static Book binarySearchByTitle(Book[] sortedBooks, String title) {
        int low = 0, high = sortedBooks.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = sortedBooks[mid].getTitle().compareToIgnoreCase(title);

            if (cmp == 0)       return sortedBooks[mid];
            else if (cmp < 0)   low  = mid + 1;
            else                high = mid - 1;
        }
        return null;
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {

        Book[] books = {
            new Book(1, "The Alchemist",           "Paulo Coelho"),
            new Book(2, "Clean Code",              "Robert C. Martin"),
            new Book(3, "Design Patterns",         "Gang of Four"),
            new Book(4, "Effective Java",          "Joshua Bloch"),
            new Book(5, "Introduction to Algorithms","Cormen et al."),
            new Book(6, "Rich Dad Poor Dad",       "Robert Kiyosaki"),
        };

        // Sort copy for binary search
        Book[] sortedBooks = books.clone();
        Arrays.sort(sortedBooks, Comparator.comparing(b -> b.getTitle().toLowerCase()));

        String query1 = "Effective Java";
        String query2 = "Unknown Book";

        System.out.println("=== Linear Search ===");
        System.out.println("Searching: '" + query1 + "' → " + linearSearchByTitle(books, query1));
        System.out.println("Searching: '" + query2 + "' → " + linearSearchByTitle(books, query2));

        System.out.println("\n=== Binary Search (sorted array) ===");
        System.out.println("Searching: '" + query1 + "' → " + binarySearchByTitle(sortedBooks, query1));
        System.out.println("Searching: '" + query2 + "' → " + binarySearchByTitle(sortedBooks, query2));

        System.out.println("\n--- Complexity Summary ---");
        System.out.println("Linear Search : O(n)     – small/unsorted datasets");
        System.out.println("Binary Search : O(log n) – large sorted datasets");
    }
}
