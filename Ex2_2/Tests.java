package Ex2_2;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import java.util.concurrent.*;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);
@Test
public void partialTest() {
    CustomExecutor customExecutor = new CustomExecutor();
    customExecutor.setCorePoolSize(1);
    customExecutor.setMaxPoolSize(1);
    for (int i = 0; i < 10; i++) {
        Callable<String> testIO = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };

        var reverseTask1 = customExecutor.submit(testIO, TaskType.IO);
        var task = Task.createTask(() -> {
            int sum = 0;
            for (int j = 1; j <= 10; j++) {
                sum += j;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        var sumTask = customExecutor.submit(task);
        var testMath = customExecutor.submit(() -> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);

        Callable<String> testIO2 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
        var test1 = customExecutor.submit(testIO2, TaskType.IO);
        System.out.println(customExecutor.getQueueTPriority().toString());
        customExecutor.getCurrentMax();
        final String func1;
        final double func2;
        final int func3;
        try {
            func1 = (String) test1.get();
            func2 = (Double) testMath.get();
            func3 = (int) sumTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        Task task1 = Task.createTask(()->{
            int factorial = 1;
            for(int g=1; g<=6 ; g++){
                factorial *= g;
            }
            return factorial;
        }, TaskType.COMPUTATIONAL);
        Future factorialTask1 = customExecutor.submit(task1);
        final int factorial;
        try {
            factorial = (int) factorialTask1.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "The factorial of 6 = " + factorial);
        logger.info(() -> "Reversed String = " + func1);
        logger.info(() -> String.valueOf("Total Price = " + func2));
        logger.info(() -> "Current maximum priority = " + customExecutor.getCurrentMax());
    }
    customExecutor.gracefullyTerminate();
}}

