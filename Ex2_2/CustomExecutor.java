package Ex2_2;

import java.util.concurrent.*;
public class CustomExecutor {
    private PriorityBlockingQueue<Runnable> queueTPriority;
    private int numOfCores, corePoolSize, maxPoolSize, maxPriority;
    private ThreadPoolExecutor threadPool1;
    /**
     * The constructor of class CustomExecutor
     */
    public CustomExecutor()
    {
        this.maxPriority=Integer.MAX_VALUE;
        queueTPriority = new PriorityBlockingQueue<Runnable>();
        this.numOfCores = Runtime.getRuntime().availableProcessors();
        this.corePoolSize = numOfCores / 2;
        this.maxPoolSize = numOfCores - 1;
        this.threadPool1 = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 300, TimeUnit.MILLISECONDS, queueTPriority);
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
            this.maxPriority = Math.min(this.maxPriority, task.getPriorityTask().getPriorityValue());
            RunnableFuture<Object> task1 = task;
            threadPool1.execute(task1);
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
            this.maxPriority = Math.min(this.maxPriority, task1.getPriorityTask().getPriorityValue());
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
            this.maxPriority = Math.min(this.maxPriority, task1.getPriorityTask().getPriorityValue());
            tasks = submit(task1);
        }
        else
        {
            System.out.println("error occurred");
        }

        return tasks;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public void setNumOfCores(int numOfCores) {
        this.numOfCores = numOfCores;
    }

    public PriorityBlockingQueue<Runnable> getQueueTPriority() {
        return queueTPriority;
    }

    public int getNumOfCores() {
        return numOfCores;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getMaxPriority() {
        if(this.maxPriority==Integer.MAX_VALUE)
        {
            System.out.println("The queue is empty");
        }
        return maxPriority;
    }

    public ThreadPoolExecutor getThreadPool1() {
        return threadPool1;
    }

    public int getCurrentMax()
    {
        return this.maxPriority;
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
        threadPool1.shutdown();
        while(!threadPool1.isTerminated())
        {
            try
            {
                threadPool1.awaitTermination(300,TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}

