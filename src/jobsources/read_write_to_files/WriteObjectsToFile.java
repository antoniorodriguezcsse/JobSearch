package jobsources.read_write_to_files;

import jobsources.files_that_work_with_job_data.JobData;

import java.io.*;
import java.util.TreeSet;

public class WriteObjectsToFile implements Serializable {

    public void writeObjects(TreeSet<JobData> listOfJobData) {
        writeToFile(listOfJobData, "jobObjects.bin");
    }

    public void writeObjects(TreeSet<JobData> listOfJobData, String fileName) {
        writeToFile(listOfJobData, fileName);
    }

    private void writeToFile(TreeSet<JobData> listOfJobData, String fileName)
    {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(listOfJobData);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
