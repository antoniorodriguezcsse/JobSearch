package jobsources.read_write_to_files;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileWrite {
   // private final String fileName;
    // private String lineToWrite;


//    public FileWrite(String fileName) {
//        this.fileName = (fileName);
//        // this.lineToWrite = lineToWrite;
//    }

    public void writeLineToFile(String lineToWrite, String fileName) {

        Writer writer = createWriter(fileName);
        writeToFile(lineToWrite, writer);

    }

    private void writeToFile(String lineToWrite, Writer writer) {
        try {
            lineToWrite = lineToWrite + "\r\n";
            writer.append(lineToWrite);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Writer createWriter(String fileName) {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName, true), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return writer;
    }
}
