package Ex1_Singleton;

public class SingletonTest {
    public static void main(String[] args) {
        Logger log1 = Logger.getInstance();
        Logger log2 = Logger.getInstance();
        Logger log3 = Logger.getInstance();

        log1.log("Application started");
        log2.log("Processing request");
        log3.log("Application stopped");

        System.out.println("\nSame instance? log1==log2: " + (log1 == log2));
        System.out.println("Same instance? log2==log3: " + (log2 == log3));
        System.out.println("Hash log1: " + System.identityHashCode(log1));
        System.out.println("Hash log2: " + System.identityHashCode(log2));
    }
}
