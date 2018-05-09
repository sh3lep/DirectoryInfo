package com.nodomain.mypackage;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.kohsuke.args4j.CmdLineException;

class DirInfoTest {


    @Test
    void test1() throws IOException, CmdLineException {
        String[] command = "-l -h -r -o output1.txt .\\src\\test\\resources\\forTest\\Dir1".split(" ");

        DirInfo.main(command);

        File file1 = new File(".\\src\\test\\resources\\testOutput\\output1.txt");
        File file2 = new File(".\\src\\test\\resources\\expectedOutput\\eOutput1.txt");

        assertTrue(FileUtils.contentEquals(file1, file2));
    }

    @Test
    void test2() throws IOException, CmdLineException {

        String[] command = "-l .\\src\\test\\resources\\forTest\\Dir1".split(" ");

        PrintStream old = System.out;
        PrintStream toFile = new PrintStream(new File(".\\src\\test\\resources\\testOutput\\output2.txt"));
        System.setOut(toFile);
        DirInfo.main(command);
        System.out.flush();
        System.setOut(old);

        File file1 = new File(".\\src\\test\\resources\\testOutput\\output2.txt");
        File file2 = new File(".\\src\\test\\resources\\expectedOutput\\eOutput3.txt");
        File file3 = new File(".\\src\\test\\resources\\expectedOutput\\eOutput1.txt");
        File file4 = new File(".\\src\\test\\resources\\expectedOutput\\eOutput2.txt");

        assertFalse(FileUtils.contentEquals(file1, file2));
        assertFalse(FileUtils.contentEquals(file1, file3));
        assertTrue(FileUtils.contentEquals(file1, file4));

    }

    @Test
    void test3() throws IOException, CmdLineException {
        String[] command = "-l -h -o output3.txt .\\src\\test\\resources\\forTest\\TestDoc.docx".split(" ");

        DirInfo.main(command);

        File file1 = new File(".\\src\\test\\resources\\testOutput\\output3.txt");
        File file2 = new File(".\\src\\test\\resources\\expectedOutput\\eOutput3.txt");
        File file3 = new File(".\\src\\test\\resources\\expectedOutput\\eOutput1.txt");

        assertFalse(FileUtils.contentEquals(file1, file3));
        assertTrue(FileUtils.contentEquals(file1, file2));
    }

    @Test
    void test4() throws IOException, CmdLineException {
        String[] command = "-h -r .\\src\\test\\resources\\forTest\\TestDoc.docx".split(" ");

        PrintStream old = System.out;
        PrintStream toFile = new PrintStream(new File(".\\src\\test\\resources\\testOutput\\output4.txt"));
        System.setOut(toFile);
        System.setErr(toFile);
        try {
            DirInfo.main(command);
        } catch (CmdLineException e) {
            // To let the assertion happen
        }
        System.out.flush();
        System.err.flush();
        System.setOut(old);
        System.setErr(old);

        File file1 = new File(".\\src\\test\\resources\\testOutput\\output4.txt");
        File file2 = new File(".\\src\\test\\resources\\expectedOutput\\eOutput4.txt");

        assertTrue(FileUtils.contentEquals(file1, file2));
    }

    @Test
    void test5() throws IOException, CmdLineException {
        String[] command = "-l -h .\\src\\test\\resources\\forTest\\GoodWeatherInSaintP".split(" ");

        PrintStream old = System.out;
        PrintStream toFile = new PrintStream(new File(".\\src\\test\\resources\\testOutput\\output5.txt"));
        System.setOut(toFile);
        System.setErr(toFile);
        DirInfo.main(command);
        System.out.flush();
        System.err.flush();
        System.setOut(old);
        System.setErr(old);

        File file1 = new File(".\\src\\test\\resources\\testOutput\\output5.txt");
        File file2 = new File(".\\src\\test\\resources\\expectedOutput\\eOutput5.txt");

        assertTrue(FileUtils.contentEquals(file1, file2));
    }

    @Test
    void test6() throws IOException, CmdLineException {
        String[] command = "-l".split(" ");

        PrintStream old = System.out;
        PrintStream toFile = new PrintStream(new File(".\\src\\test\\resources\\testOutput\\output6.txt"));
        System.setOut(toFile);
        System.setErr(toFile);
        try {
            DirInfo.main(command);
        } catch (CmdLineException e) {
            // To let the assertion happen
        }
        System.out.flush();
        System.err.flush();
        System.setOut(old);
        System.setErr(old);

        File file1 = new File(".\\src\\test\\resources\\testOutput\\output6.txt");
        File file2 = new File(".\\src\\test\\resources\\expectedOutput\\eOutput6.txt");

        assertTrue(FileUtils.contentEquals(file1, file2));
    }

    @Test
    void test7() throws IOException, CmdLineException {
        String[] command = "-l -o output0.txt".split(" ");

        PrintStream old = System.out;
        PrintStream toFile = new PrintStream(new File(".\\src\\test\\resources\\testOutput\\output7.txt"));
        System.setOut(toFile);
        System.setErr(toFile);
        try {
            DirInfo.main(command);
        } catch (CmdLineException e) {
            // To let the assertion happen
        }
        System.out.flush();
        System.err.flush();
        System.setOut(old);
        System.setErr(old);

        File file1 = new File(".\\src\\test\\resources\\testOutput\\output7.txt");
        File file2 = new File(".\\src\\test\\resources\\expectedOutput\\eOutput7.txt");

        assertTrue(FileUtils.contentEquals(file1, file2));
    }
}
