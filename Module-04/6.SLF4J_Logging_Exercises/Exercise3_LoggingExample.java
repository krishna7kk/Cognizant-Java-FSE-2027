import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingExample {

    private static final Logger logger =
            LoggerFactory.getLogger(LoggingExample.class);

    public static void main(String[] args) {

        logger.trace("Trace Message");
        logger.debug("Debug Message");
        logger.info("Application Started");
        logger.warn("Warning Message");
        logger.error("Error Message");

        System.out.println("Check console and app.log file.");
    }
}
