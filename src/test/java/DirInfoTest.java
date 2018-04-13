import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;

class DirInfoTest {


    @Test
    void test1() throws IOException { // Можно проще?
        String[] command = "-l -h -r -o output1.txt .\\forTest\\Dir1".split(" ");

        DirInfo.main(command);

        File file1 = new File(".\\testOutput\\output1.txt");
        File file2 = new File(".\\expectedOutput\\eOutput1.txt");

        assertTrue(FileUtils.contentEquals(file1, file2));
    }

    @Test
    void test2() throws IOException {

        String[] command = "-l .\\forTest\\Dir1".split(" ");

        PrintStream o = new PrintStream(new File(".\\testOutput\\output2.txt"));
        System.setOut(o);
        DirInfo.main(command);

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
        String[] command = "-l -h -r -o output3.txt .\\forTest\\TestDoc.docx".split(" ");

        DirInfo.main(command);

        File file1 = new File(".\\testOutput\\output3.txt");
        File file2 = new File(".\\expectedOutput\\eOutput3.txt");
        File file3 = new File(".\\expectedOutput\\eOutput1.txt");

        assertFalse(FileUtils.contentEquals(file1, file3));
        assertTrue(FileUtils.contentEquals(file1, file2));
    }
}
