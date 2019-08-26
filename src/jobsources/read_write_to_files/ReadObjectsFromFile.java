package jobsources.read_write_to_files;

import jobsources.SortbyJobID;
import jobsources.files_that_work_with_job_data.GlassdoorJobData;

import java.io.*;
import java.util.TreeSet;

public class ReadObjectsFromFile implements Serializable {

    public TreeSet<GlassdoorJobData> readObjects() {
        return read("jobObjects.bin");
    }

    public TreeSet<GlassdoorJobData> readObjects(String fileName) {
        return read(fileName);
    }

    @SuppressWarnings("unchecked")
    private TreeSet<GlassdoorJobData> read(String fileName) {
        WriteObjectsToFile writeObjectsToFile = new WriteObjectsToFile();
        TreeSet<GlassdoorJobData> glassdoorJobDataTreeSet = new TreeSet<>(new SortbyJobID());
        ObjectInputStream inputStream = null;

        try {
            FileInputStream fileInputStream = null;
            fileInputStream = new FileInputStream(fileName);
            inputStream = new ObjectInputStream(fileInputStream);
            glassdoorJobDataTreeSet = (TreeSet<GlassdoorJobData>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            writeObjectsToFile.writeObjects(glassdoorJobDataTreeSet);
            return glassdoorJobDataTreeSet;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (GlassdoorJobData jd : glassdoorJobDataTreeSet) {
            if (jd.getDatePosted().isEmpty()) {
                continue;
            }
            jd.setNumberOfDaysPosted();
        }
        return glassdoorJobDataTreeSet;
    }
}
