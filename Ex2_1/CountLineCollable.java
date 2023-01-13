package Ex2_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class CountLineCollable implements Callable<Integer>
{
    String nameFile = "";
    /**
     * constructor of class CountLine
     * @param nameFile
     */
    public CountLineCollable(String nameFile)
    {
        this.nameFile = nameFile;
    }
    /**
     * The call() method is called in order to execute the asynchronous task.
     * The call() method can return a result. If the task is executed asynchronously,
     *  the result is typically propagated back to the creator of the task via a Java Future
     */
    @Override
    public Integer call() throws Exception {
        int countLine=0;
        try
        {
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
        return countLine;
    }
}