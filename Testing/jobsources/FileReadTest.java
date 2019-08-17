package jobsources;

import jobsources.read_write_to_files.FileRead;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileReadTest {
    private FileRead fileReader = new FileRead();

    @Test
    void getLinesFromFile_fileDoesExistAndHasLines_returnArrayListOfTheLines() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("line 1");
        expected.add("line 2 line 2");
        expected.add("line 3 line 3 line 3");
        ArrayList<String> actual = new ArrayList<>();
        actual = fileReader.getLinesFromFile("ReadFilesTest\\notEmpty.txt");
        assertEquals(expected, actual);
    }

    @Test
    void getLinesFromFile_fileDoesNotExist_stringFileDoesntExist() {
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("File does not exist.");
        ArrayList<String> myList = new ArrayList<>();
        myList = fileReader.getLinesFromFile("ReadFilesTest\\eessEEEs.txt");
        assertEquals(testArray, myList);
    }

    @Test
    void getLinesFromFile_readingEmptyFile_stringFileIsEmpty() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("File is empty.");
        ArrayList<String> actual = new ArrayList<>();
        actual = fileReader.getLinesFromFile("ReadFilesTest\\empty.txt");
        assertEquals(expected, actual);
    }


    @Test
    void getLinesFromFile_usingFileReadertwice_lastReadFileOnlyInArrayList() {
        ArrayList<String> myList = new ArrayList<>();
        myList = fileReader.getLinesFromFile("ReadFilesTest\\notEmpty.txt");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("this file");
        expected.add("is not empty");
        expected.add("2");

        ArrayList<String> actual = new ArrayList<>();
        actual = fileReader.getLinesFromFile("ReadFilesTest\\notEmpty2.txt");

        assertEquals(expected,actual);
    }

}