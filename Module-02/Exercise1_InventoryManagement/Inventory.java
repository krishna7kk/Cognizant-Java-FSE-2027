package Exercise1_InventoryManagement;

import java.util.HashMap;
import java.util.Map;

/**
 * Inventory Management System using HashMap.
 *
 * Why HashMap?
 *  - add    : O(1) average
 *  - update : O(1) average
 *  - delete : O(1) average
 *  - lookup : O(1) average  (vs O(n) for ArrayList)
 *
 * ArrayList would be O(n) for search/update/delete because we'd have
 * to iterate over every element. HashMap gives us direct key-based
 * access using productId as the key.
 */
public class Inventory {

    // Key = productId, Value = Product object
    private Map<Integer, Product> inventory = new HashMap<>();

    // ---------- ADD ----------
    // Time complexity: O(1) average
    public void addProduct(Product product) {
        if (inventory.containsKey(product.getProductId())) {
            System.out.println("Product with ID " + product.getProductId() + " already exists.");
            return;
        }
        inventory.put(product.getProductId(), product);
        System.out.println("Added: " + product);
    }

    // ---------- UPDATE ----------
    // Time complexity: O(1) average
    public void updateProduct(int productId, String newName, int newQty, double newPrice) {
        Product p = inventory.get(productId);
        if (p == null) {
            System.out.println("Product ID " + productId + " not found.");
            return;
        }
        p.setProductName(newName);
        p.setQuantity(newQty);
        p.setPrice(newPrice);
        System.out.println("Updated: " + p);
    }

    // ---------- DELETE ----------
    // Time complexity: O(1) average
    public void deleteProduct(int productId) {
        Product removed = inventory.remove(productId);
        if (removed == null) {
            System.out.println("Product ID " + productId + " not found.");
        } else {
            System.out.println("Deleted: " + removed);
        }
    }

    // ---------- DISPLAY ALL ----------
    public void displayAll() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("\n--- Current Inventory ---");
        for (Product p : inventory.values()) {
            System.out.println(p);
        }
        System.out.println("-------------------------\n");
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        Inventory inv = new Inventory();

        // Add products
        inv.addProduct(new Product(101, "Laptop",    50,  75000.00));
        inv.addProduct(new Product(102, "Mouse",    200,    450.00));
        inv.addProduct(new Product(103, "Keyboard", 150,   1200.00));
        inv.addProduct(new Product(101, "Duplicate",  0,      0.00)); // duplicate test

        inv.displayAll();

        // Update
        inv.updateProduct(102, "Wireless Mouse", 180, 650.00);
        inv.displayAll();

        // Delete
        inv.deleteProduct(103);
        inv.deleteProduct(999); // not found test
        inv.displayAll();
    }
}
