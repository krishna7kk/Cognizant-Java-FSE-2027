package Exercise5_TaskManagement;

/**
 * Task Management System using a Singly Linked List
 *
 * Singly Linked List  : each node holds data + pointer to next node.
 * Doubly Linked List  : each node holds data + pointer to next AND previous node
 *                        (useful for backward traversal; used in LRU cache, etc.).
 *
 * Time Complexity:
 *  - Add at head   : O(1)
 *  - Add at tail   : O(n) without tail pointer
 *  - Search        : O(n)
 *  - Traverse      : O(n)
 *  - Delete        : O(n) (search + pointer update)
 *
 * Advantages over arrays:
 *  - Dynamic size – grows/shrinks at runtime.
 *  - Insertion/deletion at head is O(1) vs O(n) shifting in arrays.
 *  - No wasted pre-allocated memory.
 */
public class TaskLinkedList {

    private Task head = null;

    // ---------- ADD at end  O(n) ----------
    public void addTask(Task task) {
        if (head == null) { head = task; }
        else {
            Task curr = head;
            while (curr.next != null) curr = curr.next;
            curr.next = task;
        }
        System.out.println("Added: " + task);
    }

    // ---------- SEARCH  O(n) ----------
    public Task searchById(int taskId) {
        Task curr = head;
        while (curr != null) {
            if (curr.taskId == taskId) return curr;
            curr = curr.next;
        }
        return null;
    }

    // ---------- TRAVERSE  O(n) ----------
    public void traverseAll() {
        System.out.println("\n--- Task List ---");
        if (head == null) { System.out.println("No tasks."); return; }
        Task curr = head;
        while (curr != null) {
            System.out.println("  " + curr);
            curr = curr.next;
        }
        System.out.println("-----------------\n");
    }

    // ---------- DELETE  O(n) ----------
    public boolean deleteTask(int taskId) {
        if (head == null) return false;

        // If head itself is the target
        if (head.taskId == taskId) {
            System.out.println("Deleted: " + head);
            head = head.next;
            return true;
        }

        Task prev = head, curr = head.next;
        while (curr != null) {
            if (curr.taskId == taskId) {
                prev.next = curr.next; // bypass the node
                System.out.println("Deleted: " + curr);
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        System.out.println("Task ID " + taskId + " not found.");
        return false;
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        TaskLinkedList list = new TaskLinkedList();

        list.addTask(new Task(1, "Design UI",        "Pending"));
        list.addTask(new Task(2, "Develop API",      "In Progress"));
        list.addTask(new Task(3, "Write Unit Tests", "Pending"));
        list.addTask(new Task(4, "Deploy to Prod",   "Pending"));

        list.traverseAll();

        System.out.println("Search Task 3: " + list.searchById(3));
        System.out.println("Search Task 9: " + list.searchById(9));

        list.deleteTask(2);
        list.deleteTask(99);

        list.traverseAll();
    }
}
