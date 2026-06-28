package Exercise4_EmployeeManagement;

/**
 * Employee Management System using a fixed-size array.
 *
 * Arrays in memory:
 *  - Stored in contiguous memory blocks.
 *  - Random access via index is O(1) because address = base + (index × element_size).
 *
 * Time Complexity:
 *  - Add      : O(1) if appending at end (O(n) if shifting required)
 *  - Search   : O(n) – must scan each element
 *  - Traverse : O(n) – visit every element
 *  - Delete   : O(n) – search + shift elements left
 *
 * Limitations of arrays:
 *  - Fixed size – must declare capacity upfront.
 *  - Insertion/Deletion in middle is expensive (O(n) due to shifting).
 *  - Use when size is known and random access is frequent.
 */
public class EmployeeManager {

    private static final int MAX_SIZE = 100;
    private Employee[] employees = new Employee[MAX_SIZE];
    private int size = 0;

    // ---------- ADD  O(1) ----------
    public void addEmployee(Employee emp) {
        if (size == MAX_SIZE) {
            System.out.println("Array is full. Cannot add more employees.");
            return;
        }
        employees[size++] = emp;
        System.out.println("Added: " + emp);
    }

    // ---------- SEARCH  O(n) ----------
    public Employee searchById(int id) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId() == id) return employees[i];
        }
        return null;
    }

    // ---------- TRAVERSE  O(n) ----------
    public void traverseAll() {
        System.out.println("\n--- All Employees ---");
        if (size == 0) { System.out.println("No records."); return; }
        for (int i = 0; i < size; i++) System.out.println("  " + employees[i]);
        System.out.println("---------------------\n");
    }

    // ---------- DELETE  O(n) ----------
    public boolean deleteEmployee(int id) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId() == id) {
                Employee removed = employees[i];
                // Shift left to fill the gap
                for (int j = i; j < size - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                employees[--size] = null; // clear last slot
                System.out.println("Deleted: " + removed);
                return true;
            }
        }
        System.out.println("Employee ID " + id + " not found.");
        return false;
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        EmployeeManager mgr = new EmployeeManager();

        mgr.addEmployee(new Employee(1, "Ravi Kumar",   "Developer",      65000));
        mgr.addEmployee(new Employee(2, "Priya Sharma", "Manager",        90000));
        mgr.addEmployee(new Employee(3, "Arun Raj",     "Analyst",        55000));
        mgr.addEmployee(new Employee(4, "Sneha Mehta",  "HR",             50000));

        mgr.traverseAll();

        System.out.println("Search ID 3: " + mgr.searchById(3));
        System.out.println("Search ID 9: " + mgr.searchById(9));

        mgr.deleteEmployee(2);
        mgr.deleteEmployee(99);

        mgr.traverseAll();
    }
}
