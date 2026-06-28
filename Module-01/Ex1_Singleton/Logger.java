package Ex1_Singleton;

/**
 * Singleton Pattern – Logger
 * Only one instance exists for the entire application lifecycle.
 * Thread-safe via double-checked locking.
 */
public class Logger {

    // volatile ensures visibility across threads
    private static volatile Logger instance = null;

    // Private constructor prevents external instantiation
    private Logger() {
        System.out.println("Logger instance created.");
    }

    // Double-checked locking for thread-safe lazy initialization
    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
