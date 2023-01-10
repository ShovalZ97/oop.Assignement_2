package Ex2_2;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Task<E> extends FutureTask<E> implements Callable<E>,Comparable<Task<E>>
{
    private Callable task;
    private TaskType priorityTask;
    private E temp;
    /**
     *Constructor of Task class
     * @param task
     * the superclass constructor can be called from the first line of a subclass constructor by using
     *the special keyword super() and passing appropriate parameters
    */
    private Task(Callable task){
        super(task);
        this.task=task;
        this.priorityTask = TaskType.OTHER;
    }
    /**
     *Constructor of Task class
     * @param task
     * @param priorityTask
     * the superclass constructor can be called from the first line of a subclass constructor by using
     *the special keyword super() and passing appropriate parameters
     */
    private Task(Callable task,TaskType priorityTask){
        super(task);
        this.task=task;
        this.priorityTask = priorityTask;
    }

    public Callable getTask() {
        return task;
    }

    public void setPriorityTask(TaskType priorityTask) {
        this.priorityTask = priorityTask;
    }

    public TaskType getPriorityTask() {
        return priorityTask;
    }
    public void setTask(Callable task) {
        this.task = task;
    }
    /**
     * The call() method is called in order to execute the asynchronous task.
     * The call() method can return a result. If the task is executed asynchronously,
     * the result is typically propagated back to the creator of the task via a Java Future
     */

    @Override
    public E call() throws Exception {
        if(this.task!=null) {
            try {
                return  (E) this.task.call();
            }catch (InterruptedException | ExecutionException e){
                throw new RuntimeException(e);
            }
        }
        else{
            System.out.println("The task is null");
            return null;
        }
    }

    /**
     * First factory method
     * Creates a Task with a default priority
     * @param task The task to be performed
     * @return The created Task object
     */
    public static <T> Task<T> createTask(Callable<T> task) {
        return new Task<T>(task);
    }

    /**
     * Second factory method
     * @param task The task to be performed
     * @param priorityTask     The Task's priority
     * @return The created Task object
     */
    public static <T> Task<T> createTask(Callable<T> task, TaskType priorityTask) {
        return new Task<T>(task, priorityTask);
    }
    /**
     * The equals() method compares two strings, and returns true if the strings are equal, and false if not.
     */

@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task1 = (Task) o;
        return Objects.equals(task, task1.task) && priorityTask == task1.priorityTask;
    }
    /**
     * The hashCode() method in Java is used to compute hash values of Java objects.
     */
    @Override
    public int hashCode() {
        return Objects.hash(task, priorityTask);
    }

    @Override
    public String toString() {
        return "Task[" + "taskType=" + this.priorityTask + ", Priority=" + this.getPriorityTask().getPriorityValue()+ ']';
    }
    /**
     * compareTo() method compares two strings lexicographically.
     * The comparison is based on the Unicode value of each character in the strings.
     * The method returns 0 if the string is equal to the other string
     */

    @Override
    public int compareTo(Task<E> o) {
        return Integer.compare(this.getPriorityTask().getPriorityValue(), o.getPriorityTask().getPriorityValue());
    }
}

