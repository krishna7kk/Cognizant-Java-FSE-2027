package Ex11_DependencyInjection;

// Domain model
class Customer {
    private int    id;
    private String name;
    private String email;

    public Customer(int id, String name, String email) {
        this.id    = id;
        this.name  = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, name=%s, email=%s]", id, name, email);
    }
}

// Repository Interface
interface CustomerRepository {
    Customer findCustomerById(int id);
    void     saveCustomer(Customer customer);
}

// Concrete Repository (simulates DB)
class CustomerRepositoryImpl implements CustomerRepository {
    public Customer findCustomerById(int id) {
        // In real app this would query a database
        System.out.println("[Repository] Fetching customer ID=" + id + " from DB...");
        if (id == 1) return new Customer(1, "Ravi Kumar",   "ravi@example.com");
        if (id == 2) return new Customer(2, "Priya Sharma", "priya@example.com");
        return null;
    }

    public void saveCustomer(Customer customer) {
        System.out.println("[Repository] Saving " + customer + " to DB.");
    }
}

// Service class – depends on repository via constructor injection
class CustomerService {
    private final CustomerRepository repository;

    // CONSTRUCTOR INJECTION – dependency provided externally
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer getCustomer(int id) {
        Customer c = repository.findCustomerById(id);
        if (c == null) System.out.println("[Service] Customer ID " + id + " not found.");
        return c;
    }

    public void registerCustomer(int id, String name, String email) {
        Customer c = new Customer(id, name, email);
        repository.saveCustomer(c);
        System.out.println("[Service] Customer registered: " + c);
    }
}

// Main – wiring dependencies manually (poor man's DI container)
public class DITest {
    public static void main(String[] args) {
        // Create the repository
        CustomerRepository repo = new CustomerRepositoryImpl();

        // Inject into service via constructor
        CustomerService service = new CustomerService(repo);

        System.out.println("=== Find existing customers ===");
        System.out.println(service.getCustomer(1));
        System.out.println(service.getCustomer(2));
        System.out.println(service.getCustomer(99)); // not found

        System.out.println("\n=== Register new customer ===");
        service.registerCustomer(3, "Arun Raj", "arun@example.com");
    }
}
