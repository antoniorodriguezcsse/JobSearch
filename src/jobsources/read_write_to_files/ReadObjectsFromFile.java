package jobsources.read_write_to_files;

import jobsources.SoryByLink;
//import jobsources.files_that_work_with_job_data.GlassdoorJobData;
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
        TreeSet<JobData> glassdoorJobDataTreeSet = new TreeSet<>(new SoryByLink());
        ObjectInputStream inputStream = null;

        try {
            FileInputStream fileInputStream = null;
            fileInputStream = new FileInputStream(fileName);
            inputStream = new ObjectInputStream(fileInputStream);
            glassdoorJobDataTreeSet = (TreeSet<JobData>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            writeObjectsToFile.writeObjects(glassdoorJobDataTreeSet);
            return glassdoorJobDataTreeSet;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (JobData jd : glassdoorJobDataTreeSet) {
            if (jd.getDatePosted().isEmpty()) {
                continue;
            }
           jd.setNumberOfDaysPosted();
        }
        return glassdoorJobDataTreeSet;
    }
}
