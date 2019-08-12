package jobsources.read_write_to_files;

import jobsources.SortbyJobID;
import jobsources.files_that_work_with_job_data.JobData;

import java.io.*;
import java.util.TreeSet;

public class ReadObjectsFromFile implements Serializable {

    public TreeSet<JobData> readObjects() {
        return read("jobObjects.bin");
    }

    public TreeSet<JobData> readObjects(String fileName) {
        return read(fileName);
    }

    @SuppressWarnings("unchecked")
    private TreeSet<JobData> read(String fileName) {
        WriteObjectsToFile writeObjectsToFile = new WriteObjectsToFile();
        TreeSet<JobData> jobDataTreeSet = new TreeSet<>(new SortbyJobID());
        ObjectInputStream inputStream = null;

        try {
            FileInputStream fileInputStream = null;
            fileInputStream = new FileInputStream(fileName);
            inputStream = new ObjectInputStream(fileInputStream);
            jobDataTreeSet = (TreeSet<JobData>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            writeObjectsToFile.writeObjects(jobDataTreeSet);
            return jobDataTreeSet;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return jobDataTreeSet;
    }
}
