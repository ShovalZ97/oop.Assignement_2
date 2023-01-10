package Ex2_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class CountLine extends  Thread {
    String nameFile = "";
    int lines;

    /**
     * constructor of class CountLine
     * @param filename
     */
    public CountLine(String filename) {
        this.nameFile = filename;
        this.lines=0;
    }
    /**
     * run() method of thread class is called if the thread was constructed using a separate Runnable object otherwise this method does nothing and returns
     */
    @Override
    public void run() {
        try {
            File myFile = new File(nameFile);
            Scanner fileobj = new Scanner(myFile);
            while (fileobj.hasNextLine()) {
                this.lines++;
                fileobj.nextLine();
            }
            fileobj.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public String getFilename() {
        return this.nameFile;
    }
    public void setFilename(String nameFile) {
        this.nameFile = nameFile;
    }
    public int getLines() {
        return this.lines;
    }
}
