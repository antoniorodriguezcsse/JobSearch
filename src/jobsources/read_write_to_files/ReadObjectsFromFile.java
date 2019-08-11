package jobsources.read_write_to_files;

import jobsources.SortbyRank;
import jobsources.files_that_work_with_job_data.JobData;

import java.io.*;
import java.util.TreeSet;

class ReadObjectsFromFile implements Serializable {

    @SuppressWarnings("unchecked")
    TreeSet<JobData> readObjectsFromFile() {
         WriteObjectsToFile writeObjectsToFile = new WriteObjectsToFile();
        TreeSet<JobData> jobDataTreeSet = new TreeSet<>(new SortbyRank());
        ObjectInputStream inputStream = null;

        try {
            FileInputStream fileInputStream = null;
            fileInputStream = new FileInputStream("jobObjects.bin");
            inputStream = new ObjectInputStream(fileInputStream);
            jobDataTreeSet = (TreeSet<JobData>) inputStream.readObject();
        } catch(FileNotFoundException e)
        {
            writeObjectsToFile.writeObjectsToFile(jobDataTreeSet);
            return  jobDataTreeSet;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return jobDataTreeSet;
    }
}
