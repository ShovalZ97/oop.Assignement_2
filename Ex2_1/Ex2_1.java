package Ex2_1;

import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class Ex2_1 {
    /**
     * The function creates n text files on disk
     * The number of lines in each file is a random number,
     * obtained with the help of a usage class In this class and in the seed and bound parameters
     * @param n
     * @param seed
     * @param bound
     * @return  an array of the file names. Each line in the file contains at least 10 characters.
     */
    public static String[] createTextFiles(int n, int seed, int bound) {
        String[] myFiles = new String[n];
        Random rand = new Random(seed);
        for (int i = 0; i < n; i++) {
            int lines = rand.nextInt(bound);
            File txtFile = new File("file_" + (i+1));
            try {
                if (!(txtFile.createNewFile()))
                {
                    System.out.println("File already exists.");
                }
                FileWriter writeText = new FileWriter(txtFile);
                for (int j = 0; j < lines; j++) {
                    writeText.write("Hello World Peace And Love Everyone\n");
                }
                writeText.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            System.out.println(txtFile.getName() + " created with " + lines + " lines");
            myFiles[i] = txtFile.getName();
        }
        return myFiles;
    }
    /**
     * The function count the total number of lines of all the files
     * @param fileNames
     * In this function we used in Scanner class in nextLine() method, which is used to read Strings
     * @return total number of lines of the files
     */
    public static int getNumOfLines(String[] fileNames) {
        int countLine=0;
        for (String nameFile : fileNames) {
            try {
                File myFile = new File(nameFile);
                Scanner fileobj = new Scanner(myFile);
                while (fileobj.hasNextLine()) {
                    countLine++;
                    fileobj.nextLine();
                }
                fileobj.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        return countLine;
    }
    /**
     * The function count the total number of lines of all the files
     * @param fileNames
     * In this function we used the CountLine class that we wrote. This class extends from the thread class
     * The function creates a new CountLine, with each of the file name ,activating it's run method by start(),
     * and join each of the threads to get total line count sum of all files.
     * @return total number of lines of the files
     */
    public static int getNumOfLinesThreads(String[] fileNames){
        int countLine=0;
        CountLine[] threadFile = new CountLine[fileNames.length];
        for (int i=0;i<fileNames.length;i++) {
            threadFile[i] = new CountLine(fileNames[i]);
            threadFile[i].start();
        }
        for (int i=0;i<fileNames.length;i++) {
            try {
                //The join method is defined in the Thread class
                //join() method is quite useful for inter-thread synchronization
                threadFile[i].join();
                countLine += threadFile[i].getLines();
            } catch (InterruptedException e) {
                //RuntimeException is the superclass of those exceptions that can be thrown during the normal operation of the Java Virtual Machine.
                //A method is not required to declare in its throws clause any subclasses of RuntimeException that might be thrown during the execution of the method but not caught.
                throw new RuntimeException(e);
            }
        }
        return countLine;
    }
    /**
     * The function count the total number of lines of all the files
     * @param fileNames
     * In this function we used the CountLineCollable class that we wrote.
     * This class implements from the Callable interface
     * In our function getNumOfLinesThreadPool we are create thread pool with a number of threads equal to the number of files and Array list from Future objects,that represents the result of an asynchronous computation.
     * When the asynchronous task is created, a Java Future object is returned.
     * This Future object functions as a handle to the result of the asynchronous task.
     * Once the asynchronous task completes, the result can be accessed via the Future object returned when the task was started.
     * returned by the thread pool. It iterates over the list of file names and creates a CountLineCollable Callable object for each file name.
     * Later on submits the Callable object to the thread pool and add the returned Future object in the list.
     * After we are iterates over the list of Future objects and retrieves the result of each Future object by call the get() method ,each result we add to the counter and to finish the thread pool is shut down.
     * @return total number of lines of the files
     */
    public static  int getNumOfLinesThreadPool(String[] fileNames){
        ExecutorService threadFilePool = Executors.newFixedThreadPool(fileNames.length);
        int countLine = 0;
        List<Future<Integer>> listThreadFile = new ArrayList<Future<Integer>>();
        for (int i=0;i<fileNames.length;i++)
        {
            Callable<Integer> threadFile = new CountLineCollable(fileNames[i]);
            listThreadFile.add(threadFilePool.submit(threadFile));
        }
        for (int i=0;i<fileNames.length;i++) {
            try{
                countLine+= listThreadFile.get(i).get();
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
            catch (ExecutionException e)
            {
                throw new RuntimeException(e);
            }
        }
        threadFilePool.shutdown();
        return countLine;

    }

}



