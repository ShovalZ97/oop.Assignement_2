# OOP.Assignement2

## Part one Ex2_1

In this assignment, you create several text files calculate the number of lines in these files. We will use three methods:
• Normal method without the use of threads,
• use of threads,
• Using ThreadPool 

Within each call of the methods over our file, we are measuring the elapsed time for each action.
In this part there is a class called 1_Ex2 and in it are written 4 functions:

### Function 1 - Create Text Files
Input:
n - a natural number representing the number of text files.
The file names will read like this:
.file_1, file_2 , …, file_n.
The number of lines in each file is a random number, obtained with the help of a usage class
In this class and in the seed and bound parameters.
Output: The function creates n text files on disk and returns an array
of the file names. Each line in the file contains at least 10 characters. 

### Function2 - getNumOfLines
Input: an array that contains the file names.
Output: total number of lines of the files.
In this function we used in Scanner class in nextLine() method, which is used to read Strings.

### Function 3 - getNumOfLinesThreads
Input: an array that contains the file names.
Output: total number of lines of the files.
In this function we used the CountLine class that we wrote. This class extends from the thread class.
In class that we wrote that call CountLine we have the run method that calculating total number of lines of the file. 
The function creates a new CountLine, with each of the file name ,activating it's run method by start(), and join each of the threads to get total line count sum of all files.

### Function 4 - getNumOfLinesThreadPool
Input: an array that contains the file names.
Output: total number of lines of the files.
In this function we used the CountLineCollable class that we wrote. This class implements from the Callable interface that similar to Runnable but a Callable interface that basically throws a checked exception and returns some results. This is one of the major differences between the upcoming Runnable interface where no value is being returned. In this interface, it simply computes a result else throws an exception if unable to do so..
The Callable interface is a generic interface containing a single call() method that returns a generic value.
The call() method method that calculating total number of lines of the file.
In our function getNumOfLinesThreadPool we are create thread pool with a number of threads equal to the number of files and Array list from Future objects,that represents the result of an asynchronous computation. When the asynchronous task is created, a Java Future object is returned. This Future object functions as a handle to the result of the asynchronous task. Once the asynchronous task completes, the result can be accessed via the Future object returned when the task was started. returned by the thread pool. It iterates over the list of file names and creates a CountLineCollable Callable object for each file name. Later on submits the Callable object to the thread pool and add the returned Future object in the list.
After we are iterates over the list of Future objects and retrieves the result of each Future object by call the get() method ,each result we add to the counter and
to finish the thread pool is shut down. 

### Function 5 - deleteAllFiles
In this function ew are deleting all the files that we are creating after we are running all the functions.

### Class diagram

<img src="https://user-images.githubusercontent.com/118892976/211633816-fef45783-d7b6-4823-9fbd-fd27a6809722.png" alt="drawing" width="500"/>

### Running times of methods 2,3,4

#### Time for 500 files:
##### Without Threads : 110 ms,
##### Using Threads: 69 ms,
##### Using ThreadPool: 59 ms.
 
#### Time for 1000 files:
##### Without Threads : 167 ms,
##### Using Threads: 109 ms,
##### Using ThreadPool: 79 ms.
 
#### Time for 5000 files:
##### Without Threads : 793 ms,
##### Using Threads: 457 ms,
##### Using ThreadPool: 454 ms.

We can notice that the calculation with threadPool is almost always faster than using threads. When we look at the rest of the calculations with 500 files, 1000 files and 5000 files, we can see that the calculations with threadPool are faster than using only one thread.
There are two reasons why using a threadPool can be faster than using threads:
The first one, is that we can use the same thread for multiple tasks instead of creating a completely new thread.
And the second one, is that we use submit() method that accepts a Callable instead of Runnable, so we can get the return value faster with get() 

## Part two Ex2_2 
In Java, there is no built-in option to give priority to an asynchronous task (a task that will be executed in a separate thread).
The language does allow you to set a priority for the Thread that runs the task, but not for the task itself.
Therefore, we are in a problem when we want to prioritize an asynchronous task using:
1. Interface <V> can be called in an interface that represents a task with a generic return value.
A task that is of type <V> Callable cannot be executed by a normal thread, cannot be assigned to it
indirectly preferred.
2. ThreadPool which is placed according to its tasks can be activated or <V<Callable, since it is not possible to determine
Priority to a specific Thread in the Executor's Threads collection.

