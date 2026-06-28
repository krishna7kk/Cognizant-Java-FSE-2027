package Ex10_MVC;

// CONTROLLER
class StudentController {
    private Student     model;
    private StudentView view;

    public StudentController(Student model, StudentView view) {
        this.model = model;
        this.view  = view;
    }

    public void updateStudentName(String name)   { model.setName(name); }
    public void updateStudentGrade(String grade) { model.setGrade(grade); }

    public void displayStudent() {
        view.displayStudentDetails(model.getId(), model.getName(), model.getGrade());
    }

    // Getters for reading model data
    public String getStudentName()  { return model.getName(); }
    public String getStudentGrade() { return model.getGrade(); }
}

// MAIN
public class MVCTest {
    public static void main(String[] args) {
        // Create Model
        Student student = new Student(1001, "Arjun Sharma", "A");

        // Create View
        StudentView view = new StudentView();

        // Create Controller
        StudentController controller = new StudentController(student, view);

        System.out.println("=== Initial Record ===");
        controller.displayStudent();

        // Update via controller
        controller.updateStudentName("Arjun Kumar Sharma");
        controller.updateStudentGrade("A+");

        System.out.println("\n=== After Update ===");
        controller.displayStudent();
    }
}
