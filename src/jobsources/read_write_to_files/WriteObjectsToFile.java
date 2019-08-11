package jobsources.read_write_to_files;

import jobsources.files_that_work_with_job_data.JobData;

import java.io.*;
import java.util.TreeSet;

class WriteObjectsToFile implements Serializable {

    void writeObjectsToFile(TreeSet<JobData> listOfJobData) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("jobObjects.bin"));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(listOfJobData);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