In this part we created a new type that represents an asynchronous task with priority and a new ThreadPool type that supports tasks with
priority.
 
### We are create two classes and use one class to do this: 
 
 ### TaskType Class - the class that we use
We have a class called TaskType,he is enum that describes the type of task (computational/access to O/I/unknown) and its priority based on the value
The number of the task type.

### Task Class - the first class we create 
Represents an operation that can be run asynchronously and can return a value of some type (that is, will be defined). as a type
return generic (. It is not necessary for the operation to succeed and in case of failure, an exception will be thrown (E)

Extends FutureTask<E> because in the CustomExecutor class
We use an exeute that receives a variable of type Runnable Future.
Implenents Callable<E> - A task that returns a result and may throw an exception.
And Comparable<Task<E>> - To compare between the priority task(The priorityblockingquque class
make the comparison according to the compareTo function that found in the Task class.
We do overrider to compareTo method because we changed the method to compare the priority values and not to compare the addresses in memory.

- There are 2 constructors, the first get 1 parameter of Callable,the second get 2 parametrs of Callable and TaskType.
 We use the superclass constructor that can be called from the first line of a subclass constructor by using the special keyword super() and passing appropriate parameters.
 
 - We have the function call because we implements from Callable class.
  The call() method is called in order to execute the asynchronous task,the method can return a result.
  If the task is executed asynchronously,the result is typically propagated back to the creator of the task via a Java Future.

 - We have 2 factorial methods that creates a Task with a default priority,the first method get 1 parameter of Callable,the second get 2 parametrs of Callable and    TaskType. 

  - The hashCode() method in Java is used to compute hash values of Java objects and to do run time of O(1).
 
### CustomExecutor Class - the second class we create 
This class contains a custom implementation of the ThreadPoolExecutor class in Java. The CustomExecutor class allows for the execution of tasks with different priorities using a PriorityBlockingQueue to hold the tasks.

##### Methods
The following methods are provided for task submission:

submit(Task task): This method submits a task instance to a priority task queue. It returns a RunnableFuture<Object> that has been executed.
submit(Callable task, TaskType taskPriority): This method submits to the queue an operation that can be performed asynchronously with the addition of TaskType. It returns an object of type Future.
submit(Callable task): This method submits to a queue an operation that can be performed asynchronously without a TaskType as a parameter. It returns an object of type Future.

#### Additional methods provided:

setCorePoolSize(int corePoolSize): This method sets the core pool size.
setMaxPoolSize(int maxPoolSize): This method sets the max pool size.
setNumOfCores(int numOfCores): This method sets the number of cores.
getQueueTPriority(): This method returns the priority task queue.
getNumOfCores(): This method returns the number of cores.
getCorePoolSize(): This method returns the core pool size.
getMaxPoolSize(): This method returns the max pool size.
The beforeExecute method is overridden to decrement the count of the current maximum number of tasks with a given priority.

- shutdown() method is provided which allow previously submitted tasks to execute before terminating. isTerminated() method is used to wait for all the tasks submitted to the service to complete execution.
The CustomExecutor class can be used by creating an instance of it and then using the provided methods to submit tasks for execution. Tasks can be submitted with or without a TaskType priority. The class also allows for setting the core and max pool size, and getting the number of cores and the priority task queue.
 - The gracefullyTerminate method -
  A method that check if all tasks is done,not allow entry of additional tasks to the queue and Completing all remaining tasks in the queue.
  In this function we used the shutdown method that allow previously submitted tasks to execute before terminating,we used the isTerminated method of      ThreadPoolExecutor class returns true if all the tasks have been completed following shut down.
  And we used isTerminated() method of ExecutorService in Java is used to wait for all the tasks submitted to the service to complete execution
 
### Tests Class - check if we have implemented what is required.

### Design Patterns

We used the design pattern of the factory method in the Task class. The class has two factory methods "createTask()" which creates a Task object with default priority and another one with an additional parameter TaskType priority which are used to create new Task objects (accessed by our constructors which are a private method). These factory methods provide a way to create objects without exposing the instance logic to the client. Factory methods are typically used to create objects of a particular type, but they can also be used to create objects of different types based on the input provided to the factory method.
 
### Class diagram

<img src="https://user-images.githubusercontent.com/118892976/212069825-02c81ca5-5f98-46f0-a694-bad1f0c8745a.png" alt="drawing" width="500"/>

