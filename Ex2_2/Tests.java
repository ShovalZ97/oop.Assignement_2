package Ex2_2;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);
@Test
public void partialTest(){

        CustomExecutor customExecutor = new CustomExecutor();
    for (int i=0;i<20;i++) {
        var task = Task.createTask(() -> {
            int sum = 0;
            for (int j = 1; j <= 10; j++) {
                sum += j;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        var sumTask = customExecutor.submit(task);
        final int sum;
        try {
            sum = (int) sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(() -> "Sum of 1 through 10 = " + sum);
        Callable<Double> callable1 = () -> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
        // var is used to infer the declared type automatically
        var priceTask = customExecutor.submit(() -> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);
        var reverseTask = customExecutor.submit(callable2, TaskType.IO);
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = (Double) priceTask.get();
            reversed = (String) reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(() -> "Reversed String = " + reversed);
        logger.info(() -> String.valueOf("Total Price = " + totalPrice));
        logger.info(() -> "Current maximum priority = " +
                customExecutor.getCurrentMax());
    }

    customExecutor.gracefullyTerminate();

}
    @Test
    public void test2() {
        CustomExecutor ree = new CustomExecutor();
        Callable<String> testIO = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            sleep(1000);
            return sb.reverse().toString();
        };
        Callable<String> testIO1 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            sleep(1000);
            return sb.reverse().toString();
        };

        Callable<String> testIO2 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            sleep(1000);
            return sb.reverse().toString();
        };
        Callable<String> testIO3 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            sleep(1000);
            return sb.reverse().toString();
        };
        Callable<String> testIO4 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            sleep(1000);
            return sb.reverse().toString();
        };
        Callable<String> testIO5 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            sleep(1000);
            return sb.reverse().toString();
        };
        Callable<String> testIO6 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            sleep(1000);
            return sb.reverse().toString();
        };
        Callable<String> testIO7 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            sleep(1000);
            return sb.reverse().toString();
        };
        Callable<String> testIO8 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            sleep(1000);
            return sb.reverse().toString();
        };
        ree.submit(testIO);
        ree.submit(testIO1);
        ree.submit(testIO3);
        ree.submit(testIO4);
        ree.submit(testIO5);
        ree.submit(testIO6);
        ree.submit(testIO7);
        ree.submit(testIO8);
        System.out.println("THIS IS MADAFAKA : " + ree.getCurrentMax());
        ree.gracefullyTerminate();
    }
}

