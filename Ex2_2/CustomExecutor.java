package Ex2_2;

import java.util.Arrays;
import java.util.concurrent.*;
public class CustomExecutor<V> extends ThreadPoolExecutor{
    private PriorityBlockingQueue<Runnable> queueTPriority;
    private int numOfCores, corePoolSize, maxPoolSize;
    private int[] currentMax = new int[11];

    /**
     * The constructor of class CustomExecutor
     */
    public CustomExecutor() {
        super(Runtime.getRuntime().availableProcessors() / 2, Runtime.getRuntime().availableProcessors() - 1,
                300, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());
    }
    /**
     * A method for submitting task instances to a priority task queue
     * @param task
     * @return a RunnableFuture<T> that has been executed
     */
    public Future<Object> submit(Task task)
    {
        if (task==null)
        {
            System.out.println("The task is null");
            return null;
        }
        else
        {
            currentMax[task.getPriorityTask().getPriorityValue()]++;
            RunnableFuture<Object> task1 = task;
            super.execute(task1);
            return task1;
        }
    }
    /**
     * A method whose purpose is to submit to the queue an operation that can be performed asynchronously with the addition of TaskType
     * @param task
     * @param taskPriority
     * @return object of type Future
     */
    public Future<Object> submit(Callable task, TaskType taskPriority){
        Future<Object> tasks = null;
        if (task != null || taskPriority != null)
        {
            Task task1 = Task.createTask(task, taskPriority);
            tasks = submit(task1);
        }
        else
        {
            System.out.println("error occurred");
        }

        return tasks;
    }
    /**
     *A method whose purpose is to submit to a queue an operation that can be performed asynchronously without a TaskType as a parameter
     * @param task
     * @return object of type Future
     */
    public Future<Object> submit(Callable task)
    {
        Future<Object> tasks = null;
        if (task != null)
        {
            Task task1 = Task.createTask(task);
            tasks = submit(task1);
        }
        else
        {
            System.out.println("error occurred");
        }

        return tasks;
    }
    /**
     * public method that
     *  called "setCorePoolSize" that takes in an int parameter and sets the value of the "corePoolSize" field to the value of the int parameter.
     * @param corePoolSize
     */
    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }
    /**
     * public method that
     *  called "setNumOfCores" that takes in an int parameter and sets the value of the "numOfCores" field to the value of the int parameter.
     * @param numOfCores
     */
    public void setNumOfCores(int numOfCores) {
        this.numOfCores = numOfCores;
    }
    /**
     * public method that
     *  called "getQueueTPriority" that return us our queue
     * @return queueTPriority
     */
    public PriorityBlockingQueue<Runnable> getQueueTPriority() {
        return queueTPriority;
    }
    /**
     * public method that
     *  called "getNumOfCores" that return us the number of cores
     * @return numOfCores
     */
    public int getNumOfCores() {
        return numOfCores;
    }
    /**
     * public method that
     *  called "getCorePoolSize" that return us the size of cores
     * @return corePoolSize
     */
    public int getCorePoolSize() {
        return corePoolSize;
    }
    /**
     * The method "beforeExecute" that takes in a Thread and a Runnable as parameters. Inside the method,
     *   it checks whether the Runnable is an instance of the "Task" class.
     *  if it is, it casts the Runnable object to a Task object, gets the priority value of the task,
     *  and decrements the corresponding element of the "currentMax" array.
     */

    @Override
    protected void beforeExecute(Thread t, Runnable r)
    {
        currentMax[((Task)r).getPriorityTask().getPriorityValue()]--;
    }
    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    /**
     *A method that check if all tasks is done,not allow entry of additional tasks to the queue
     * and Completing all remaining tasks in the queue
     * In this function we used the shutdown() method that allow previously submitted tasks to execute before terminating
     * In this function we used the isTerminated() method of ThreadPoolExecutor class returns true if all the tasks have been completed following shut down
     * * In this function we used the isTerminated() method of ExecutorService in Java is used to wait for all the tasks submitted to the service to complete execution
     */
    public void gracefullyTerminate()
    {
        super.shutdown();
        while(!super.isTerminated())
        {
            try
            {
                super.awaitTermination(300,TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * public method that
     *  called "getCorePoolSize" that return us the size of cores
     * @return corePoolSize
     */
    public int getCurrentMax()
    {
        for (int i = 0; i < 11; i++)
        {
            if (currentMax[i] > 0)
            {
                return i;
            }
        }
        return 0;
    }

}

