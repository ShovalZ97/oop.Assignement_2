package Ex2_1;

import java.io.File;

public class Ex2_Test {
    public static void main(String[] args) {
        String[] filesArr = new String[10];
        filesArr = Ex2_1.createTextFiles(filesArr.length, 1, 500);
        System.out.println("num of getNumOfLines:" + Ex2_1.getNumOfLines(filesArr));
        System.out.println("num of getNumOfLinesThreads:" + Ex2_1.getNumOfLinesThreads(filesArr));
        System.out.println("num of getNumOfLinesThreadPool:" + Ex2_1.getNumOfLinesThreadPool(filesArr));

        long startTime1 = System.currentTimeMillis();
        int numofLines = Ex2_1.getNumOfLines(filesArr);
        System.out.println("Time of getNumOfLines:" + (System.currentTimeMillis() - startTime1) + " ms");

        long startTime2 = System.currentTimeMillis();
        int numOfLinesThreads = Ex2_1.getNumOfLinesThreads(filesArr);
        System.out.println("Time of getNumOfLinesThreads:" + (System.currentTimeMillis() - startTime2) + " ms");

        long startTime3 = System.currentTimeMillis();
        int numOfLinesThreadPool = Ex2_1.getNumOfLinesThreadPool(filesArr);
        System.out.println("Time of getNumOfLinesThreadPool:" + (System.currentTimeMillis() - startTime3) + " ms");

            for(int i=0;i<filesArr.length;i++) {
                File obj = new File("file_"+ (i+1));
                if (obj.delete()) {
                    filesArr[i]=null;
                } else {
                    System.out.println("Can't delete file_"+(i+1));
                }

        }

    }

}
