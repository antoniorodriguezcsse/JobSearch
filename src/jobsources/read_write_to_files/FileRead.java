package jobsources.read_write_to_files;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileRead {

    private String fileName;
    private String errorMessage;
    private ArrayList<String> linesFromFile = new ArrayList<>();
    private Scanner fileScanner;
    private int dilimeterCounter = 0;
    private FileInputStream fileInputStream = null;
    private String lineFromFile = "";

    private Boolean doesFileExist(String file) {
        try {
            fileInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    public ArrayList<String> getLinesFromFile(String fileName) {
        this.fileName = fileName;

        if (!linesFromFile.isEmpty()) {
            linesFromFile.clear();
        }
        if (!doesFileExist(fileName)) {
            linesFromFile.add("File does not exist.");
            return linesFromFile;
        }

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        setLinesFromFile(bufferedReader);

        closeReaders(inputStreamReader, bufferedReader);

        if (linesFromFile.isEmpty()) {
            linesFromFile.add("File is empty.");
            return linesFromFile;
        }
        return linesFromFile;
    }

    private void closeReaders(InputStreamReader inputStreamReader, BufferedReader bufferedReader) {
        try {
            bufferedReader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setLinesFromFile(BufferedReader bufferedReader) {
        while (true) {
            try {
                if ((lineFromFile = bufferedReader.readLine()) == null) {
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            lineFromFile = replaceReplacementCharacterWithApostrophie(lineFromFile);
            linesFromFile.add(lineFromFile);
        }
    }

    private String replaceReplacementCharacterWithApostrophie(String sCurrentLine) {
        for (int i = 0; i < sCurrentLine.length(); i++) {
            if ((int) sCurrentLine.charAt(i) == 65533) {
                sCurrentLine = sCurrentLine.replace((char) 65533, (char) 0x0027);
            }
        }
        return sCurrentLine;
    }
}
