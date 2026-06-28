import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterizedLogging {

    private static final Logger logger =
            LoggerFactory.getLogger(ParameterizedLogging.class);

    public static void main(String[] args) {

        String userName = "Krishna";
        int age = 21;
        double salary = 50000.00;

        logger.info("User Name: {}", userName);
        logger.info("Age: {}", age);
        logger.info("Salary: {}", salary);

        logger.debug("Employee {} is {} years old.", userName, age);
        logger.warn("Salary of {} is {}", userName, salary);
    }
}
