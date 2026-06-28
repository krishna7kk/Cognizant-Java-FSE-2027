package Ex10_MVC;

// VIEW
public class StudentView {
    public void displayStudentDetails(int id, String name, String grade) {
        System.out.println("┌──────────────────────────┐");
        System.out.println("│      Student Record      │");
        System.out.println("├──────────────────────────┤");
        System.out.printf( "│  ID    : %-15d │%n", id);
        System.out.printf( "│  Name  : %-15s │%n", name);
        System.out.printf( "│  Grade : %-15s │%n", grade);
        System.out.println("└──────────────────────────┘");
    }
}
