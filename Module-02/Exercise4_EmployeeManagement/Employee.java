package Exercise4_EmployeeManagement;

public class Employee {
    private int employeeId;
    private String name;
    private String position;
    private double salary;

    public Employee(int employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name       = name;
        this.position   = position;
        this.salary     = salary;
    }

    public int getEmployeeId()  { return employeeId; }
    public String getName()     { return name; }
    public String getPosition() { return position; }
    public double getSalary()   { return salary; }

    @Override
    public String toString() {
        return String.format("Employee[id=%d, name=%s, position=%s, salary=%.2f]",
                employeeId, name, position, salary);
    }
}
