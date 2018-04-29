package DirInfo;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;

class DirInfoTest {


    @Test
    void test1() throws IOException {
        String[] command = "-l -h -r -o output1.txt .\\forTest\\Dir1".split(" ");

        DirInfo.main(command);

        File file1 = new File(".\\testOutput\\output1.txt");
        File file2 = new File(".\\expectedOutput\\eOutput1.txt");

        assertTrue(FileUtils.contentEquals(file1, file2));
    }

    @Test
    void test2() throws IOException {

        String[] command = "-l .\\forTest\\Dir1".split(" ");

        PrintStream old = System.out;
        PrintStream toFile = new PrintStream(new File(".\\testOutput\\output2.txt"));
        System.setOut(toFile);
        DirInfo.main(command);
        System.out.flush();
        System.setOut(old);

        File file1 = new File(".\\testOutput\\output2.txt");
        File file2 = new File(".\\expectedOutput\\eOutput3.txt");
        File file3 = new File(".\\expectedOutput\\eOutput1.txt");
        File file4 = new File(".\\expectedOutput\\eOutput2.txt");

        assertFalse(FileUtils.contentEquals(file1, file2));
        assertFalse(FileUtils.contentEquals(file1, file3));
        assertTrue(FileUtils.contentEquals(file1, file4));

    }

    @Test
    void test3() throws IOException {
        String[] command = "-l -h -o output3.txt .\\forTest\\TestDoc.docx".split(" ");

        DirInfo.main(command);

        File file1 = new File(".\\testOutput\\output3.txt");
        File file2 = new File(".\\expectedOutput\\eOutput3.txt");
        File file3 = new File(".\\expectedOutput\\eOutput1.txt");

        assertFalse(FileUtils.contentEquals(file1, file3));
        assertTrue(FileUtils.contentEquals(file1, file2));
    }

    @Test
    void test4() throws IOException {
        String[] command = "-h -r .\\forTest\\TestDoc.docx".split(" ");

        PrintStream old = System.out;
        PrintStream toFile = new PrintStream(new File(".\\testOutput\\output4.txt"));
        System.setOut(toFile);
        System.setErr(toFile);
        DirInfo.main(command);
        System.out.flush();
        System.err.flush();
        System.setOut(old);
        System.setErr(old);

        File file1 = new File(".\\testOutput\\output4.txt");
        File file2 = new File(".\\expectedOutput\\eOutput4.txt");

        assertTrue(FileUtils.contentEquals(file1, file2));
    }

    @Test
    void test5() throws  IOException {
        String[] command = "-l -h .\\forTest\\GoodWeatherInSaintP".split(" ");

        PrintStream old = System.out;
        PrintStream toFile = new PrintStream(new File(".\\testOutput\\output5.txt"));
        System.setOut(toFile);
        System.setErr(toFile);
        DirInfo.main(command);
        System.out.flush();
        System.err.flush();
        System.setOut(old);
        System.setErr(old);

        File file1 = new File(".\\testOutput\\output5.txt");
        File file2 = new File(".\\expectedOutput\\eOutput5.txt");

        assertTrue(FileUtils.contentEquals(file1, file2));
    }

    @Test
    void test6() throws IOException {
        String[] command = "-l".split(" ");

        PrintStream old = System.out;
        PrintStream toFile = new PrintStream(new File(".\\testOutput\\output6.txt"));
        System.setOut(toFile);
        System.setErr(toFile);
        DirInfo.main(command);
        System.out.flush();
        System.err.flush();
        System.setOut(old);
        System.setErr(old);

        File file1 = new File(".\\testOutput\\output6.txt");
        File file2 = new File(".\\expectedOutput\\eOutput6.txt");

        assertTrue(FileUtils.contentEquals(file1, file2));
    }

    @Test
    void test7() throws IOException {
        String[] command = "-l -o output0.txt".split(" ");

        PrintStream old = System.out;
        PrintStream toFile = new PrintStream(new File(".\\testOutput\\output7.txt"));
        System.setOut(toFile);
        System.setErr(toFile);
        DirInfo.main(command);
        System.out.flush();
        System.err.flush();
        System.setOut(old);
        System.setErr(old);

        File file1 = new File(".\\testOutput\\output7.txt");
        File file2 = new File(".\\expectedOutput\\eOutput7.txt");

        assertTrue(FileUtils.contentEquals(file1, file2));
    }
}
