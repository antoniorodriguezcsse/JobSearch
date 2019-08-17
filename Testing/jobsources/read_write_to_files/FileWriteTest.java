package jobsources.read_write_to_files;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileWriteTest {

    FileWrite fileWrite = new FileWrite();

    @Test
    void writeLineToFile_fileDoesExistAndIsEmpty_writeToFileSuccessfully() throws IOException {
        File file = new File("WriteFileTest\\TestWriteToEmptyFile.txt");

        if (file.delete()) {
                file.createNewFile();
        }

        ArrayList<String> linesToWrite = new ArrayList<>();
        linesToWrite.add("hello");
        linesToWrite.add("two this is a string");
        for (String s : linesToWrite) {
            fileWrite.writeLineToFile(s, "WriteFileTest\\TestWriteToEmptyFile.txt");
        }


        ArrayList<String> linesFromFile = new ArrayList<>();
        FileRead fileRead = new FileRead();
        linesFromFile = fileRead.getLinesFromFile("WriteFileTest\\TestWriteToEmptyFile.txt");
        assertEquals(linesToWrite, linesFromFile);
    }

    @Test
    void writeLineToFile_fileDoesNotExist_CreateAndwriteToFileSuccessfully() throws IOException {
        File file = new File("WriteFileTest\\TestWriteToEmptyFile.txt");

        file.delete();

        ArrayList<String> linesToWrite = new ArrayList<>();
        linesToWrite.add("hello");
        linesToWrite.add("two this is a string");
        for (String s : linesToWrite) {
            fileWrite.writeLineToFile(s, "WriteFileTest\\TestWriteToEmptyFile.txt");
        }


        ArrayList<String> linesFromFile;
        FileRead fileRead = new FileRead();
        linesFromFile = fileRead.getLinesFromFile("WriteFileTest\\TestWriteToEmptyFile.txt");
        assertEquals(linesToWrite, linesFromFile);
    }
}